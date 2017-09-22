package model;

import javax.persistence.*;

/**
 * @class NotificableSpecial 
 * 
 * Used to represent Notification
 *  
 * @author Grupo7
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Notification")
public class Notification implements  Comparable<Object>, Active{//Active

	/**
	 * Represents the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idNotification;
	
	/**
	 * Represents the content
	 */
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval = true)
	protected DomainEvent content;
	
	/**
	 * Represents if the notification was viewed
	 */
	@Column(name="viewed")
	protected boolean viewed=false;
	
	/**
	 * Represents lowlogic
	 */
	@Column(name="lowLogic")
	private Boolean lowLogic;
	
	/**
	 * Default constructor
	 */
	public Notification(){}
	
	/**
	 * Constructor with params
	 * 
	 * @param contenido
	 */
	public Notification(DomainEvent contenido){
		this.setContent(contenido);
		this.setViewed(false);
	}

	
	public Object goTo(){
		this.viewed();
		return ( this.getContentWithoutSee()).goToView();
	}
	public Object show(){
		this.viewed();
		return this.getContent();
	}
	
	private void viewed(){
		this.setViewed(true);
	}

	protected Notificable getContentWithoutSee(){
		Notificable content=null;
		return content;
	}
	
	@Override
	public int compareTo(Object notification) {
		return ((Notificable) this.getContentWithoutSee()).getDate().compareTo(((Notificable) ((Notification) notification).getContentWithoutSee()).getDate());
	}

	@Override
	public Boolean isActive() {
		return null;
	}

	@Override
	public void unsubscribe() {
	}
	
	@Override
	public void reSubscribe() {}
	
	@Override
	public Boolean getActive() {
		return null;
	}

	@Override
	public void setActive(Boolean active) {
	}
	

	// GETTERS AND SETTERS
		
	public Boolean getLowLogic() {
		return lowLogic;
	}
	public void setLowLogic(Boolean lowLogic) {
		this.lowLogic = lowLogic;
	}
	public Long getIdNotification() {
		return idNotification;
	}
	public void setIdNotification(Long idNotification) {
		this.idNotification = idNotification;
	}
	public DomainEvent getContent() {
		return content;
	}
	public void setContent(DomainEvent content) {
		this.content =  content;
	}
	public boolean isViewed() {
		return viewed;
	}
	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}	
}
