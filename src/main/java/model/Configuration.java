package model;

import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * @class Configuration 
 * 
 * Used to represent the configuration of the responsible to  the restaurant
 *  
 * @author Grupo7
 */
@Entity
@Table(name="Configuration")
public class Configuration implements Active {

	/**
	 * Represents configuration id
	 * */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idConfiguration;
	
	/**
	 * Represents restaurant who will be configurated
	 * */
	@ManyToOne(fetch=FetchType.EAGER)
	private Restaurant restaurant;
	
	/**
	 * Represents responsible who made the configuration
	 * */
	@ManyToOne(fetch=FetchType.EAGER)
	private Responsible responsible;
	
	/**
	 * Represents the filter
	 * */	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private FilterAbstract filter;
	
	/**
	 * Represents the dateOfAsociation of configuration
	 * */	
	@Column(name="dateOfAsociation")
	private LocalDateTime dateOfAsociation;
	
	/**
	 * Represents the date of last modification of configuration
	 * */	
	@Column(name="dateOfLastModification")
	private LocalDateTime dateOfLastModification;
	
	/**
	 * Represents the lowlogic
	 * */	
	@Column(name="lowLogic")
	private Boolean lowLogic;
	
	/**
	 * Default constructor
	 * */
	public Configuration(){}
	
	/**
	 * Constructor with param
	 * @param responsible
	 * @param restaurant
	 */
	public Configuration(Responsible responsible,Restaurant restaurant){
		this.setLowLogic(false);
		this.setResponsible(responsible);
		this.setRestaurant(restaurant);
		this.setDateOfAsociation(LocalDateTime.now());
		this.setDateOfLastModification(LocalDateTime.now());
		this.setFilter(new FilterByCategorieUser(false,false,false));
		restaurant.getConfiguration().add(this);
	}
	
	/**
	 * Constructor with param
	 * @param responsible
	 * @param restaurant
	 * @param filter
	 */
	public Configuration(Responsible responsible,Restaurant restaurant,Filter filter){
		this.setResponsible(responsible);
		this.setRestaurant(restaurant);
		this.setDateOfAsociation(LocalDateTime.now());
		this.setDateOfLastModification(LocalDateTime.now());
		this.setFilter(filter);
		restaurant.getConfiguration().add(this);
	}

	/**
	 * Ask to strategy if needs notificate the param coment
	 * @param coment
	 * @return
	 */
	public boolean filter(Commentary coment){
		if (this.getFilter().filter(coment)){
			return true;
		}
		return false;
	}
		
	/**
	 * Used to know if the restaurant have this configurations
	 * @param restaurant
	 * @return
	 */
	public boolean isConfigurationTo(Restaurant restaurant) {
		return this.getRestaurant().areYou(restaurant.getName(),restaurant.getUbication());
	}
	
	@Override
	public Boolean isActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unsubscribe() {
		// TODO Auto-generated method stub
	}

	@Override
	public void reSubscribe() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Boolean getActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActive(Boolean active) {
		// TODO Auto-generated method stub
	}
	
		
	
	// GETTERS AND SETTERS
	public Restaurant getRestaurant() {
		return restaurant;
	}
	private void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Responsible getResponsible() {
		return responsible;
	}
	private void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}
	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = (FilterByCategorieUser) filter;
		this.setDateOfLastModification(LocalDateTime.now());
	}
	public LocalDateTime getDateOfAsociation() {
		return dateOfAsociation;
	}
	private void setDateOfAsociation(LocalDateTime dateOfAsociation) {
		this.dateOfAsociation = dateOfAsociation;
	}
	public LocalDateTime getDateOfLastModification() {
		return dateOfLastModification;
	}
	private void setDateOfLastModification(LocalDateTime dateOfLastModification) {
		this.dateOfLastModification = dateOfLastModification;
	}
	public Boolean getLowLogic() {
		return lowLogic;
	}
	public void setLowLogic(Boolean lowLogic) {
		this.lowLogic = lowLogic;
		this.setDateOfLastModification(LocalDateTime.now());
	}
	public Long getIdConfiguration() {
		return idConfiguration;
	}
	public void setIdConfiguration(Long idConfiguration) {
		this.idConfiguration = idConfiguration;
	}

	

	
		
		
}
