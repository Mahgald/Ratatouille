package model;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @class Ubication 
 * 
 * Used to represent ubication 
 *  
 * @see User 
 * @see Restaurant
 *  
 * @author Grupo7
 */
@Entity
@Table(name="Ubication")
public class Ubication {
	
	/**
	 * Represents the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idUbication;
	
	/**
	 * Represents the latitude
	 */
	@Column(name="latitude")
	private double latitude;
	
	/**
	 * Represents the longitude
	 */
	@Column(name="longitude")
	private double longitude;

	/**
	 * Represents the direction as a string
	 */
	@Column(name="direction")
	private String direction;
	
	/**
	 * Default constructor
	 */	
	public Ubication() {}

	/**
	 * Constructor with param
	 * @param latitude
	 * @param longitude
	 * @param direction
	 */	
	public Ubication(double latitude, double longitude,String direction) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.direction=direction;
	}
	
	/**
	 * Receives an ubication and compares if the location is more far than 1 km
	 * 
	 * @param ubication
	 * @return
	 */
	public Boolean compareMoreThan1Km(Ubication ubication) {
		double va1 = Math.pow(Math.sin(Math.toRadians(this.getLatitude() - ubication.getLatitude()) / 2), 2) + Math.pow(Math.sin(Math.toRadians(this.getLongitude() - ubication.getLongitude()) / 2), 2)* Math.cos(Math.toRadians(this.getLatitude())) * Math.cos(Math.toRadians(ubication.getLatitude()));  
	    return (6371 * 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1))>1);  
	}

	/**
	 * Receives an ubication and compare if the location is less than a distance
	 * 
	 * @param ubication
	 * @param distance
	 * @return
	 */
	public Boolean compareLessThan(Ubication ubication,int distance) {
		double va1 = Math.pow(Math.sin(Math.toRadians(this.getLatitude() - ubication.getLatitude()) / 2), 2) + Math.pow(Math.sin(Math.toRadians(this.getLongitude() - ubication.getLongitude()) / 2), 2)* Math.cos(Math.toRadians(this.getLatitude())) * Math.cos(Math.toRadians(ubication.getLatitude()));  
	    return ((6371 * 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1)) *1000) <distance);  
	}
		
	// GETTERS AND SETTERS
	
	public double getLatitude() {
		return latitude;
	}
	public Long getIdUbication() {
		return idUbication;
	}
	public void setIdUbication(Long idUbication) {
		this.idUbication = idUbication;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longidude) {
		this.longitude = longidude;
	}
		public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
}
