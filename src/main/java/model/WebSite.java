package model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDate;


/**
 * Represents the application, but in the practice isn't used
 *
 * @author Grupo7
 */

public class WebSite {
	private Set<Restaurant> listRestaurant = new HashSet<>();
	private Set<User> listUser = new HashSet<>();
	private User userLoged;
	private static WebSite instance = null;
	
	
	private WebSite(){
		
	};
	

	

	/**
	 * Singleton 
	 * @return WebSite
	 */
	public static WebSite Instance(){
		if(instance==null){
			instance=new WebSite();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param name
	 * @param lastName
	 * @param email
	 * @param birthdate
	 * @return
	 * @deprecated not is necessary
	 */
	@Deprecated
	private Boolean existUser(String name,String lastName,String email,LocalDate birthdate){
		if (this.getListUser().stream().filter(x -> (x.areYou(email)) ).collect(Collectors.toSet()).isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param lastName
	 * @param email
	 * @param password
	 * @param birthdate
	 * @return
	 */
	public Boolean createUser(String name,String lastName,String email,String password,LocalDate birthdate){
		Optional<User> stream = this.getListUser().stream().filter(x -> (x.areYou(email)) ).findFirst();
		if(!(stream.isPresent())){
			User newUser = new User(name,lastName,email,password,birthdate);
			return this.getListUser().add(newUser);
		}else{
			return false;
		}
	}
	
	/**
	 * dado los valores que identifican a 
	 * un restauran si este existe lo devuelve 
	 * y sino, lo crea y lo devuelve 
	 * @param name
	 * @param ubication
	 * @return
	 * @deprecated not is necessary
	 */
	@Deprecated
	private Boolean existRestaurant(String name,Ubication ubication){
		if (this.getListRestaurant().stream().filter(x -> (x.areYou(name,ubication)) ).collect(Collectors.toSet()).isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Create a new restaurant if not exist a restaurant 
	 * whit equal name and ubication (identificators) 
	 * @param name
	 * @param ubication
	 * @return
	 */
	public Boolean createRestaurant(String name,Ubication ubication){
		Optional<Restaurant> stream = this.getListRestaurant().stream().filter(x -> (x.areYou(name,ubication)) ).findFirst();
		if (!(stream.isPresent())){
			Restaurant newRestaurant=new Restaurant(name, ubication);
			Responsible responsible =this.getUserLoged().becomeResposibleOf(newRestaurant);
			//aca se podria comparar por igualdad al userlogueado contra responsible
			if (!(this.getUserLoged().haveRestaurant())){
				this.getListUser().remove(this.getUserLoged());
				this.getListUser().add(responsible);
				this.setUserLoged(responsible);
			}
			this.getListRestaurant().add(newRestaurant);
			return true;//en realidad este true deberia ser un and de los resultados de los emtodos que usamos...
		}else{
			return false;
		}
	}

	/**
	 * 
	 * @param name
	 * @param ubication
	 * @return
	 * @deprecated {@link #createRestaurant(String,Ubication) createRestaurant} is recommended instead
	 */
	@Deprecated
	public Boolean createRestaurantV1(String name,Ubication ubication){
		
		if (!(this.existRestaurant(name,ubication))){
			Restaurant newRestaurant=new Restaurant(name, ubication);
			Responsible responsible =this.getUserLoged().becomeResposibleOf(newRestaurant);
			
			//aca se podria comparar por igualdad al userlogueado contra responsible
			if (!(this.getUserLoged().haveRestaurant())){
				this.getListUser().remove(this.getUserLoged());
				this.getListUser().add(responsible);
				this.setUserLoged(responsible);
			}
			this.getListRestaurant().add(newRestaurant);
			return true; //en realidad este true deberia ser un and de los resultados de los emtodos que usamos...
		}else{
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public Boolean logIn(String email, String password){
		Optional<User> stream =this.getListUser().stream().filter(x->x.areAccessData(email,password)).findFirst();
		if (stream.isPresent()){
			this.setUserLoged(stream.get());
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @deprecated  {@link #logIn(String,String) logIn } is recommended instead
	 */
	@Deprecated 
	public User logInV1(String email, String password){
	for(User users: this.getListUser()){
		if(users.getEmail().equals(email) && users.getPassword().equals(password)){
			return users;
			}else{
				if(users.getEmail().equals(email) && !users.getPassword().equals(password)){
					User notPassord=users;
					notPassord.setPassword(null);
					return notPassord;
				}
			}
		}
		return null;
	}
	
	
	//GETTERS AND SETTERS
	public Set<Restaurant> getListRestaurant() {
		return listRestaurant;
	}
	private void setListRestaurant(Set<Restaurant> listRestaurant) {
		this.listRestaurant = listRestaurant;
	}
	public Set<User> getListUser() {
		return listUser;
	}
	private void setListUser(Set<User> listUser) {
		this.listUser = listUser;
	}
	public User getUserLoged() {
		return userLoged;
	}
	private void setUserLoged(User userLoged) {
		this.userLoged = userLoged;
	}
	
	

	
		
	
	
}
