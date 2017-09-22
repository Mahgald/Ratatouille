package model;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @class RestaurantCategory 
 * 
 * Used to represent the restaurant category
 * 
 * @see Restaurant
 *  
 * @author Grupo7
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="RestaurantCategory")
public abstract class RestaurantCategory {
		
	/**
	 * Represents category id which is the same of the restaurant in database 
	 */
	/*@GenericGenerator(name = "generator", strategy = "foreign",	parameters = @Parameter(name = "property", value = "restaurant"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name="id" )*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	protected Long idCategory;

	/**
	 * Represents restaurant who have the category 
	 */
	@OneToOne(cascade=CascadeType.ALL)
	//@PrimaryKeyJoinColumn
	@JsonIgnore
	protected Restaurant restaurant;

	/**
	 * Represents benefit that restaurant have
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="benefit")	
	protected Benefit benefit;
				
	/**
	 * Default constuctor		
	*/
	RestaurantCategory() {

	}

	/**
	 * Constructor category
	 * @param restaurant
	 * @param benefit
	 */
	protected RestaurantCategory(Restaurant restaurant,Benefit benefit) {
		this.setRestaurant(restaurant);
		this.setBenefit(benefit);
	}

	protected boolean compare(int[] counters){
		return ((counters[0] >= visitorPosCont()) && (counters[1] >= commensalPosCont()) && (counters[2] >= gourmetPosCont()));
	}
	
	protected abstract int gourmetPosCont();
	protected abstract int commensalPosCont();
	protected abstract int visitorPosCont();
	protected RestaurantCategory nextCategory(int[] counters){
		if (this.compare(counters)) {
			return this.next(counters);
		}else{
			return this.previous(counters);
		}
	}
	protected abstract RestaurantCategory next(int[] counters);
	protected abstract RestaurantCategory previous(int[] counters);

	public RestaurantCategory calculateCategory() {
		//int visitor, commensal, gourmet;
			
		int[] counters=new int[3];
		Set<Long> users=new HashSet<>();

		
		
		ListIterator<?> comments = this.getRestaurant().getComments().listIterator(this.getRestaurant().getComments().size());

		// Iterate in reverse.
		while(comments.hasPrevious()) {
			Commentary comment=(Commentary) comments.previous();
			if (!users.contains(comment.getUser().getId())){
				users.add(comment.getUser().getId());
				if (comment.getValoration()==Valoration.POSITIVE){
					comment.getUser().addCommentOfTypeUser(counters);
				}else if(comment.getValoration()==Valoration.NEGATIVE){
					comment.getUser().subCommentOfTypeUser(counters);
				}
			}
		}
		return this.nextCategory(counters);
	}
		
	
	//GETTERS AND SETTERS
	public Restaurant getRestaurant(){
		return this.restaurant;
	}
	public void setRestaurant(Restaurant restaurant){
		this.restaurant=restaurant;
	}
	private void setBenefit(Benefit benefit) {
		this.benefit = benefit;
	}
	public Benefit getBenefit() {
		return this.benefit;
	}
	public long getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(long idCategory) {
		this.idCategory = idCategory;
	}
	
		
}
