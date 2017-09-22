package model;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

/**
 * @class Commentary 
 * 
 * Used to represent the commentary  
 *  
 * @author Grupo7
 */
@Entity
@Proxy(lazy=false)
@Table(name="Commentary")
public class Commentary extends DomainEvent implements Comparable<Commentary>{//Active implements notificable

	/**
	 * Represents the commentary description
	 * */
	private String description;
	
	/**
	 * Represents the creation date
	 * */
	private LocalDateTime date;
	
	/**
	 * Represents user who made the commentary
	 * */
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
	private User user;
	
	/**
	 * Represents the commentary valoration
	 * */
	private Valoration valoration;
	
	/**
	 * Represents the commentary content, in this case can be a restaurant or food dish
	 * 
	 * @see DomanOnject
	 * */
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
	private DomainObject content;
	
	/**
	 * Default constructor
	 * */
	public Commentary(){

	}
	/**
	 * Constructor with params
	 * 
	 * @param user
	 * @param description
	 * @param valoration
	 * @param content
	 */
	public Commentary(User user, String description, Valoration valoration,DomainObject content){
		this.setUser(user);
		this.setDescription(description);
		this.setValoration(valoration);
		this.setDate(LocalDateTime.now());
		this.setContent(content);
		content.getComments().add(this);
		this.addCommentToUser(user);
	}
		
	/**
	 * Adds the commentary to the user who do it
	 * @param user
	 */
	protected void addCommentToUser(User user){
		user.addComentary(this);
	}

	/**
	 * this method is override on the son, commentrestaurant
	 * @return
	 */
	public boolean ofRestaurant(){
		return false;
	}
	
	/**
	 * Delete this commentary
	 */
	public void delete() {
		this.getUser().removeCommentary(this);
		this.getContent().getComments().remove(this);
	}
	

	@Override
	public int compareTo(Commentary comentary) {
		return this.getDate().compareTo(comentary.getDate());
	}
	
	@Override
	public Notificable goToView() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String showReduced() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//GETTERS AND SETTERS
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Valoration getValoration() {
		return valoration;
	}
	public void setValoration(Valoration valoration) {
		this.valoration = valoration;
	}
	public DomainObject getContent() {
		return content;
	}
	public void setContent(DomainObject content) {
		this.content = content;
	}
}
