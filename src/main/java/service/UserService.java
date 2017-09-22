package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import dao.UserDao;
import helpers.ResultObject;
import model.FriendRequest;
import model.User;

public class UserService implements IUserService{

	public static final String SALT= "Ratatouille";
	
	@Autowired
	UserDao userDAO;
	
	@Autowired
	FriendRequestService friendRequestService;
	
	public void update(User user){
		userDAO.update3(user);
		
	}
	
	
	
	public boolean existByMail(String mail) {
		if (userDAO.existWithThisMail(mail)){
			return true;
		}else{
			return false;
		}
	}

	
	
	
	public User GetUserWithThisMail(String mail) {
		
		return userDAO.getEntityByMail(mail);
	}

	public boolean deleteUser(User user) {
		
		userDAO.delete(user.getId());
		return true;
	}
	
	public ResultObject logInUser(User user){
		ResultObject resultObject=new ResultObject();
		user.setPassword(this.generateHash(SALT + user.getPassword()));
		if(userDAO.isAccessData(user)){
			resultObject.setResult(true);
			resultObject.setContent(this.GetUserWithThisMail(user.getEmail()));
		}else{
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR LOGIN DATOS NO VALIDOS", "El mail o el password ingresados son incorrectos");
		}
		return resultObject;
	}
	

	

	public void saveNewUser(User user) {
		user.setPassword(this.generateHash(SALT + user.getPassword()));
		userDAO.persist(user);
	}
	
	private String generateHash(String input) {
		StringBuilder hash = new StringBuilder();
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}
		return hash.toString();
	}

	public List<User> getUsersLikeWords(String words) {
		return userDAO.listOfUserMatchWhitThisWords(words);
	}

	public User GetUserById(Long idUser) {
		// TODO Auto-generated method stub
		return userDAO.getEntity(idUser);
	}


	public ResultObject createNewUser(User user) {
		ResultObject resultObject=new ResultObject();
		
		if(this.existByMail(user.getEmail())){
			resultObject.getMessages().put("ERROR IN CONTENT", "Ya hay un usuario registrado con ese E-Mail");
			resultObject.setResult(false);
		}else{
			if (user.getBirthdate().getYear()> LocalDate.now().getYear()){
				user.setBirthdate(user.getBirthdate().withYear(user.getBirthdate().getYear()-100));
			}
			this.saveNewUser(user);
			resultObject.setResult(true);
		}
		return resultObject;
	}

	public ResultObject deactivateAccount(User userToModified) {
		// TODO Auto-generated method stub
		ResultObject result=new ResultObject();
		User user = this.GetUserById(userToModified.getId());
		user.setLowLogic(true);
		userDAO.update(user);
		result.setResult(true);
		
		return result;
	}

	public ResultObject reactivateAccount(User userToModified) {
		// TODO Auto-generated method stub
		ResultObject result=new ResultObject();
		User user = this.GetUserById(userToModified.getId());
		user.setLowLogic(false);
		userDAO.update(user);
		result.setResult(true);
		return result;
	}

	public ResultObject changeInformationOfAccount(User userToModified) {
		ResultObject result=new ResultObject();
		User user = this.GetUserById(userToModified.getId());
		if (user.getEmail().equals(userToModified.getEmail())){
			user.setLastName(userToModified.getLastName());
			user.setName(userToModified.getName());
			user.setBirthdate(userToModified.getBirthdate());
			userDAO.update(user);
			result.setResult(true);
		}else if(!this.existByMail(userToModified.getEmail())){
			user.setEmail(userToModified.getEmail());
			user.setLastName(userToModified.getLastName());
			user.setName(userToModified.getName());
			user.setBirthdate(userToModified.getBirthdate());
			userDAO.update(user);
			result.setResult(true);
		}else{
			result.setResult(false);
			result.getMessages().put("ERROR MODAL CHANGE INFORMATION OF USER", "El nuevo mail ingresado ya esta registrado en el sistema");
		}
		return result;
	}

	public ResultObject changePasswordOfAccount(User userToModified, String oldPassword, String newPassword,
			String reNewPassword) {
		ResultObject result=new ResultObject();
		if (newPassword.equals(reNewPassword)){
			User user = this.GetUserById(userToModified.getId());
			if (user.getPassword().equals(this.generateHash(SALT + oldPassword))){
				user.setPassword(this.generateHash(SALT + newPassword));
				userDAO.update(user);
				result.setResult(true);
				result.getMessages().put("message","la clave de su cuenta ha sido actualizada exitosamente");
			}else{
				result.setResult(false);
				result.getMessages().put("ERROR MODAL CHANGE PASSWORD OF USER","Su antigua clave no coincide con la que ingreso");
				}
		}else{
			result.setResult(false);
			result.getMessages().put("ERROR MODAL CHANGE PASSWORD OF USER","La nueva clave y la confirmacion de la nueva clae no coinciden");
		}
		return result;
	}

	public ResultObject sendFriendRequest(String emailOfSender, Long idOfUserToSendFriendRequest) {
		// TODO Auto-generated method stub
		ResultObject result=new ResultObject();
		User user = this.GetUserWithThisMail(emailOfSender);
		User newFriend = this.GetUserById(idOfUserToSendFriendRequest);
		user.SendFriendRequest(newFriend);
		userDAO.update3(newFriend);
		result.setResult(true);
		result.getMessages().put("message", "su solicitud de amistad ha sido enviada exitosamente");
		return result;
	}

	public ResultObject acceptFriendRequest(String emailOfUserLoged, Long idOfFriendRequest) {
		ResultObject result=new ResultObject();
		User user = this.GetUserWithThisMail(emailOfUserLoged);
		ResultObject internalResult=friendRequestService.acceptFriendRequest(idOfFriendRequest,user);
		if (internalResult.getResult()){
			result.setResult(true);
			result.getMessages().put("message", "Ha acceptado la solicitud de amistad exitosamente");
		}else{
			result.setResult(false);
			result.setMessages(internalResult.getMessages());
		}
		return result;
	}

	public ResultObject rejectFriendRequest(String emailOfUserLoged, Long idOfFriendRequest) {
		ResultObject result=new ResultObject();
		User user = this.GetUserWithThisMail(emailOfUserLoged);
		ResultObject internalResult=friendRequestService.rejectFriendRequest(idOfFriendRequest,user);
		if (internalResult.getResult()){
			result.setResult(true);
			result.getMessages().put("message", "Ha rechazado la solicitud de amistad exitosamente");
		}else{
			result.setResult(false);
			result.setMessages(internalResult.getMessages());
		}
		return result;
	}



	public void clearInconsistenceRanking(User user) {
		
		userDAO.normalceInconsistenceRanking(user);
	
		
	}



	public void updateAndAreConsistence(User user) {
		userDAO.update(user);
		this.clearInconsistenceRanking(user);
		
	}



	public List<User> getActiveUsers() {
		// TODO Auto-generated method stub
		return userDAO.getActiveUsers();
	}



	public List<User> getUsers() {
		return userDAO.list();
		
	}
	
	
	
	
	
	
}

	