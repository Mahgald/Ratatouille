package model;

import java.time.LocalDateTime;
import javax.persistence.*;

import org.hibernate.annotations.Proxy;


/**
 * @class FriendRequest 
 * 
 * Used to represent the user friend request
 *  
 * @see User 
 *  
 * @author Grupo7
 */
@Entity
@Proxy(lazy=false)
@Table(name="FriendRequest")
public class FriendRequest extends DomainEventSpecial implements NotificableSpecial{//extends Suscribtable?
	
	/**
	 * Represents the user who send the request
	 */
	@ManyToOne(fetch=FetchType.EAGER ,cascade=CascadeType.ALL)
	private User sender;
	
	/**
	 * Represents the user who receive the request
	*/
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private User recepter;
	
	/**
	 * Represents the status of request if was accepted o rejected
	*/
	@Column(name="status")	
	private boolean requestStatus;//necesitariamos una variable mas para implementarlo bien, tbn podria meter un state
	
	/**
	 * Represents if was evaluated the friend request
	 */
	@Column(name="requestEvaluated")
	public boolean requestEvaluated=false; 
	
	/**
	 * Represents the date where the request was send
	*/
	@Column(name="dateRequest")
	private LocalDateTime dateRequest;
	
	/**
	 * Represents the date where the request was reject if it wasn't
	*/
	@Column(name="dateReject")
	private LocalDateTime dateReject;
	
	/**
	 * Represents the date where the request was accepted if it wasn't
	*/
	@Column(name="dateOfFriendShip")
	private LocalDateTime dateOfFriendShip;
	
	/**
	 * Default constructor
	 */
	public FriendRequest(){}
	
	/**
	 * Constuctor with param
	 * @param sender
	 * @param recepter
	 */
	public FriendRequest(User sender,User recepter){
		this.setSender(sender);
		this.setRecepter(recepter);
		this.setDateRequest(LocalDateTime.now());
		recepter.getReceived().add(this);
		recepter.getNotifications().add(new SpecialNotification(this));
	}
	
	/**
	 * User confirm the friend request
	 * 
	 * @param user
	 * @return
	 */
	public boolean confirm(User user){
		if (this.getRecepter().areYou(user.getEmail())){
			this.setRequestEvaluated();
			this.setRequestStatus(true);
			this.getSender().getFriends().add(this.getRecepter());
			this.getRecepter().getFriends().add(this.getSender());
			this.setDateOfFriendShip(LocalDateTime.now());
			this.getSender().getNotifications().add(new SpecialNotification(this));
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * @deprecated {@link #confirm(User) confirm(User) } is recommended instead. The new method verifies that the user that confirms is the receiving user
	 */
	@Deprecated
	public void confirm(){
		this.setRequestStatus(true);
		this.getSender().getFriends().add(this.getRecepter());
		this.getRecepter().getFriends().add(this.getSender());
		this.setDateOfFriendShip(LocalDateTime.now());
		this.getSender().getNotifications().add(new SpecialNotification(this));
	}
	
	/**
	 * User reject the friend request
	 * 
	 * @param user
	 * @return
	 */
	public boolean reject(User user){
		if (this.getRecepter().areYou(user.getEmail())){
			this.setRequestEvaluated();
			this.setRequestStatus(false);
			this.setDateReject(LocalDateTime.now());
			this.getSender().getNotifications().add(new SpecialNotification(this));
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @deprecated {@link #confirm(User) confirm(User) } is recommended instead. The new method verifies that the user that rejects is the receiving user
	 */
	@Deprecated
	public void reject(){
		this.setRequestStatus(false);
		this.setDateReject(LocalDateTime.now());
		this.getSender().getNotifications().add(new SpecialNotification(this));
	}
	
	/**
	 * Cancel the request
	 * @param user
	 */
	public void cancel(User user){
		if (this.getSender().areYou(user.getEmail())){
			
		}
	}
	
	@Override
	public	Notificable goToView() {
		return this;
	}

	@Override
	public	String showReduced() {
		String asText="El usuario: "+this.getRecepter().getName()+", "+this.getRecepter().getLastName()+" te acepto como amigo ";
		return asText;
	}

	@Override
	public LocalDateTime getDate() {
			return this.getDateOfFriendShip();
	}

	@Override
	public LocalDateTime getDateSpecial() {
		return this.getDateRequest();
	}

	@Override
	public String showReducedSpecial() {
		String asText="El usuario: "+this.getSender().getName()+", "+this.getSender().getLastName()+" te quiere agregar como amigo ";
		return asText;
	}
	

	//GETTERS AND SETTERS
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getRecepter() {
		return recepter;
	}
	public void setRecepter(User recepter) {
		this.recepter = recepter;
	}
	public boolean isRequestStatus() {
		return requestStatus;
	}
	public boolean getRequestStatus() {
		return requestStatus;
	}
	private void setRequestStatus(boolean requestStatus) {
		this.requestStatus = requestStatus;
	}
	public LocalDateTime getDateRequest() {
		return dateRequest;
	}
	private void setDateRequest(LocalDateTime localDate) {
		this.dateRequest = localDate;
	}
	public LocalDateTime getDateReject() {
		return dateReject;
	}
	private void setDateReject(LocalDateTime dateReject) {
		this.dateReject = dateReject;
	}
	public LocalDateTime getDateOfFriendShip() {
		return dateOfFriendShip;
	}
	private void setDateOfFriendShip(LocalDateTime dateOfFriendShip) {
		this.dateOfFriendShip = dateOfFriendShip;
	}
	public boolean getRequestEvaluated(){
		return this.requestEvaluated;
	}
	private void setRequestEvaluated(){
		this.requestEvaluated=true;
	}

	
	
	
}
