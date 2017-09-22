package model;

import java.util.stream.Stream;
import javax.persistence.*;

/**
 * @class Visitor 
 * 
 * Used to represent the visitor state
 *  
 * @author Grupo7
 */
@Entity
@Table(name="Visitor")
public class Visitor extends RankingUser {
		
	/**
	 * Default constuctor
	 */
	public Visitor(){
		super();
	}
	
	/**
	 * Constructor with user as param
	 * @param user
	 */
	public Visitor(User user){
		super(user,0);
		this.user=user;
		this.comments=0;
	}
	
	/**
	 * Constructor with param
	 * @param user
	 * @param comments
	 */
	public Visitor(User user,int comments){
		super(user,0);
		this.user=user;
		this.comments=comments;
	}
		
	@Override
	protected void changeRanking() {
		if (this.getComments() >= 20) {
			this.getUser().setRanking(new Commensal(this.getUser(),this.getComments()));
		}
	}

	@Override
	void addCommentOfTypeUser(int[] counters) {
		counters[0]++;
	}
	@Override
	void subCommentOfTypeUser(int[] counters) {
		counters[0]--;
	}
	

	@Override
	Boolean interesting(Stream<Boolean> combination) {
		return combination.findFirst().get();
	}
}
