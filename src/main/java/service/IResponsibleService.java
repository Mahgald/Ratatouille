package service;

import model.Responsible;
import model.User;

public interface IResponsibleService {

	
	
	public boolean existByMail(String mail);
	public Responsible getEntityByMail(String mail);
	
}
