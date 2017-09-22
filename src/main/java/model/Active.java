package model;

/**
 * @class Active 
 * 
 * Used to represent the active interface
 * 
 * @author Grupo7
 */
public interface Active {
	public Boolean isActive();
	public void unsubscribe();
	public void reSubscribe();
	
	public Boolean getActive();
	public void setActive(Boolean active);
}
