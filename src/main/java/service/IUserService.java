package service;

import model.User;

public interface IUserService {
	public boolean existByMail(String mail);
	public User GetUserWithThisMail(String mail);
	public boolean deleteUser(User user) ;
	public User GetUserById(Long attribute);
}
