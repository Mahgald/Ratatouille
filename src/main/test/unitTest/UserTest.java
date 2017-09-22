package unitTest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.stream.IntStream;

import junit.framework.TestCase;
import model.Commensal;
import model.CommentRestaurant;
import model.FoodDish;
import model.FriendRequest;
import model.SpecialNotification;
import model.Gourmet;
import model.Responsible;
import model.Restaurant;
import model.Ubication;
import model.User;
import model.Valoration;
import model.Visitor;
import model.WebSite;

public class UserTest extends TestCase {
	
	
	private Ubication ubication;
	private WebSite webSite;
	private Restaurant restaurant;
	private Valoration valoration;
	
	//variables usadas
	private String email1,email2;
	private String lastname1,lastname2;
	private String name1,name2;
	private User user,user2;
	private String pass1,pass2;
	private LocalDate date2,date1;
	private String namer1,namer2;
	private Ubication ubication1,ubication2;
	
	private String namem1,namem2,namem3;
	private int price1,price2,price3;
	private String descm1,descm2,descm3;
	
	
			//LocalDate.of(1992, 8, 24);
	
	protected void setUp(){
		/*
		Calendar cal =  Calendar.getInstance();
		cal.set(1992,8,24);
		System.out.println(cal);
		
		LocalDate dat;
		LocalDateTime dat2;
		//dat=LocalDate.now();
		//dat=LocalDate.of(1992, 8, 25);
		dat2=LocalDateTime.now();
		LocalDateTime dat3;
		dat3=LocalDateTime.now();
		dat=LocalDate.from(dat3);
		dat3.compareTo(dat2);
		
		System.out.println(dat3);
		System.out.println(dat);*/
		
		this.name1="Martin";
		this.lastname1="Valdez";
		this.email1="martin@mail.com";
		this.pass1="123456";
		this.date1=LocalDate.of(1992, 8, 24);
		
		this.name1="Ricardo";
		this.lastname1="Surribas";
		this.email1="Ricardo@mail.com";
		this.pass1="654321";
		this.date2=LocalDate.of(1990, 5, 15);
		
		this.namer1="resturant1";
		this.namer2="resturant2";
		this.ubication1=new Ubication(1,2);
		this.ubication2=new Ubication(3,4);
	}
	
	public void before(){
		
	}
	
	public void after(){
		
	}
	
	
	public void testCreateUser(){
//		
//		Probe padre;
//		ProbeSon hijo;
//		hijo=new ProbeSon();
//		padre=hijo;
//		System.out.println(hijo.Dato());
//		System.out.println(padre.Dato());
		
		
		
		
		
		this.user=new User(this.name1,this.lastname1,this.email1,this.pass1,this.date1);
		assertNotNull(user);
		assertEquals(this.name1, user.getName());
		assertEquals(this.lastname1, user.getLastName());
		assertEquals(this.email1, user.getEmail());
		assertEquals(this.date1, user.getBirthdate());
		assertTrue(user.areAccessData(this.email1, this.pass1));
		assertEquals(0,user.getFriends().size());
		assertEquals(0,user.getNotifications().size());
		assertEquals(0,user.getCommentaries().size());
		assertEquals(0,user.getReceived().size());
		assertEquals(0,user.getRequestsSubmitted().size());
		assertEquals(0,user.getSharedFoodDish().size());
		assertEquals(0,user.getSharedFoodDishToMe().size());
		assertFalse(user.haveRestaurant());
		assertEquals(0,user.countObservers());
		assertEquals(user.getRanking().getClass(),Visitor.class);
		
		
		//assertTrue(user.getDateCreation().before..before(LocalDate.now()));
	}
	
	
	
	
	public void testSendRequestFriend(){
		this.user=new User(this.name1,this.lastname1,this.email1,this.pass1,this.date1);
		this.user2=new User(this.name2,this.lastname2,this.email2,this.pass2,this.date2);
		
		
		
		int cantFrinds1,cantsubmit1,cantnotif1,cantfriends2,cantreceived2,cantnotif2;
		cantFrinds1=user.getFriends().size();
		cantsubmit1=user.getRequestsSubmitted().size();
		cantnotif1=user.getNotifications().size();
		cantfriends2=user2.getFriends().size();
		cantreceived2=user2.getReceived().size();
		cantnotif2=user2.getNotifications().size();
		
		user.SendFriendRequest(user2);
		assertEquals(cantFrinds1,user.getFriends().size());
		assertEquals(cantfriends2,user2.getFriends().size());
		assertEquals(cantnotif1,user.getNotifications().size());
		assertEquals(cantsubmit1+1,user.getRequestsSubmitted().size());
		assertEquals(cantnotif2+1,user2.getNotifications().size());
		assertEquals(cantreceived2+1,user2.getReceived().size());
		assertFalse(((SpecialNotification)user2.getNotifications().get(0)).isViewed());
		assertEquals(this.email1, user.getRequestsSubmitted().get(0).getSender().getEmail());
		assertEquals(this.email2, user.getRequestsSubmitted().get(0).getRecepter().getEmail());
		
		assertEquals(this.email1, user2.getReceived().get(0).getSender().getEmail());
		assertEquals(this.email2, user2.getReceived().get(0).getRecepter().getEmail());

		assertFalse(((FriendRequest)user2.getNotifications().get(0).goTo()).getRequestEvaluated());
		assertEquals(this.email1,((FriendRequest)user2.getNotifications().get(0).goTo()).getSender().getEmail());
		assertEquals(this.email2,((FriendRequest)user2.getNotifications().get(0).goTo()).getRecepter().getEmail());
		assertTrue(((SpecialNotification)user2.getNotifications().get(0)).isViewed());
		
		assertEquals(this.email1,user.getRequestsSubmitted().get(0).getSender().getEmail());
		assertEquals(this.email2,user.getRequestsSubmitted().get(0).getRecepter().getEmail());
		
		assertEquals(this.email1,user2.getReceived().get(0).getSender().getEmail());
		assertEquals(this.email2,user2.getReceived().get(0).getRecepter().getEmail());
		//falta probar fechas
	
	}
	
	public void testConfirmRequestFriend(){
		this.user=new User(this.name1,this.lastname1,this.email1,this.pass1,this.date1);
		this.user2=new User(this.name2,this.lastname2,this.email2,this.pass2,this.date2);
		user.SendFriendRequest(user2);
		
		int cantFrinds1,cantsubmit1,cantnotif1,cantfriends2,cantreceived2,cantnotif2;
		cantFrinds1=user.getFriends().size();
		cantsubmit1=user.getRequestsSubmitted().size();
		cantnotif1=user.getNotifications().size();
		cantfriends2=user2.getFriends().size();
		cantreceived2=user2.getReceived().size();
		cantnotif2=user2.getNotifications().size();	

		user2.getReceived().get(0).confirm(user);
		
		assertEquals(cantFrinds1,user.getFriends().size());
		assertEquals(cantfriends2,user2.getFriends().size());
		assertEquals(cantnotif1,user.getNotifications().size());
		assertEquals(cantsubmit1,user.getRequestsSubmitted().size());
		assertEquals(cantnotif2,user2.getNotifications().size());
		assertEquals(cantreceived2,user2.getReceived().size());
		
		
		
		//assertFalse(((FriendRequestNotification)user.getNotifications().get(0)).isViewed());
		user2.getReceived().get(0).confirm(user2);
		
		assertEquals(cantFrinds1+1,user.getFriends().size());
		assertEquals(cantfriends2+1,user2.getFriends().size());
		assertEquals(cantnotif1+1,user.getNotifications().size());
		assertEquals(cantsubmit1,user.getRequestsSubmitted().size());
		assertEquals(cantnotif2,user2.getNotifications().size());
		assertEquals(cantreceived2,user2.getReceived().size());
		
		
		assertFalse(((SpecialNotification)user.getNotifications().get(0)).isViewed());
		assertTrue(((FriendRequest)user.getNotifications().get(0).goTo()).getRequestEvaluated());
		assertTrue(((FriendRequest)user2.getNotifications().get(0).goTo()).isRequestStatus());
		assertTrue(((SpecialNotification)user.getNotifications().get(0)).isViewed());

		assertEquals(this.email2, user.getFriends().get(0).getEmail());
		assertEquals(this.email1, user2.getFriends().get(0).getEmail());
		
		assertEquals(this.email1,((FriendRequest)user.getNotifications().get(0).goTo()).getSender().getEmail());
		assertEquals(this.email2,((FriendRequest)user.getNotifications().get(0).goTo()).getRecepter().getEmail());
		assertEquals(this.email1,((FriendRequest)user2.getNotifications().get(0).goTo()).getSender().getEmail());
		assertEquals(this.email2,((FriendRequest)user2.getNotifications().get(0).goTo()).getRecepter().getEmail());
		
	}
	
	public void testRejectRequestFriend(){
		this.user=new User(this.name1,this.lastname1,this.email1,this.pass1,this.date1);
		this.user2=new User(this.name2,this.lastname2,this.email2,this.pass2,this.date2);
		user.SendFriendRequest(user2);
		
		int cantFrinds1,cantsubmit1,cantnotif1,cantfriends2,cantreceived2,cantnotif2;
		cantFrinds1=user.getFriends().size();
		cantsubmit1=user.getRequestsSubmitted().size();
		cantnotif1=user.getNotifications().size();
		cantfriends2=user2.getFriends().size();
		cantreceived2=user2.getReceived().size();
		cantnotif2=user2.getNotifications().size();	

		user2.getReceived().get(0).reject(user);
		
		assertEquals(cantFrinds1,user.getFriends().size());
		assertEquals(cantfriends2,user2.getFriends().size());
		assertEquals(cantnotif1,user.getNotifications().size());
		assertEquals(cantsubmit1,user.getRequestsSubmitted().size());
		assertEquals(cantnotif2,user2.getNotifications().size());
		assertEquals(cantreceived2,user2.getReceived().size());
		
		//OJO FALTA AGREGAR QUE CUANDO HAGAS ALGO CON UN CONTENIDO DE NOTIFICACION BUSQUES SU NOTIFICACION Y LA PONGAS EN VISTO
		
		user2.getReceived().get(0).reject(user2);
		
		assertEquals(cantFrinds1,user.getFriends().size());
		assertEquals(cantfriends2,user2.getFriends().size());
		assertEquals(cantnotif1+1,user.getNotifications().size());
		assertEquals(cantsubmit1,user.getRequestsSubmitted().size());
		assertEquals(cantnotif2,user2.getNotifications().size());
		assertEquals(cantreceived2,user2.getReceived().size());
		
		//assertTrue(((FriendRequestNotification)user2.getNotifications().get(0)).isViewed());
		assertTrue(((FriendRequest)user2.getNotifications().get(0).goTo()).getRequestEvaluated());
		assertTrue(((SpecialNotification)user2.getNotifications().get(0)).isViewed());
		assertFalse(((FriendRequest)user2.getNotifications().get(0).goTo()).isRequestStatus());
		
		assertFalse(((SpecialNotification)user.getNotifications().get(0)).isViewed());
		assertTrue(((FriendRequest)user.getNotifications().get(0).goTo()).getRequestEvaluated());
		assertTrue(((SpecialNotification)user.getNotifications().get(0)).isViewed());
		

	
		
		assertEquals(this.email1,((FriendRequest)user.getNotifications().get(0).goTo()).getSender().getEmail());
		assertEquals(this.email2,((FriendRequest)user.getNotifications().get(0).goTo()).getRecepter().getEmail());
		assertEquals(this.email1,((FriendRequest)user2.getNotifications().get(0).goTo()).getSender().getEmail());
		assertEquals(this.email2,((FriendRequest)user2.getNotifications().get(0).goTo()).getRecepter().getEmail());
		
	}
	
	public void testChangeStateRanking(){
		Restaurant rest1,rest2,rest3;
		FoodDish food1,food2,food3;
		
		
		rest1=new Restaurant(this.namer1,this.ubication1);
		food1=new FoodDish(this.namem1, this.price1, this.descm1, rest1);
		
		this.user=new User(this.name1,this.lastname1,this.email1,this.pass1,this.date1);
		
		food1.comment(user, "comentario1", Valoration.NEUTRAL);
		rest1.comment(user, "comentario1", Valoration.NEUTRAL);
		IntStream.range(1, 18).forEach(x->food1.comment(user, "comentario"+x, Valoration.NEGATIVE));
		assertEquals(19, user.getCommentaries().size());
		assertEquals(user.getRanking().getClass(),Visitor.class);
		food1.comment(user, "comentario20", Valoration.NEUTRAL);
		assertEquals(20, user.getCommentaries().size());
		assertEquals(user.getRanking().getClass(),Commensal.class);
		IntStream.range(21, 40).forEach(x->rest1.comment(user, "comentario"+x, Valoration.NEGATIVE));
		assertEquals(39, user.getCommentaries().size());
		assertEquals(user.getRanking().getClass(),Commensal.class);
		rest1.comment(user, "comentario40", Valoration.NEGATIVE);
		assertEquals(40, user.getCommentaries().size());
		assertEquals(user.getRanking().getClass(),Gourmet.class);
	}
	
	
	
	
	/*
	protected void setUp(){
		
		
		valoration = Valoration.POSITIVE;
		
		webSite = WebSite.Instance();
		webSite.getListRestaurant().clear();
		webSite.getListUser().clear();
		webSite.createUser("pepe", "lopez", "pepe@gmail.com", "123", date, ubication);
		user = webSite.getListUser().get(0);
		webSite.setUserLoged(user);
		webSite.createRestaurant("PEPE", ubication);
		restaurant = webSite.getListRestaurant().get(0);
	}*/
	/**
	 * ejemplo de test
	 */
	/*public void testCreateUser(){
		assertNotNull(user);
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public void testName(){
		assertEquals("pepe", user.getName());
	}
	public void testLastName(){
		assertEquals("lopez", user.getLastName());
	}
	public void testEmail(){
		assertEquals("pepe@gmail.com", user.getEmail());
	}

	public void testBirthdate(){
		assertEquals(date, user.getBirthdate());
		
	}
	

	public void testComment(){
		CommentRestaurant commentRestaurant = new CommentRestaurant(user,restaurant,"lll",valoration);
		user.addComentary(commentRestaurant);
		assertEquals(2, user.getCommentariesOfRestaurant().size());
	}*/
	
	
	/**
	 *@MODIFIED by martin valdez 
	 * 
	 */
	/*USER NO TIENE MAS UBICACION
	public void testUbication(){
		assertEquals(ubication, user.getUbication());
	}
	*/
	/*se usa el metodo areAccessData(email, password)
	 * public void testPassword(){
		assertEquals("123", user.getPassword());
	}
	*
	*/
}
