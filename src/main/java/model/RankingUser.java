package model;

import java.util.stream.Stream;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @class RankingUser 
 * 
 * Used to represent the ranking user
 * 
 * @see User
 *  
 * @author Grupo7
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="RankingUser")
public abstract class RankingUser{
	
	/**
	 * Represents the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	protected Long idRanking;
	
	/**
	 * Represents user who have the ranking 
	 */
	@JsonBackReference
	@OneToOne
	protected User user;
		
	/**
	 * Represents the user commentary count
	 */
	@Column(name="comments")
	protected int comments;
		
	/**
	 * Default constructor
	 */
	public RankingUser() {}
	
	/**
	 * Constructor with param
	 * 
	 * @param user
	 * @param counter
	 */
	public RankingUser(User user,int counter){
		this.user=user;
		this.comments=counter;
	}
	
	/**
	 * Check if I make enough comments to change categories
	 * @return 
	 * @return 
	 */
	protected abstract void changeRanking();
		
	/**
	 * Increments the user commentaries
	 * @param counters
	 */
	abstract void addCommentOfTypeUser(int[] counters);
		
	/**
	 * Decrements the user commentaries
	 * @param counters
	 */
	abstract void subCommentOfTypeUser(int[] counters);
	
	/**
	 * Use of template method
	 * @param commentRestaurant
	 */
	public void addComentary(Commentary comment){
		this.getUser().getCommentaries().add(comment);
		this.comments++;
		changeRanking();
	}

	/**
	 * Use of template method
	 * @param commentRestaurant
	 */
	public void removeCommentary(Commentary commentary) {
		this.getUser().getCommentaries().remove(commentary);
		this.comments--;
		changeRanking();
	}
		
	abstract Boolean interesting(Stream<Boolean> stream3);	
	
	// GETTERS AND SETTERS
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public Long getIdRanking() {
		return idRanking;
	}
	public void setIdRanking(Long idRanking) {
		this.idRanking = idRanking;
	}

	
}

