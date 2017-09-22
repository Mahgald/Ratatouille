package model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;

/**
 * @class SharedElement 
 * 
 * Used to represent the shared element
 * 
 * @author Grupo7
 */
@Entity
@Table(name="SharedElement")
public class SharedElement extends DomainEvent implements Notificable{
	
	/**
	 * Represents the message in the element
	*/
	@Column(name="message")
	private String message;
	
	/**
	 * Represents the date when something was shared
	 * */
	@Column(name="sharedDate")
	private LocalDateTime sharedDate;
	
	/**
	 * Represents the content shared
	 * */
	@OneToOne(cascade=CascadeType.ALL)
	private DomainObject content;
	
	/**
	 * Represents user who shared the element
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private User userShare;
	
	/**
	 * represents the users who receive shared the element 
	 */
	@ManyToMany( cascade=CascadeType.ALL)
	private Set<User> usersToShare;
	
	/**
	 * Default constructor
	 */
	public SharedElement(){}

	/**
	 * Constructor with param
	 * 
	 * @param user
	 * @param usersToShare
	 * @param foodDish
	 * @param message
	 */
	public SharedElement(User user,Set<User> usersToShare, FoodDish foodDish, String message){
		this.setContent(foodDish);
		this.setUsersToShare(usersToShare);
		this.setUserShare(user);
		this.setMessage(message);
		this.setSharedDate(LocalDateTime.now());
		user.getSharedElement().add(this);
		usersToShare.stream().forEach(x->x.getSharedElementToMe().add(this));
		usersToShare.stream().forEach(x->x.getNotifications().add(new Notification(this)));
	}
	
	public void deleteSharedFoodDish(){
		
	}
	
	@Override
	public Notificable goToView() {
		return this;
	}

	@Override
	public String showReduced() {
		String asText="El usuario: "+this.getUserShare().getName()+", "+this.getUserShare().getLastName()+" compartio algo contigo/n Plato:  "+this.getContent();
		return asText;
	}
	
	//GETTERS AND SETTERS
	
	public void setContent(DomainObject content) {
		this.content = content;
	}
	public User getUserShare() {
		return userShare;
	}
	public void setUserShare(User userShare) {
		this.userShare = userShare;
	}
	public Set<User> getUsersToShare() {
		return usersToShare;
	}
	public void setUsersToShare(Set<User> usersToShare) {
		this.usersToShare = usersToShare;
	}
	public LocalDateTime getSharedDate() {
		return sharedDate;
	}
	public void setSharedDate(LocalDateTime localDateTime) {
		this.sharedDate = localDateTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getDate() {
		return this.getSharedDate();
	}
	public DomainObject getContent() {
		return content;
	}
}
