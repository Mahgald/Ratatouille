package unitTest;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import model.CommentRestaurant;
import model.FoodDish;
import model.Responsible;
import model.Restaurant;
import model.Silver;
import model.Ubication;
import model.User;
import model.Valoration;
import model.WebSite;

public class RestaurantTest extends TestCase{

	private Restaurant restaurant;
	private Ubication ubicationRestaurant;
	private LocalDate date;
	private User user,user2;
	private Responsible responsible;
	WebSite webSite;
	
	public void setUp(){
		date = LocalDate.of(2000, 3, 3);
//		webSite = WebSite.Instance();
//		webSite.createUser("user1", "ape1", "user1@gmail.com", "123", date);
//		webSite.logIn("user1@gmail.com", "123");
//		webSite.createRestaurant("rest",new Ubication(123, 123));
		
		
		user =  new User("pepse", "lopez", "pepe@gmail.com", "123",date);
		ubicationRestaurant = new Ubication(1, 1);
		restaurant = new Restaurant("LIFIA", ubicationRestaurant);
	}
	/**
	 * testea la creacion del restaurante
	 */
	public void testCreateRestaurant(){
		assertEquals("LIFIA", restaurant.getName());
		assertEquals(0, restaurant.getMenu().size());
		assertEquals(1,0, restaurant.getUbication().getLatitude());
		assertEquals(1,0, restaurant.getUbication().getLongitude());
		assertTrue(restaurant.getMenu().isEmpty());
		assertTrue(restaurant.getComments().isEmpty());
		assertEquals(0, restaurant.countObservers());
		assertTrue(restaurant.areYou("LIFIA", ubicationRestaurant));
	}
	/**testea la cantidad de comentarios  
	 * 
	 */
	public void testAddCommentary(){
		restaurant.comment(user, "pepep", Valoration.POSITIVE);
		restaurant.comment(user, "pepep", Valoration.POSITIVE);
		assertEquals(2, restaurant.getComments().size());	
	}
	/**
	 * testea si los platos estan  en el menu
	 */
	public void testAddFoodDish(){
		restaurant.addFoodDish("plato1", 22, "kkk");
		restaurant.addFoodDish("plato2", 22, "kkk");
		assertEquals(2, restaurant.getMenu().size());
		restaurant.addFoodDish("plato2", 32, "lll");
		assertEquals(2, restaurant.getMenu().size());
	}
	/**testea el responsable 
	 * 
	 */
	public void testResponsible(){
		
	}
	/**
	 * testea la categoria
	 */
	public void testCalculateCategory(){
		
	}
}
