package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


/**
 * @class DomainEvent 
 * 
 * Used to represent an interface, because we don't know how to map interface as multiple hierarchy in hibernate.
 * Then the classes who need notificate something, they will inherit DomanEvent
 * Represents actions or events that can be carried out in the system 
 *
 * @see Commentary @see SharedElement
 *
 * @author Grupo7
 */
@Entity
@Table(name="DomainEvent")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DomainEvent implements Notificable {
	
	/**
	 * Represents the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idDomainEvent;

	//GETTERS AND SETTERS
	public Long getIdDomainEvent() {
		return idDomainEvent;
	}

	public void setIdDomainEvent(Long idDomainEvent) {
		this.idDomainEvent = idDomainEvent;
	}
	
}
