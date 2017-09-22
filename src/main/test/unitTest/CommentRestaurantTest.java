package unitTest;

import java.time.LocalDate;

import junit.framework.TestCase;
import model.CommentRestaurant;
import model.Restaurant;
import model.Ubication;
import model.User;
import model.Valoration;


public class CommentRestaurantTest extends TestCase{
	private Restaurant restaurant;
	private User user ;
	private CommentRestaurant commentRestaurant;
	private Valoration valoration;
	private Ubication ubication;
	private LocalDate date ;
	
	
	protected void setUp(){
		user = new User("pepe", "lopez", "pepe@gmail.com", "123", date);
		restaurant = new Restaurant("LIFIA", ubication);
		valoration = Valoration.POSITIVE;
		commentRestaurant = new CommentRestaurant(user, restaurant, "bueno", valoration);
	}
	/**
	 * testea que el usuario sea el del comentario restaurante
	 */
	public void testUserComentary(){
		User a = new User();
		assertEquals(user, commentRestaurant.getUser());
		assertFalse(a == commentRestaurant.getUser());
		
	}
	/**
	 * testea que la valoracion sea la correcta
	 */
	public void testValoration(){
		assertTrue(Valoration.POSITIVE ==  commentRestaurant.getValoration());
		assertFalse(Valoration.NEGATIVE == commentRestaurant.getValoration());
		assertFalse(Valoration.NEUTRAL == commentRestaurant.getValoration());
	}
	/**
	 * testea el restaurante del comentario
	 */
	public void testRestaurantComentary(){
		assertTrue(restaurant.getComments().contains(commentRestaurant));
	}
}
