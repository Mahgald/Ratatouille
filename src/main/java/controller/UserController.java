package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import dao.FriendRequestDao;
import dao.UserDao;
import helpers.ResultObject;
import model.Commentary;
import model.Configuration;
import model.FoodDish;
import model.FriendRequest;
import model.Responsible;
import model.Restaurant;
import model.User;
import model.Valoration;
import service.ResponsibleService;
import service.UserService;

@Controller
@RequestMapping("User")
public class UserController {
		
	@Autowired
	UserService userService;
	@Autowired
	ResponsibleService responsibleService;
	
	@RequestMapping(path="/",method=RequestMethod.GET)
	public ModelAndView createUser(HttpServletRequest request){
		ModelAndView view = new ModelAndView("User/SignUp");
		 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		 if (inputFlashMap != null) {
			  view.addObject("error",inputFlashMap.get("error"));
		  }
		view.addObject("newUser", new User());
		return view;
	}
	
	@RequestMapping(path="CrearUser", method=RequestMethod.POST)
	public ModelAndView newUser(@ModelAttribute("newUser")User user,RedirectAttributes atributos){
		ResultObject resultObject=userService.createNewUser(user);
		if(!resultObject.getResult()){
			ModelAndView view = new ModelAndView("redirect:/User/");
			atributos.addFlashAttribute("error", resultObject.getMessages());
			return view;
		}else{
			ModelAndView view = new ModelAndView("Index");
			view.addObject("message", "Se ha registrado correctamente");
			return view;
		}
	}
	
	@RequestMapping(path="LogIn", method=RequestMethod.POST)
	public ModelAndView logIn(@ModelAttribute("UserToLog")User user , @RequestParam(value="uri") String uri, HttpServletRequest request,RedirectAttributes atributos){
		
		ModelAndView view=new ModelAndView();
		HttpSession session = request.getSession(false);
		ResultObject result=userService.logInUser(user);
		if(result.getResult()){
			if(responsibleService.existByMail(user.getEmail())){
				session.setAttribute("typeUserLoged", 2);
			}else{
				session.setAttribute("typeUserLoged", 1);
			}
			session.setAttribute("userLoged", user.getEmail());
			session.setAttribute("idUser", ((User)result.getContent()).getId());
			if(!uri.equals("")){
				view.setViewName("redirect:"+uri);
				
				return view;
			}else{
				view.addObject("message", "Bienenido");
				view.setViewName("Index");
				view.addObject("user", ((User)result.getContent()));
				return view;
			}
		}else{
			if(!uri.equals("")){
				atributos.addFlashAttribute("error", result.getMessages());
				atributos.addFlashAttribute("redirectUrl", uri);
				view.setViewName("redirect:"+uri);
				return view;
			}else{
				view.addObject("error", result.getMessages());
				view.setViewName("Index");
				return view;
			}
		}
	}
	
	@RequestMapping(path="LogOut", method=RequestMethod.GET)
	public ModelAndView logOut(HttpServletRequest request){
		HttpSession session = request.getSession();
		ModelAndView view = new ModelAndView("Index");
		session.removeAttribute("userLoged");
		session.removeAttribute("idUser");
		session.setAttribute("typeUserLoged", 0);
		return view;
	}
	
	
	@RequestMapping(value = "users/{partOfName}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<User>> listAllUsers(@PathVariable("partOfName") String partOfName) {
	List<User> users = userService.getUsersLikeWords(partOfName);
	if(users.isEmpty()){
		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	

	
	@RequestMapping(path="Perfil", method=RequestMethod.GET)
	public ModelAndView showPerfil( @RequestParam(value="idUser", required=true) Long idUser,HttpServletRequest request){
		ResultObject result=new ResultObject();
		HttpSession session = request.getSession(false);
		if( ((int)session.getAttribute("typeUserLoged") >0) && session.getAttribute("idUser").equals(idUser)){
			User user = userService.GetUserById(idUser);
			ModelAndView view =new ModelAndView("User/Perfil","user",user);
			 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			 if (inputFlashMap != null) {
				  view.addObject("message",inputFlashMap.get("message"));
				  view.addObject("error",inputFlashMap.get("error"));
			  }
			
			return view;
		}else{
			result.getMessages().put("ERROR IN CONTENT", "Lo sentimos pero el perfil de un usuario es privado al mismo por el momento");
			ModelAndView view =new ModelAndView("Index");
			view.addObject("error", result.getMessages());
			return view;
		}
		
	}
	
	
	@RequestMapping(path="DeleteUser", method=RequestMethod.POST)
	public String deleteUser(@ModelAttribute("user")User userToModified, HttpServletRequest request,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=userService.deactivateAccount(userToModified);
		atributos.addFlashAttribute("message", "Se ha dado de baja exitosamente");
		return "redirect:/User/Perfil?idUser="+userToModified.getId();
	}
	
	@RequestMapping(path="ReactiveUser", method=RequestMethod.POST)
	public String reactivateUser(@ModelAttribute("user")User userToModified, HttpServletRequest request,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=userService.reactivateAccount(userToModified);
		atributos.addFlashAttribute("message", "Se ha reactivado su cuenta exitosamente");
		return "redirect:/User/Perfil?idUser="+userToModified.getId();
	}
	
	
	@RequestMapping(path="ChangeInformationOfUser", method=RequestMethod.POST)
	public String changeInformationOfUser(@ModelAttribute("user")User userToModified, HttpServletRequest request,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=userService.changeInformationOfAccount(userToModified);
		if (result.getResult()){
			if(!userToModified.getEmail().equals((String)session.getAttribute("userLoged"))){
				session.setAttribute("userLoged", userToModified.getEmail());
			}
			atributos.addFlashAttribute("message", "Se ha modificado su informacion de cuenta exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/User/Perfil?idUser="+userToModified.getId();
	}
	
	@RequestMapping(path="ChangePassword", method=RequestMethod.POST)
	public String changePassword(@ModelAttribute("user")User userToModified, HttpServletRequest request,@RequestParam(value="oldPassword", required=true) String oldPassword,@RequestParam(value="reNewPassword", required=true) String reNewPassword,@RequestParam(value="newPassword", required=true) String newPassword,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=userService.changePasswordOfAccount(userToModified,oldPassword,newPassword,reNewPassword);
		if (result.getResult()){
			atributos.addFlashAttribute("message", "Se ha modificado su clave de cuenta exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/User/Perfil?idUser="+userToModified.getId();
	}
	
	
	@RequestMapping(path="SendFriendRequest", method=RequestMethod.POST)
	public String sendFriendRequest( HttpServletRequest request,@RequestParam(value="idOfUserToSendFriendRequest", required=true) Long idOfUserToSendFriendRequest,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=userService.sendFriendRequest((String)session.getAttribute("userLoged"),idOfUserToSendFriendRequest);
		if (result.getResult()){
			atributos.addFlashAttribute("message", "Su solicitud de amistad se ha enviado exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/User/Perfil?idUser="+session.getAttribute("idUser");
	}
	

	@RequestMapping(path="AcceptFriendRequest", method=RequestMethod.POST)
	public String acceptFriendRequest( HttpServletRequest request,@RequestParam(value="idOfFriendRequest", required=true) Long idOfFriendRequest,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=userService.acceptFriendRequest((String)session.getAttribute("userLoged"),idOfFriendRequest);
		if (result.getResult()){
			atributos.addFlashAttribute("message", "Ha aceptado una solictud de amistad");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/User/Perfil?idUser="+session.getAttribute("idUser");
	}
	
	@RequestMapping(path="RejectFriendRequest", method=RequestMethod.POST)
	public String rejectFriendRequest( HttpServletRequest request,@RequestParam(value="idOfFriendRequest", required=true) Long idOfFriendRequest,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=userService.rejectFriendRequest((String)session.getAttribute("userLoged"),idOfFriendRequest);
		if (result.getResult()){
			atributos.addFlashAttribute("message", "Ha rechazado una solictud de amistad");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/User/Perfil?idUser="+session.getAttribute("idUser");
	}
	
}
