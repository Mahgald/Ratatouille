package unitTest;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import junit.framework.TestCase;
import model.Restaurant;
import model.Ubication;
import model.User;
import model.WebSite;

public class WebSiteTest extends TestCase {
	private WebSite webSite;
	private Ubication ubicationResto;
	private LocalDate date;
	
	public void setUp(){
//		date = Calendar.getInstance().getTime();
		ubicationResto = new Ubication(10, 20);
		webSite = WebSite.Instance();
	}
	
	
	/**
	 * testea la creacion del usuario
	 */
	public void testCreateUser(){
		webSite.getListUser().clear();
		webSite.createUser("pepe2", "lopez", "pepe@gmail.com", "123", date);
		webSite.createUser("pepe2", "lopez", "pepe@gmail.com", "123", date);
		webSite.createUser("pepe2", "lopez", "pepe@gmail.com", "123", date);
		webSite.createUser("pepe2", "lopez", "pepe@gmail.com", "123", date);
		assertEquals(1, webSite.getListUser().size());
		webSite.createUser("pepe3", "lopez", "pepe3@gmail.com", "123", date);
		webSite.createUser("pepe4", "lopez", "pepe4@gmail.com", "123", date);
		webSite.createUser("pepe5", "lopez", "pepe5@gmail.com", "123", date);
		assertEquals(4, webSite.getListUser().size());
	}
	/**
	 * testea la cracion del restaurante
	 */
	public void testCreateRestaurant(){
		webSite.getListUser().clear();
		webSite.createUser("pepe1", "lopez", "pepe@gmail.com", "123", date);
		webSite.logIn("pepe@gmail.com", "123");
		webSite.createRestaurant("LIFIA1", ubicationResto);
		//webSite.createRestaurant("LIFIA2", ubicationResto);
		//webSite.createRestaurant("LIFIA3", ubicationResto);
		assertEquals(1, webSite.getListRestaurant().size());
		assertEquals(1, webSite.getListUser().size());
//		System.out.println(webSite.getListUser().stream().map(x->x.haveRestaurant()).collect(Collectors.toList()));
		
	}	
	/**
	 * testea el login
	 */
	public void testLogIn(){
		webSite.getListUser().clear();
		webSite.createUser("lala", "lopez", "lala@gmail.com", "123", date);
		assertFalse(webSite.logIn("pepe30", "123"));
		assertTrue(webSite.logIn("lala@gmail.com", "123"));
	}

	
}
