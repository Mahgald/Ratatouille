package unitTest;

import java.time.LocalDate;
import junit.framework.TestCase;
import model.FoodDish;
import model.Restaurant;
import model.Ubication;
import model.User;
import model.Valoration;

public class FoodDishTest extends TestCase{

	
	private FoodDish foodDish;
	private Restaurant restaurant;
	private User user;
	public void setUp(){
		user = new User("pepe", "lopez", "pepe@gmail.cm", "123", LocalDate.of(2000, 2, 3));
		restaurant = new Restaurant("LIFiA", new Ubication(12, 32));
		foodDish = new FoodDish("plato1", 10, "ppp", restaurant);
	}
	/**
	 * testea la creacion del plato
	 */
	public void testCreateFoodDish(){
		assertEquals("plato1",foodDish.getName());
		assertTrue(foodDish.getComments().isEmpty());
		assertEquals("ppp", foodDish.getDescription());
		assertEquals(10,0,foodDish.getPrice());
		assertEquals(restaurant, foodDish.getRestaurant());
	}
	/**
	 * testea los comentarios de los platos
	 */
	public void testCommentFoodDish(){
		User user1,user2,user3;
		user1 = new User();
		user2 = new User();
		user3 = new User();
		foodDish.comment(user1, "aaa", Valoration.POSITIVE);
		foodDish.comment(user2, "aaa", Valoration.NEGATIVE);
		foodDish.comment(user3, "aaa", Valoration.NEUTRAL);
		assertEquals(3, foodDish.getComments().size());
		System.out.println(user1.getCommentaries().size());
	}
	/**
	 * testea el compartir el plato
	 */
	public void testSharedFoodDish(){
		
	}
	
//	private String namef1,namef2,nameuser1,nameuser2,nameuser3,desc1,desc2,desc3,namer1,namer2,lastname1,email1,pass1;
//	private Restaurant rest1,rest2,rest3;
//	private float price1,price2,price3;
//	private FoodDish food1;
//	private Ubication ubic1,ubic2;
//	private User user1,user2;
//	private LocalDate date1;
//	
//	
//	protected void setUp(){
//		namef1="Milanesa con papas fritas";
//		desc1="Una milanesa de ternera increible doradita y crocante con una porcion de fritas abundante, re polenta para cuando tenes hambre de hacer muchos testcase";
//		price1=150;
//		namer1="el mundo de las milanesas";
//		ubic1=new Ubication(25,23);
//		rest1=new Restaurant(namer1,ubic1);
//		nameuser1="Martin";
//		lastname1="Valdez";
//		email1="martin@mail.com";
//		pass1="123456";
//		date1=LocalDate.of(1992, 8, 24);
//		namer2="El infierno de las milanesas";
//		ubic2=new Ubication(15,25);
//		rest2=new Restaurant(namer2,ubic2);
//		user1=new User(nameuser1,lastname1,email1,pass1,date1);
//		namef2="la milnesa eterna";
//		price2=299;
//		desc2="Una milanesa enorme que te hara recordar a la tortura de homero en el infierno comiendo rosquillas por l eternidad";
//		
//		food1=new FoodDish(namef2,price2,desc2,rest2);
//	}
//	
//	public void testCreateFoodDish(){
//		//food1=new FoodDish(namef1, price1, desc1, rest1);
//		rest1.addFoodDish(namef1, price1, desc1);
//		assertEquals(1,rest1.getMenu().size());
//		rest1.addFoodDish(namef1, price1, desc1);
//		assertEquals(1,rest1.getMenu().size());
//		assertEquals(namef1,rest1.getMenu().stream().findFirst().get().getName());
//		assertEquals(price1,rest1.getMenu().stream().findFirst().get().getPrice());
//		assertEquals(desc1,rest1.getMenu().stream().findFirst().get().getDescription());
//		assertTrue(rest1.getMenu().stream().findFirst().get().getRestaurant().areYou(rest1.getName(), rest1.getUbication()));
//	};
//	public void testComentFoodDish(){
//		food1.commentate(user1, "Igual que homero no fui vencido", Valoration.POSITIVE);
//		assertEquals(1, food1.getCommentaries().size());
//		assertEquals(user1.getEmail(), food1.getCommentaries().get(0).getUser().getEmail());
//		assertEquals(user1, food1.getCommentaries().get(0).getUser());
//		assertEquals(Valoration.POSITIVE, food1.getCommentaries().get(0).getValoration());
//		assertEquals("Igual que homero no fui vencido", food1.getCommentaries().get(0).getDescription());
//		
//	};
//	public void testShareFoodDish(){};
//	
	
	
	
	
	
	
	/*
	
	private FoodDish foodDish;
	private Restaurant restaurant;
	private ArrayList<CommentFoodDish> commentaries;
	private User userSharing,shareToUser,user;
	private Valoration valoration;
	
	protected void setUp(){
		userSharing = new User();
		foodDish = new FoodDish();
		foodDish.setCommentaries(commentaries);
		foodDish.setDescription("lala");
		foodDish.setName("pasta");
		foodDish.setPrice(10);
		foodDish.setRestaurant(restaurant);
	}
	public void testGetter(){
		assertEquals("pasta", foodDish.getName());
		assertEquals(10,0, foodDish.getPrice());
		assertEquals("lala", foodDish.getDescription());
		assertEquals(restaurant, foodDish.getRestaurant());
	}*/
}
