package model;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @class Responsible 
 * 
 * Used to represent the responsible of restaurant, this class inherits from User
 * 
 * @see User 
 * @see Restaurant
 *  
 * @author Grupo7
 */
@Entity
@Table(name="Responsible")
public class Responsible extends User implements Observer {
	
	/**
	 * Represents the date from user become to responsible
	 * */
	@Column(name="dateMakeResponsible")
	private LocalDateTime dateMakeResponsible;
	
	/**
	 * Is a set of configurations to each restaurant where is responsible
	 * */
	@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER,mappedBy="responsible", cascade=CascadeType.ALL)
	private Set<Configuration> configurations = new HashSet<>();
		
	
	/**
	 * Default constructor
	 * */
	public Responsible(){}

	
	/**
	 * Constructor for responsible, is protected because it is only called by the class User
	 * @param user
	 */
	protected Responsible(User user){
		super(user);
		this.setDateMakeResponsible(LocalDateTime.now());
	}

	/**
	 * @param user
	 * @param responsible
	 * @deprecated Don't need call this method. the logical of this method are move to constructor {@link User#User(User) User(User)} of User's class
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void areConsistence(User user, Responsible responsible) {
		user.getRanking().setUser(this);
		
		user.getFriends().stream().forEach(x->x.getFriends().remove(user));
		user.getFriends().stream().forEach(x-> x.getFriends().add(responsible));
		user.getCommentaries().stream().forEach(x-> x.setUser(responsible));
		user.getReceived().stream().forEach(x->x.setRecepter(responsible));
		user.getRequestsSubmitted().stream().forEach(x->x.setSender(responsible));
		user.getSharedElement().stream().forEach(x->x.setUserShare(responsible));
		
		user.getSharedElementToMe().stream().forEach(x->x.getUsersToShare().remove(user));
		user.getSharedElementToMe().stream().forEach(x->x.getUsersToShare().add(responsible));
		
	}
	
	/**
	 * Becomes in an responsible of other restaurant
	 * @param restaurant
	 * @return Himself
	 */
	@Override
	public Responsible becomeResposibleOf(Restaurant restaurant){
		this.addResponsibleOf(restaurant);
		return this;
	}

	/**
	 * Add, this responsible, to the list of responsible of this restaurant, and add, this restaurant, to the list of restaurants of this responsible
	 * @param restaurant
	 */
	public void addResponsibleOf(Restaurant restaurant){
		Configuration configuration =new Configuration(this,restaurant);
		this.getConfigurations().add(configuration);
	}
	
	/**
	 * Return true if all restaurants of this responsible are more than 1 km of this restaurant, otherwise return false
	 * @param restaurant
	 * @return Boolean
	 */
	public Boolean canComent(Restaurant restaurant){
		return this.getConfigurations().stream().allMatch(x->x.getRestaurant().sufficientDistanceOf(restaurant));
	}
	
	/**
	 * Returns the configuration of an specific restaurant
	 * @param restaurant
	 * @return Configuration
	 */
	public Configuration getConfigurationTo(Restaurant restaurant) {
		return this.getConfigurations().stream().filter(x->x.isConfigurationTo(restaurant)).findFirst().get();
	}
	
	/**
	 * Adds a new notification
	 * @param notification
	 */
	@Override
	public void update(Observable o, Object notification) {
		this.getNotifications().add((Notification) notification);
	}

	/**
	 * Adds a new configuration filter in the configurations set
	 * @param restaurant
	 * @param filter
	 */
	public void configurateNotifications(Restaurant restaurant,Filter filter){
		this.getConfigurationTo(restaurant).setFilter(filter);
	}
	
	/**
	 * 
	 * @return false
	 */
	public boolean haveRestaurant() {
		return true;
	}
	
	
	@Override
	public Boolean isActive() {
		return null;
	}

	@Override
	public void unsubscribe() {
		
	}
	@Override
	public void reSubscribe() {
		// TODO Auto-generated method stub
	}
	
	
	// GETTERS AND SETTERS
	
	public LocalDateTime getDateMakeResponsible() {
		return dateMakeResponsible;
	}

	private void setDateMakeResponsible(LocalDateTime localDateTime) {
		this.dateMakeResponsible = localDateTime;
	}
	
	public void setConfigurations(Set<Configuration> configurations) {
		this.configurations = configurations;
	}

	public Set<Configuration> getConfigurations() {
		return configurations;
	}
	
}