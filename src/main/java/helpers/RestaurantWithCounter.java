package helpers;

import model.Restaurant;

public class RestaurantWithCounter implements Comparable{

	public Restaurant restaurant;
	public int counter;
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public RestaurantWithCounter(Restaurant restaurant, int counter) {
		
		this.restaurant = restaurant;
		this.counter = counter;
	}
	

	@Override
	public int compareTo(Object param) {
		return (this.counter < ((RestaurantWithCounter)param).getCounter() ) ? -1: (this.counter > ((RestaurantWithCounter)param).getCounter() ) ? 1:0 ;

	}

}