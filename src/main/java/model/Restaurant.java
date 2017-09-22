package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @class Restaurant 
 * 
 * Used to represent the Restaurant
 * 
 * @author Grupo7
 */
@Entity
@Table(name="Restaurant")
public class Restaurant extends DomainObject {

	
	/**
	 * Represents the restaurant name
	 * */
	@Column(name="name")
	private String name;
	
	/**
	 * Represents the restaurant ubication
	 * 
	 * @see Ubication
	 */
	@OneToOne(cascade=CascadeType.ALL)
	private Ubication ubication;
	
	/**
	 * Represents the restaurant menu as a food dish list
	 * 
	 * @see FoodDish
	 */
	@JsonBackReference
	@OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FoodDish> menu = new ArrayList<>();
	
	/**
	 * Represents the restaurant commentaries as a commentary list
	 * 
	 * @see Commentary
	 */
	@JsonIgnore
	@OneToMany(mappedBy="content", cascade=CascadeType.ALL,orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Commentary> commentaries = new ArrayList<>();
	
	/**
	 * Represents the restaurant category
	 * 
	 * @see RestaurantCategory
	 */
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "restaurant")
	private RestaurantCategory category;
	
	/**
	 * Represents the restaurant configurations as a configuration set
	 * 
	 * @see Configuration
	 */
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,mappedBy="restaurant", cascade=CascadeType.ALL)
	private Set<Configuration> configuration = new HashSet<>();
	
	/**
	 * Represents the restaurant creation date
	*/
	@Column(name="dateCreation")
	private LocalDateTime dateCreation;
	
	/**
	 * 
	 * @param name
	 * @param ubication
	 * @return
	 */
	public Boolean areYou(String name, Ubication ubication) {
		return (this.getName()==name && this.getUbication()==ubication);
	}
	
	/**
	 * Constructor used to create a new restaurant
	 * @param name
	 * @param ubication
	 */
	public Restaurant(String name, Ubication ubication){
		this.dateCreation= LocalDateTime.now();
		this.name = name;
		this.ubication = ubication;
		this.category=new Silver(this);
	}
	
	/**
	 * Default constructor 
	 */
	public Restaurant() {
		this.dateCreation= LocalDateTime.now();
		this.category=new Silver(this);
		this.ubication = new Ubication();
	}

	/**
	 * Adds a new food dish in menu
	 * @param name
	 * @param price
	 * @param description
	 * @return true if can add the food dish
	 * @return false if can't because already exist
	 */
	public Boolean addFoodDish(String name,float price,String description){
		if(this.getMenu().stream().anyMatch(x->x.areYou(name))){
			return false;
		}else{
			new FoodDish(name,price,description,this);
			return true;
		}
	}
	
	/**
	 * Recalculates the restaurant category
	 * @see RestaurantCategory
	 */
	@Override
	public boolean calculateCategory() {
		this.setCategory(this.getCategory().calculateCategory());
		return true;
	}

	@Override
	public Long getIdRestaurant(){
		return this.getIdDomainObject();
	}
	
	
	/**
	 * If the user not is a responsible of this restaurant, and not have a restaurant  at less 1km that restaurant, create a new coment for this restaurant
	 * @param user
	 * @param description
	 * @param valoration
	 * @return boolean
	 */
	public Boolean comment(User user, String description, Valoration valoration){
		if ((!(user.haveRestaurant()))||(((Responsible)user).canComent(this))){
				Commentary coment =new Commentary(user,description,valoration,this);
				this.getConfiguration().stream().filter(x->x.filter(coment)).forEach(x->x.getResponsible().getNotifications().add(new Notification(coment)));
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns true if both restaurants are more than 1 km, otherwise return false
	 * @param restaurant
	 * @return boolean
	 */
	public Boolean sufficientDistanceOf(Restaurant restaurant) {
		return this.getUbication().compareMoreThan1Km(restaurant.getUbication());
	}
	
	/**
	 * Converts a user set into restaurant responsible
	 * @param users
	 * @return a set of responsible
	 */
	public Set<Responsible> addResponsibles(Set<User> users){
		Set<Responsible> responsibles = new HashSet<>();
		for (User user: users){
			responsibles.add(user.becomeResposibleOf(this));
		}
		return responsibles;
	}
	
	public Boolean isActive() {
		return null;
	}

	public void unsubscribe() {
	}

	public void reSubscribe() {
	}

	@Override
	public void share(User userSharing, String message, Set<User> shareToUser) {
	}

	@Override
	public Long getIdShareable() {
		return null;
	}

	@Override
	public void setIdShareable(Long id) {
	}
	
	@JsonIgnore
	@Override
	public List<Commentary> getComments() {
		return commentaries;
	}

	@Override
	public Boolean getActive() {
		return null;
	}

	@Override
	public void setActive(Boolean active) {
	}
	
	/**
	 * Seek if the param user is responsible of this
	 * 
	 * 
	 */
	@JsonIgnore
	public boolean areResponsible(User user){
		for (Configuration configuration: this.getConfiguration()){
			if(configuration.getResponsible().getId().equals(user.getId())){
				return true;
			}
		}
		return false;
	}
	
	// GETTERS AND SETTERS
	
	public void setConfiguration(Set<Configuration> configuration){
		this.configuration=configuration;
	}
	public Set<Configuration> getConfiguration(){
		return this.configuration;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Ubication getUbication() {
		return ubication;
	}
	public void setUbication(Ubication ubication) {
		this.ubication = ubication;
	}
	public List<FoodDish> getMenu() {
		return menu;
	}
	public void setMenu(List<FoodDish> menu) {
		this.menu = menu;
	}
	public List<Commentary> getCommentaries() {
		return commentaries;
	}
	public void setCommentaries(List<Commentary> comments) {
		this.commentaries = comments;
	}
	public RestaurantCategory getCategory() {
		return category;
	}
	public void setCategory(RestaurantCategory category) {
		this.category = category;
	}
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	

	
	
}
