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
 * @class FilterAbstract 
 * 
 * Used to represent the abstract filter
 * 
 * @author Grupo7
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="FilterAbstract")
public abstract class FilterAbstract implements Filter{
	
	/**
	 * Represents the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	protected Long idFilter;
}
