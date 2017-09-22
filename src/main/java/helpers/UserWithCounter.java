package helpers;

import model.Restaurant;
import model.User;

public class UserWithCounter implements Comparable{

	public User user;
	public int counter;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public UserWithCounter(User user, int counter) {
		
		this.user = user;
		this.counter = counter;
	}
	

	@Override
	public int compareTo(Object param) {
		return (this.counter < ((UserWithCounter)param).getCounter() ) ? -1: (this.counter > ((UserWithCounter)param).getCounter() ) ? 1:0 ;

	}

}