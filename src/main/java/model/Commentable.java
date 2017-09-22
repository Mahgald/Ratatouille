package model;

import java.util.List;

/**
 * @class Commentable 
 * 
 * Used to represent the commentable interface
 * 
 * @author Grupo7
 */
public interface Commentable {
	abstract public Boolean comment(User user, String description, Valoration valoration);
	abstract public List<Commentary> getComments(); 
	
	
}
