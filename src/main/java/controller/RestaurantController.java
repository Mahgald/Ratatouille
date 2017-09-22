package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import dao.CommentaryDao;
import dao.FoodDishDao;
import dao.RestaurantDao;
import dao.UbicationDao;
import dao.UserDao;
import helpers.ResultObject;
import model.Commentary;
import model.Configuration;
import model.DomainObject;
import model.FilterByCategorieUser;
import model.FoodDish;
import model.Notification;
import model.Restaurant;
import model.Ubication;
import model.User;
import model.Valoration;
import service.IFoodDishService;
import service.IRestaurantService;
import service.IUserService;
import service.RestaurantService;
import service.UbicationService;

@Controller
@RequestMapping("Restaurant")
public class RestaurantController {
	
	@Autowired
	IRestaurantService restaurantService;
	@Autowired
	IUserService userService;
	@Autowired
	IFoodDishService foodDishService;
	
	@Autowired
	RestaurantDao restaurantDAO;
	@Autowired
	UbicationDao ubicationDAO;
	@Autowired
	FoodDishDao foodDishDAO;
	
	@Autowired
	CommentaryDao commentaryDAO;
	@Autowired
	UserDao userDAO;
	
	@RequestMapping("NewRestaurant")
	public ModelAndView newRestaurant(HttpServletRequest request){
		ResultObject result=new ResultObject();
		if((Integer) request.getSession().getAttribute("typeUserLoged") > 0){
			ModelAndView view = new ModelAndView("/Restaurant/NewRestaurant");
			view.addObject("newRestaurant",new Restaurant());
			 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			 if (inputFlashMap != null) {
				  view.addObject("message",inputFlashMap.get("message"));
				  view.addObject("error",inputFlashMap.get("error"));
			  }
			 User user = userService.GetUserById((Long)request.getSession().getAttribute("idUser"));
				view.addObject("user",user);
			 
			return view;
		}else{
			ModelAndView view=new ModelAndView("Index");
			view.addObject("redirectUrl","/Restaurant/NewRestaurant/");
			result.getMessages().put("ERROR LOGIN DATOS NO VALIDOS", "Por favor logueese para poder realizar esta accion");
			view.addObject("error", result.getMessages());
			 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			 if (inputFlashMap != null) {
				  view.addObject("message",inputFlashMap.get("message"));
				  view.addObject("error",inputFlashMap.get("error"));
			  }
			return view;
		}
	}
	
	
	@RequestMapping(path="SaveNewRestaurant", method=RequestMethod.POST)
	public String saveNewRestaurant(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=restaurantService.newRestaurant(restaurant,(String)request.getSession().getAttribute("userLoged"));
		if(result.getResult()){
			if (((int)session.getAttribute("typeUserLoged")) != 2){
				session.setAttribute("typeUserLoged", 2);
				User userLoged=userService.GetUserWithThisMail((String) session.getAttribute("userLoged"));
				session.setAttribute("idUser", userLoged.getId());
			}
			atributos.addFlashAttribute("message", "Se ha registrado el restaurant exitosamente");
			return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+((Restaurant)result.getContent()).getIdDomainObject();
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
			return "redirect:/Restaurant/NewRestaurant/";
		}
	}
	
	@RequestMapping(path="ChangeConfiguration", method=RequestMethod.POST)
	public String changeConfigurationt(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request ,RedirectAttributes atributos, 
				@RequestParam(value="visitor", required=false) Boolean visitor, @RequestParam(value="commensal", required=false) Boolean commensal, @RequestParam(value="gourmet", required=false) Boolean gourmet){
		List<Boolean> combination=new ArrayList<Boolean>();
		if(visitor==null){
			visitor=false;
		}
		if(commensal==null){
			commensal=false;
		}
		if(gourmet==null){
			gourmet=false;
		}
		combination.add(gourmet);
		combination.add(commensal);
		combination.add(visitor);
		ResultObject result=restaurantService.ChangeConfigurationOfRestaurantForResponsible(restaurant,(String)request.getSession().getAttribute("userLoged"),combination);
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha actualizado su configuracion para filtrar las notificaciones");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
		
	}
	
	
	/**
	 * @deprecated this method not isnecessary actually
	 */
	@Deprecated
	@RequestMapping(path="NewFoodDish", method=RequestMethod.POST)
	public ModelAndView newFoodDish(@ModelAttribute("restaurant")Restaurant restaurant){

		restaurant = restaurantDAO.getEntity(restaurant.getIdDomainObject());
		FoodDish foodDish = new FoodDish();
		foodDish.setRestaurant(restaurant);
		ModelAndView modelAndView=new ModelAndView("Restaurant/NewFoodDish");
		modelAndView.addObject("restaurant",restaurant);
		modelAndView.addObject("newFoodDish",foodDish);
		modelAndView.addObject("idvalor",restaurant.getIdDomainObject());
		return modelAndView;
	}
	
	@RequestMapping(path="SaveNewFoodDish", method=RequestMethod.POST)
	public String saveNewFoodDish(@ModelAttribute("newFoodDish")FoodDish foodDish,RedirectAttributes atributos){
		ResultObject result=restaurantService.saveNewFoodDishOfRestaurant(foodDish);
		if (result.getResult()){
			//ModelAndView modelAndView=new ModelAndView("Restaurant/ShowRestaurant");
			//modelAndView.addObject("restaurant",((FoodDish)result.getContent()).getRestaurant());
			atributos.addFlashAttribute("message", "Se ha agregado un nuevo plato al menu de este restaurant");
			return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+((FoodDish)result.getContent()).getRestaurant().getIdDomainObject();
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
			return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+((FoodDish)result.getContent()).getRestaurant().getIdDomainObject();
		}
	}
	
	
	@RequestMapping(path="CommentFoodDish", method=RequestMethod.POST)
	public String commentFoodDish(@ModelAttribute("comentary")Commentary commentary,@RequestParam(value="idFoodDish", required=true) Long idFoodDish,@RequestParam(value="valorationOfNewCommentaryOfFoodDish", required=true) String valoration, HttpServletRequest request,RedirectAttributes atributos){
		
		if ((int)request.getSession().getAttribute("typeUserLoged")>0){
			ResultObject result=foodDishService.saveComentaryOfFoodDish(commentary,idFoodDish,valoration,(String)request.getSession().getAttribute("userLoged"));
			if(result.getResult()){
				atributos.addFlashAttribute("message", "Se ha agregado su comentario sobre el plato");
			}else{
				atributos.addFlashAttribute("error", result.getMessages());
			}			
			return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+((FoodDish)result.getContent()).getRestaurant().getIdDomainObject();
		}else{
			ResultObject result=foodDishService.getRestaurantOfThisFoodDish(idFoodDish);
			result.getMessages().put("ERROR LOGIN DATOS NO VALIDOS", "Para comentar un plato de comida debe loguearse primero");
			atributos.addFlashAttribute("error", result.getMessages());
			atributos.addFlashAttribute("redirectUrl","/Restaurant/ShowRestaurant?idRestaurant="+((Restaurant)result.getContent()).getIdDomainObject());
			return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+((Restaurant)result.getContent()).getIdDomainObject();
		}
	}
	
	
	
	
	@RequestMapping(path="ShowRestaurant", method=RequestMethod.GET)
	public ModelAndView showRestaurant( @RequestParam(value="idRestaurant", required=true) Long idRestaurant,@RequestParam(value="idFoodDish", required=false) Long dFoodDish,HttpServletRequest request){
		HttpSession session = request.getSession(false);
		ResultObject result= restaurantService.getRestaurantByIdForShow(idRestaurant);
		if (result.getResult()){//existe el restaurant
			if(!((Restaurant)result.getContent()).isLowlogic()){//esta activo
				ModelAndView view =new ModelAndView("Restaurant/ShowRestaurant","restaurant",(Restaurant)result.getContent());
				
				FoodDish foodDish = new FoodDish();
				foodDish.setRestaurant((Restaurant)result.getContent());
				view.addObject("newFoodDish",foodDish);
				Commentary commentary = new Commentary();
				commentary.setValoration( Valoration.NEUTRAL);
				view.addObject("comentary",commentary);
				
				int type=(Integer)session.getAttribute("typeUserLoged");
				System.out.println(type+"agregue la info del responsable++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				if(type>0){//hay que cargar la info del usuario
					User user = userService.GetUserById((Long)session.getAttribute("idUser"));
					view.addObject("user",user);
					for (Notification notification : user.getNotifications()){
						System.out.println(notification.getContent());
					}
					
					System.out.println("agregue la info del 00000000000++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				 if(type==2){//podria ser el responsable
					System.out.println("agregue la info del 22222222222++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					Set<Configuration> configurations = ((Restaurant)result.getContent()).getConfiguration();
					for (Configuration configuration : configurations) {
						if (configuration.getResponsible().getEmail().equals((String) session.getAttribute("userLoged"))
								&& (configuration.getLowLogic() == false)) {
							view.addObject("responsibleOf", "Responsible");
							view.addObject("configurationOf", configuration);
							System.out.println("agregue la info del responsable++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
						}
					}
				}
				}
				 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
				 if (inputFlashMap != null) {
					  view.addObject("message",inputFlashMap.get("message"));
					  view.addObject("error",inputFlashMap.get("error"));
					  view.addObject("redirectUrl",inputFlashMap.get("redirectUrl"));
				 }
				 System.out.println("entre por el existe y no esta dado de baja++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				return view;
			}else{//esta dado de baja
				int type=(Integer)session.getAttribute("typeUserLoged");
				if(type==2){//podria ser el responsable
					Set<Configuration> configurations = ((Restaurant)result.getContent()).getConfiguration();
					boolean canShow=false;
					for (Configuration configuration : configurations) {
						if (configuration.getResponsible().getEmail().equals((String) session.getAttribute("userLoged"))
								&& (configuration.getLowLogic() == false)) {//esta dado de baja el restaurant pero este usuario es responsable del mismo
							canShow=true;
							ModelAndView view =new ModelAndView("Restaurant/ShowRestaurant","restaurant",(Restaurant)result.getContent());
							
							FoodDish foodDish = new FoodDish();
							foodDish.setRestaurant((Restaurant)result.getContent());
							view.addObject("newFoodDish",foodDish);
							Commentary commentary = new Commentary();
							commentary.setValoration( Valoration.NEUTRAL);
							view.addObject("comentary",commentary);
							
							view.addObject("responsibleOf", "Responsible");
							view.addObject("configurationOf", configuration);
							
							 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
							 if (inputFlashMap != null) {
								  view.addObject("message",inputFlashMap.get("message"));
								  view.addObject("error",inputFlashMap.get("error"));
								  view.addObject("redirectUrl",inputFlashMap.get("redirectUrl"));
							 }
							 return view;
						}
					}
					if(!canShow){//esta dado de baja y no es responsible del restaurant

						ModelAndView view=new ModelAndView("Index");
						result.getMessages().put("ERROR IN CONTENT", "El contenido al que intenta acceder ha sido deshabilitado por sus creadores, disculpe las molestias");
						view.addObject("error", result.getMessages());
						 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
						 if (inputFlashMap != null) {
							  view.addObject("message",inputFlashMap.get("message"));
							  view.addObject("error",inputFlashMap.get("error"));
						  }
						return view;
						
					}
					
					
				}else{//restaurant dado de baja y no podria ser responsable
					ModelAndView view=new ModelAndView("Index");
					result.getMessages().put("ERROR IN CONTENT", "El contenido al que intenta acceder ha sido deshabilitado por sus creadores, disculpe las molestias");
					view.addObject("error", result.getMessages());
					 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
					 if (inputFlashMap != null) {
						  view.addObject("message",inputFlashMap.get("message"));
						  view.addObject("error",inputFlashMap.get("error"));
					  }
					return view;
				}
				
				
				
			}
		}else{//no existe el restaurant
			ModelAndView view=new ModelAndView("Index");
			result.getMessages().put("ERROR IN CONTENT", "El contenido al que intenta acceder no fue encontrado en el sistema");
			view.addObject("error", result.getMessages());
			 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			 if (inputFlashMap != null) {
				  view.addObject("message",inputFlashMap.get("message"));
				  view.addObject("error",inputFlashMap.get("error"));
			  }
			return view;
		}
		return null;
		
		

	

	}
	
	@RequestMapping("ShowRestaurants")
	public ModelAndView showRestaurants(){
		Restaurant restaurant = new Restaurant();
		//restaurant.setUbication(new Ubication());
		return new ModelAndView("Restaurant/ShowRestaurant","restaurant", restaurant);
	}
	
	@RequestMapping("SearchRestarantsAndFoodDish")
	public ModelAndView searchRestarantsAndFoodDish(){
		Restaurant restaurant = new Restaurant();
		//restaurant.setUbication(new Ubication());
		return new ModelAndView("Restaurant/ShowRestaurant","restaurant", restaurant);
	}
	
	
	
	@RequestMapping(path="DeleteResponsibleOf", method=RequestMethod.POST)
	public String DeleteResponsibleOf(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,@RequestParam(value="idResponsible", required=true) Long idResponsible,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=restaurantService.deleteThisResponsibleOfThisRestaurant(restaurant,idResponsible);
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha quitado al responsable de este restaurant");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
	}
	
	@RequestMapping(path="DeleteFoodDish", method=RequestMethod.POST)
	public String deleteFoodDish(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,@RequestParam(value="idFoodDishToDelete", required=true) Long idFoodDish,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=restaurantService.deleteThisFoodDishOfThisRestaurant(restaurant,idFoodDish,(Long)session.getAttribute("idUser"));
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha quitado este plato del menu del restaurant, le informamos que podra reactivarlo si lo desea en un futuro");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
	}
	
	@RequestMapping(path="ChangeFoodDish", method=RequestMethod.POST)
	public String changeFoodDish(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,@RequestParam(value="idFoodDishToChange", required=true) Long idFoodDish,
			@RequestParam(value="changeFoodDishModalNameNewInput", required=true) String newNameFoodDish,@RequestParam(value="changeFoodDishModalPriceNewInput", required=true) float newPriceFoodDish,@RequestParam(value="changeFoodDishModalDescriptionNewInput", required=true) String newDescriptionFoodDish,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=restaurantService.changeThisFoodDishOfThisRestaurant(restaurant,idFoodDish,(Long)session.getAttribute("idUser"),newNameFoodDish,newPriceFoodDish,newDescriptionFoodDish);
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha modificado la informacion del plato del menu");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject()+"&idFoodDish="+idFoodDish;
	}
	
	@RequestMapping(path="DeleteCommentaryOfFoodDish", method=RequestMethod.POST)
	public String deleteCommentaryOfFoodDish(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,@RequestParam(value="idCommentaryOfFoodDishToDelete", required=true) Long idCommentary,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		
		ResultObject result=foodDishService.deleteComentaryOfFoodDish(idCommentary,(String)request.getSession().getAttribute("userLoged"));
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha eliminado su comentario del plato");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
		
	}
	
	
	
	@RequestMapping(path="ChangeCommentaryOfFoodDish", method=RequestMethod.POST)
	public String changeCommentaryOfFoodDish(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,@RequestParam(value="idCommentaryOfFoodDishToChange", required=true) Long idCommentaryOfFoodDish,
			@RequestParam(value="changeCommentaryOfFoodDishModalDescriptionNewInput", required=true) String newDescriptionCommentaryOfFoodDish,@RequestParam(value="changeCommentaryOfFoodDishModalValorationNewInput", required=true) String newValorationCommentaryOfFoodDish,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result=foodDishService.changeComentaryOfFoodDish(idCommentaryOfFoodDish,(String)request.getSession().getAttribute("userLoged"),newDescriptionCommentaryOfFoodDish,newValorationCommentaryOfFoodDish);
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha modificado su comentario del plato");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
		
		
		
	}
	
	
	@RequestMapping(path="ReactiveFoodDish", method=RequestMethod.POST)
	public String reactiveFoodDish(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,@RequestParam(value="idFoodDishToReactive", required=true) Long idFoodDish,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		
		ResultObject result=restaurantService.reactivateFoodDishOfThisRestaurant(restaurant.getIdDomainObject(),idFoodDish,(Long)session.getAttribute("idUser"));
		

		
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha reactivado el plato exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
	}
	
	
	
	@RequestMapping(path="CommentRestaurant", method=RequestMethod.POST)
	public String commentRestaurant(@ModelAttribute("comentary")Commentary commentary,@RequestParam(value="idRestaurant", required=true) Long idRestaurant,@RequestParam(value="valorationOfNewCommentaryOfRestaurant", required=true) String valoration, HttpServletRequest request,RedirectAttributes atributos){
		
		if ((int)request.getSession().getAttribute("typeUserLoged")>0){
			ResultObject result=restaurantService.saveComentaryOfRestaurant(commentary,idRestaurant,valoration,(String)request.getSession().getAttribute("userLoged"));
			if(result.getResult()){
				atributos.addFlashAttribute("message", "Se ha agregado su comentario sobre Restaurant");
			}else{
				atributos.addFlashAttribute("error", result.getMessages());
			}
			
		}else{
			ResultObject result=restaurantService.getRestaurantByIdForShow(idRestaurant);
			result.getMessages().put("ERROR LOGIN DATOS NO VALIDOS", "Para comentar un restaurant debe loguearse primero");
			atributos.addFlashAttribute("error", result.getMessages());
			atributos.addFlashAttribute("redirectUrl","/Restaurant/ShowRestaurant?idRestaurant="+idRestaurant);
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+idRestaurant;
			
	}
	
	
	
	@RequestMapping(path="DeleteCommentaryOfRestaurant", method=RequestMethod.POST)//pendiente
	public String deleteCommentaryOfRestaurant(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,@RequestParam(value="idCommentaryOfRestaurantToDelete", required=true) Long idCommentary,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		
		ResultObject result=restaurantService.deleteComentaryOfRestaurant(idCommentary,(String)request.getSession().getAttribute("userLoged"),restaurant.getIdDomainObject());
		

		
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha eliminado su comentario del plato exitosamente exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();

		
	}
	
	//564
	@RequestMapping(path="ChangeCommentaryOfRestaurant", method=RequestMethod.POST)
	public String changeCommentaryOfRestaurant(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,@RequestParam(value="idCommentaryOfRestaurantToChange", required=true) Long idCommentaryOfRestaurant,
			@RequestParam(value="changeCommentaryOfRestaurantModalDescriptionNewInput", required=true) String newDescriptionCommentaryOfRestaurant, @RequestParam(value="changeCommentaryOfRestaurantModalValorationNewInput", required=true) String newValorationCommentaryOfRestaurant,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		ResultObject result= restaurantService.changeCommentaryOfRestaurant(idCommentaryOfRestaurant,newDescriptionCommentaryOfRestaurant,newValorationCommentaryOfRestaurant,(String)session.getAttribute("userLoged"));
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha modificado  su comentario del restaurant  exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
	}
	
	@RequestMapping(path="DeleteRestaurant", method=RequestMethod.POST)
	public String deleteRestaurant(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		

		ResultObject result= restaurantService.deactivateRestaurant(restaurant,(String)session.getAttribute("userLoged"));
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha deshabilitado el restaurant exitosamente recuerde que puede reactivarlo mas adelante si lo desea");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
	
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
	}
	
	@RequestMapping(path="ReactiveRestaurant", method=RequestMethod.POST)
	public String reactiveRestaurant(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		

		ResultObject result= restaurantService.reactivateRestaurant(restaurant,(String)session.getAttribute("userLoged"));
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha rehabilitado el restaurant exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
	}
	
	@RequestMapping(path="ChangeInformationOfRestaurant", method=RequestMethod.POST)
	public String changeInformationOfRestaurant(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request, @RequestParam(value="addresToChange", required=true) String addresToChange,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		

		ResultObject result= restaurantService.changeInformationOfRestaurant(restaurant,addresToChange,(String)session.getAttribute("userLoged"));
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha modificado la informacion del restaurant exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
	}
	
	
	
	@RequestMapping(path="ShareFoodDish", method=RequestMethod.POST)
	public String shareFoodDish(@ModelAttribute("restaurant")Restaurant restaurant, HttpServletRequest request, @RequestParam(value="idFoodDishToShare", required=true) Long idFoodDishToShare,@RequestParam(value="descriptionToShareFoodDish", required=true) String descriptionToShareFoodDish,@RequestParam(value="friendsToShareFoodDish", required=false) Long[] friendsToShareFoodDish,RedirectAttributes atributos){
		HttpSession session = request.getSession(false);
		

		ResultObject result= foodDishService.ShareFoodDish(restaurant,idFoodDishToShare,descriptionToShareFoodDish,friendsToShareFoodDish,(String)session.getAttribute("userLoged"));
		if(result.getResult()){
			atributos.addFlashAttribute("message", "Se ha compartido el plato exitosamente");
		}else{
			atributos.addFlashAttribute("error", result.getMessages());
		}
		return "redirect:/Restaurant/ShowRestaurant?idRestaurant="+restaurant.getIdDomainObject();
	}
	
	
	
	@RequestMapping(value = "Search/{partOfName}/{distance}/{latitude}/{longitude}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Restaurant>> SearchRestaurantsByDistance(@PathVariable("partOfName") String partOfName,@PathVariable("distance") int distance,@PathVariable("latitude") double latitude,@PathVariable("longitude") double longitude) {
		if(partOfName.equals("---")){
			partOfName="";
		}
		
		
		List<Restaurant> restaurants = restaurantService.getRestaurantsInProximity(partOfName,distance,latitude,longitude);
		
		Ubication ubication=new Ubication();
		ubication.setLatitude(latitude);
		ubication.setLongitude(longitude);
		List<Restaurant> restaurantsResult=restaurants.stream().filter(rest-> rest.getUbication().compareLessThan(ubication, distance)).collect(Collectors.toList());
	if(restaurants.isEmpty()){
		return new ResponseEntity<List<Restaurant>>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<List<Restaurant>>(restaurantsResult, HttpStatus.OK);
	}
	
	@RequestMapping(value = "Search/{partOfName}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<DomainObject>> SearchRestaurantsAndFoodDishByNAme(@PathVariable("partOfName") String partOfName) {
		
		List<DomainObject> domainObjects = restaurantService.getRestaurantsAndFoodDishLikeWords(partOfName);
	if(domainObjects.isEmpty()){
		return new ResponseEntity<List<DomainObject>>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<List<DomainObject>>(domainObjects, HttpStatus.OK);
	}
	
	
}