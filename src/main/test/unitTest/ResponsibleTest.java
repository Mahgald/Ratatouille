package unitTest;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import model.Commensal;
import model.CommentRestaurant;
import model.FoodDish;
import model.Gourmet;
import model.Responsible;
import model.Restaurant;
import model.SharedElement;
import model.Ubication;
import model.User;
import model.Valoration;
import model.Visitor;

public class ResponsibleTest extends TestCase{

	Responsible responsible;
	User user;
	Restaurant restaurant;
	Ubication ubication;
	public void setUp(){
		 ubication = new Ubication(-34.924265,-57.937067);
		user = new User("pepe","lopez","pepe@gmail.com","123",LocalDate.of(2000, 1, 12));
		restaurant = new Restaurant("LIFIA",ubication);
		responsible = user.becomeResposibleOf(restaurant);
		
		
	}
	/**
	 * testea la creacion las inicializaciones 
	 */
	public void testCreateResponsible(){
		assertEquals("pepe", responsible.getName());
		assertEquals("lopez", responsible.getLastName());
		assertTrue(responsible.haveRestaurant());
		
		assertTrue(responsible.areAccessData("pepe@gmail.com", "123"));
		
		assertTrue(responsible.getCommentaries().isEmpty());
		assertTrue(responsible.getCommentariesOfRestaurant().isEmpty());
		
		assertTrue(responsible.getNotifications().isEmpty());
		assertTrue(responsible.getFriends().isEmpty());
		assertTrue(responsible.getReceived().isEmpty());
		assertTrue(responsible.getRequestsSubmitted().isEmpty());
		
		assertTrue(responsible.getSharedFoodDish().isEmpty());
		assertTrue(responsible.getSharedFoodDishToMe().isEmpty());
		
		assertEquals(Visitor.class,responsible.getRanking().getClass());
		
	}
	/**
	 * testea el restaurante en el cual es responsable
	 */
	public void testRestaurant(){
		
	}
	/**
	 * testea la cantidad de comentarios que hace
	 */
	public void testCommentary(){
		restaurant.comment(responsible, "asd", Valoration.NEGATIVE);
		restaurant.comment(responsible, "asd", Valoration.NEGATIVE);
		restaurant.comment(responsible, "asd", Valoration.NEGATIVE);
		assertEquals(3, responsible.getCommentaries().size());
	}
	/**
	 * testea si el amigo esta es su lista de amigos
	 */
	public void testFriend(){
		User user2 = new User();
		
		responsible.SendFriendRequest(user2);
		assertEquals(1, user2.getNotifications().size());
		
		user2.getReceived().get(0).confirm(responsible);
		
		responsible.getFriends().contains(user2);
		user2.getFriends().contains(responsible);
	}
	/**
	 * testea si le llega el mensaje de solicitud y se guarda en la lista de solicitudes
	 */
	public void testSendRequestFriend(){
		User user2 = new User();
		user2.SendFriendRequest(responsible);
		assertEquals(1, responsible.getReceived().size());
	}
	/**
	 * testea el cambio de estado
	 */
	public void testRanking(){
		
		for(int i=0; i<19;i++){
			restaurant.comment(responsible, "pp", Valoration.POSITIVE);
		}
		assertTrue(responsible.getRanking().getClass() == Visitor.class);
		for(int i=0; i<19;i++){
			restaurant.comment(responsible, "pp", Valoration.POSITIVE);
		}
		assertTrue(responsible.getRanking().getClass() == Commensal.class);
		for(int i=0; i<19;i++){
			restaurant.comment(responsible, "pp", Valoration.POSITIVE);
		}
		assertTrue(responsible.getRanking().getClass() == Gourmet.class);
	}
	/**
	 * testea el compartir los platos
	 */
	public void testSharedFoodDish(){
		User user1,user2,user3; 
		user1=new User();
		user2=new User();
		user3=new User();
		FoodDish foodDish = new FoodDish("lll", 23, "ppp", restaurant);
		Set<User> users = new HashSet<>();
		users.add(user1);users.add(user2);users.add(user3);
		SharedElement sharedFoodDish = new SharedElement(responsible, users, foodDish, "lll");
		SharedElement sharedFoodDish1 = new SharedElement(responsible, users, foodDish, "lll");
		assertEquals(2, responsible.getSharedFoodDish().size());
		assertTrue(responsible.getSharedFoodDish().contains(sharedFoodDish));
		assertTrue(responsible.getSharedFoodDish().contains(sharedFoodDish1));
	}		
	/**
	 * testea los platos que me compartieros
	 */
	public void testSharedFoodDishMe(){
		User user1,user2,user3,userA,userB; 
		user1=new User();
		user2=new User();
		userA=new User();
		userB=new User();
		FoodDish foodDish = new FoodDish("lll", 23, "ppp", restaurant);
		Set<User> users = new HashSet<>();
		users.add(userA);users.add(userB);users.add(responsible);
		SharedElement sharedFoodDish = new SharedElement(user1, users, foodDish, "lll");
		SharedElement sharedFoodDish1 = new SharedElement(user2, users, foodDish, "lll");
		assertEquals(2, responsible.getSharedFoodDishToMe().size());
		assertTrue(responsible.getSharedFoodDishToMe().contains(sharedFoodDish));
		assertTrue(responsible.getSharedFoodDishToMe().contains(sharedFoodDish1));
	}
	/**
	 * testea si puedo comentar a restaurante menos de un kilometro
	 */
	public void testCompareMoreThan1km(){
		 
		Ubication ubic2 = new Ubication( -34.929825,-57.943289); 
		Ubication ubic3 = new Ubication(  -34.933484,-57.947624); 
		Restaurant r1,r2;
		r1 = new Restaurant("res1", ubic2);
		r2 = new Restaurant("res2",ubic3);
		assertTrue(responsible.canComent(r1));
		assertFalse(responsible.canComent(r2));
	}
}
