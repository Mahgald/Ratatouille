package model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @class Silver
 * 
 * Represents the silver state
 * 
 * @see RestaurantCategory
 * 
 * @author Grupo7
 *
 */
@Entity
@Table(name="Silver")
public class Silver extends RestaurantCategory{
	static int visitorPosCont=2;//valores maximos en vez de minimos
	static int commensalPosCont=0;
	static int gourmetPosCont=0;
	
	/**
	 * Default constructor
	 */
	public Silver(){}
	
	/**
	 * Constructor with param
	 * 
	 * @param restaurant
	 */
	public Silver(Restaurant restaurant){
		super(restaurant,Benefit.NOTHING);
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
		return "Silver";
	}

	@Override
	protected RestaurantCategory next(int[] counters) {
		if (this.compare(counters)){
			return new Gold(this.getRestaurant()).next(counters);
		}else{
			return this;
		}
	}
	
	@Override
	protected RestaurantCategory previous(int[] counters) {
		return this;
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
