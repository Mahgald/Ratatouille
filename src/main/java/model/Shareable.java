package model;

import java.util.Set;

/**
 * @class Shareable 
 * 
 * Used to represent the shareable interface
 * 
 * @author Grupo7
 */
public interface Shareable {
	abstract public void share(User userSharing, String message, Set<User> shareToUser);
	public Long getIdShareable();
	public void setIdShareable(Long id);
	
}
