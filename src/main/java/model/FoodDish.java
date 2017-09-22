package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Proxy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @class FoodDish 
 * 
 * Used to represent the food dish
 *
 * @author Grupo7
 */
@Entity
@Proxy(lazy=false)
@Table(name="FoodDish")
public class FoodDish  extends DomainObject {//implements Active
	
	/**
	 * Represents the name
	 */
	@Column(name="name")
	private String name;
	
	/**
	 * Represents the price
	 */
	@Column(name="price")
	private float price;
	
	/**
	 * Represents the description
	 */
	@Column(name="description")
	private String description;
	
	/**
	 * Represents the restaurant who have this food dish in his menu
	 */
	@JsonManagedReference
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Restaurant restaurant;
	
	/**
	 * Represents the comments received
	 */
	@JsonIgnore
	@OneToMany(fetch=FetchType.EAGER,mappedBy="content", cascade=CascadeType.ALL,orphanRemoval = true)
	private List<Commentary> commentaries = new ArrayList<>();
	
	
	/**
	 * Default constructor
	 */
	public FoodDish(){	}
	
	/**
	 * Constructor with param
	 * @param name
	 * @param price
	 * @param description
	 * @param restaurant
	 */
	public FoodDish(String name, float price, String description,Restaurant restaurant){
		this.setName(name);
		this.setPrice(price);
		this.setDescription(description);
		this.setRestaurant(restaurant);
		restaurant.getMenu().add(this);
	}
	
	/**
	 * Return if the fooddish is the same
	 * 
	 * @param name
	 * @return
	 */
	public Boolean areYou(String name) {
		return this.getName()==name;
	}
	
	/**
	 * Add a new notification to responsable
	 */
	@Override
	public Boolean comment(User user, String text, Valoration valoration){
		if ((!(user.haveRestaurant()))||(((Responsible)user).canComent(this.getRestaurant()))){
			Commentary coment =new Commentary(user,text,valoration,this);
			this.getRestaurant().getConfiguration().stream().filter(x->x.filter(coment)).forEach(x->x.getResponsible().getNotifications().add(new Notification(coment)));
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Share the food dish to your friends
	 */
	@Override
	public void share(User userSharing, String message,Set<User> shareToUser){
		new SharedElement(userSharing, shareToUser, this, message);
	}
	
	@Override
	public Long getIdRestaurant(){
		return this.getRestaurant().getIdDomainObject();
	}
	
	@Override
	public Boolean isActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unsubscribe() {
		// TODO Auto-generated method stub
	}

	@Override
	public void reSubscribe() {
		// TODO Auto-generated method stub
		}
	
	@Override
	public Boolean getActive() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setActive(Boolean active) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdShareable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdShareable(Long id) {
		// TODO Auto-generated method stub
	}

	@JsonIgnore
	public boolean areResponsible(User user){
		return this.getRestaurant().areResponsible(user);
	
	}
	
	
	// GETTERS AND SETTERS
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@JsonIgnore
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	@JsonIgnore
	@Override
	public List<Commentary> getComments() {
		return commentaries;
	}

	public void setCommentaries(List<Commentary> commentaries) {
		this.commentaries = commentaries;
	}
	
	public List<Commentary> getCommentaries() {
		return commentaries;
	}
}
