package model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @class Gold
 * 
 * Represents the gold state
 * 
 * @see RestaurantCategory
 * 
 * @author Grupo7
 *
 */
@Entity
@Table(name="Gold")
public class Gold extends RestaurantCategory{
	static int visitorPosCont=4;
	static int commensalPosCont=0;
	static int gourmetPosCont=0;
	
	/**
	 * Default constructor
	 */
	public Gold(){}
	
	/**
	 * Constructor with param
	 * 
	 * @param restaurant
	 */
	public Gold(Restaurant restaurant){
		super(restaurant,Benefit.CURSIVA);
		this.idCategory=restaurant.getIdDomainObject();
	}
	
	@Override
	protected int gourmetPosCont() {
		return gourmetPosCont;
	}

	@Override
	protected int commensalPosCont() {
		return commensalPosCont;
	}

	@Override
	protected int visitorPosCont() {
		return visitorPosCont;
	}
	
	/**
	 * @return The category name as string
	 */
	@Override
	public String toString() {
		return "Gold";
	}
	
	
	@Override
	protected RestaurantCategory next(int[] counters) {
		if (this.compare(counters)){
			return new Platinum(this.getRestaurant()).next(counters);
		}else{
			return this;
		}
	}
	@Override
	protected RestaurantCategory previous(int[] counters) {
		if (!(this.compare(counters))){
			return new Silver(this.getRestaurant()).previous(counters);
		}else{
			return this;
		}
	}
	
	
	// GETTERS AND SETTERS

	public static int getVisitorPosCont() {
		return visitorPosCont;
	}
	public static void setVisitorPosCont(int visitorPosCont) {
		Silver.visitorPosCont = visitorPosCont;
	}
	public static int getCommensalPosCont() {
		return commensalPosCont;
	}
	public static void setCommensalPosCont(int commensalPosCont) {
		Silver.commensalPosCont = commensalPosCont;
	}
	public static int getGourmetPosCont() {
		return gourmetPosCont;
	}
	public static void setGourmetPosCont(int gourmetPosCont) {
		Silver.gourmetPosCont = gourmetPosCont;
	}
	
}
