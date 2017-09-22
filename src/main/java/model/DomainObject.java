package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @class DomainObject 
 * 
 * Used to represent an interface, because we don't know how to map interface as multiple hierarchy in hibernate.
 * Is similar to @see DomainEvent , here we implements three interfaces to represents classes who can be commented, shareabled and with the posibility to have lowlogic
 * Represents the objects of domain
 * 
 * @see FoodDish @see Restaurant
 * 
 * @author Grupo7
 */
@Entity
@Table(name="DomainObject")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DomainObject implements Shareable,Commentable,Active{
	
	/**
	 * Represents the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idDomainObject;

	/**
	 * Represents the lowlogic
	 */
	protected boolean lowlogic;
	
	
	//GETTERS AND SETTERS
	public boolean isLowlogic() {
		return lowlogic;
	}
	public void setLowlogic(boolean lowlogic) {
		this.lowlogic = lowlogic;
	}
	public Long getIdDomainObject() {
		return idDomainObject;
	}
	public void setIdDomainObject(Long idDomainObject) {
		this.idDomainObject = idDomainObject;
	}
	@JsonIgnore
	public abstract boolean areResponsible(User user);
	public boolean calculateCategory() {
		// TODO Auto-generated method stub
		return false;
	}
	abstract public Long getIdRestaurant(); 
}
