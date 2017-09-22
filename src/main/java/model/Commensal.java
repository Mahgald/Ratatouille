package model;

import java.util.stream.Stream;
import javax.persistence.*;

/**
 * @class Commensal 
 * 
 * Used to represent the commensal state
 *  
 * @author Grupo7
 */
@Entity
@Table(name="Commensal")
//@DiscriminatorValue("Commensal")
//@PrimaryKeyJoinColumn(name="RankingUser_id")
public class Commensal extends RankingUser{
	
	/**
	 * Default constuctor
	 */
	public Commensal(){
		super();
	}
	
	/**
	 * Constructor with param
	 * @param user
	 * @param comments
	 */
	public Commensal(User user,int comments) {
		super(user,0);
		this.user=user;
		this.comments=comments;
	}
	
	@Override
	protected void changeRanking() {
		if (this.getComments() >= 40) {
			this.getUser().setRanking(new Gourmet(this.getUser(),this.getComments()));
			
		}else{if (this.getComments() < 20) {
			this.getUser().setRanking(new Visitor(this.getUser(),this.getComments()));
			}
		}
	}

	@Override
	void addCommentOfTypeUser(int[] counters) {
		counters[1]++;
	}
	@Override
	void subCommentOfTypeUser(int[] counters) {
		counters[1]--;
	}
	
	
	@Override
	Boolean interesting(Stream<Boolean> combination) {
		return combination.skip(1).findFirst().get();
	}
}