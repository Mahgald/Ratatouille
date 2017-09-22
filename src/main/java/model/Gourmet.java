package model;

import java.util.stream.Stream;
import javax.persistence.*;

/**
 * @class Gourmet 
 * 
 * Used to represent the gourmet state
 *  
 * @author Grupo7
 */
@Entity
@Table(name="Gourmet")
public class Gourmet extends RankingUser{
	/**
	 * Default constructor
	 */
	public Gourmet(){
		super();
	}
	
	/**
	 * Constructor with param
	 * @param user
	 * @param comments
	 */
	public Gourmet(User user,int comments) {
		super(user,0);
		this.user=user;
		this.comments=comments;
	}

	@Override
	protected void changeRanking() {
		if (this.getComments() < 40) {
			this.getUser().setRanking(new Commensal(this.getUser(),this.getComments()));
		}
	}

	@Override
	void addCommentOfTypeUser(int[] counters) {
		counters[2]++;
	}
	@Override
	void subCommentOfTypeUser(int[] counters) {
		counters[2]--;
	}
	
	
	@Override
	Boolean interesting(Stream<Boolean> combination) {
		/*este codigo representaria el codigo de una clase nueva
		 * y la mejor ventaja es que esta implementacion permite que al agregar una clase nueva 
		 * no tengas que hacer modificaciones a la base de datos
		
		if (combination.size()<4){
			Boolean  result =combination.stream().reduce(true, (a,b)->a&&b);
			while (combination.size()<4){
				combination.add(result);
		}
		return combination.get(4);
		
		*/
		
		/*otra alternativa mas concisa pero menos eficiente seria

			while (combination.size()<x){
				combination.add(combination.stream().reduce(true, (a,b)->a&&b));
			return combination.get(x);
		 */
		
		return combination.skip(2).findFirst().get();
	}
}
