package model;
import javax.persistence.*;

/**
 * @class SpecialNotification 
 * 
 * Used to represent special notifications
 * 
 * @author Grupo7
 */
@Entity
@Table(name="SpecialNotification")
public class SpecialNotification extends Notification{

	public SpecialNotification(){}
	
	public SpecialNotification(DomainEventSpecial contenido) {
		super(contenido);
		
	}

	@Override
	public int compareTo(Object notification) {
		return ((NotificableSpecial) this.getContentWithoutSee()).getDateSpecial().compareTo(((NotificableSpecial) ((Notification) notification).getContentWithoutSee()).getDateSpecial());
	}
	
	

	

}
