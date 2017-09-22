package service;

import org.springframework.beans.factory.annotation.Autowired;

import dao.ResponsibleDao;
import dao.UserDao;
import model.Responsible;
import model.User;

public class ResponsibleService implements IResponsibleService{

	@Autowired
	ResponsibleDao responsibleDAO;
	@Autowired
	UserService userService;
	
	
	public boolean existByMailOrUpgrade(String mail) {
		if (responsibleDAO.existWithThisMail(mail)){
			return true;
		}else if(userService.existByMail(mail)){
			
			// TODO
			User user= userService.GetUserWithThisMail(mail);
			
			
			Responsible responsible =user.becomeResposible();
			
			responsibleDAO.persist(responsible);
			userService.deleteUser(user);
			
			return true;
		}else{
			return false;
			}
	}
	
	public boolean existByMail(String mail) {
		if (responsibleDAO.existWithThisMail(mail)){
			return true;
		}else {
			return false;
			}
	}

	
	public Responsible getEntityByMail(String mail) {
		
		return responsibleDAO.getEntityByMail(mail);
		
		
	}

	
	
}
