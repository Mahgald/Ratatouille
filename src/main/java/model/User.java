package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @class User 
 * 
 * Used to represent the User
 * 
 * @author Grupo7
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Proxy(lazy=false)
@Entity
@Table(name="User")
public class User  implements Active{//implements Active extends Observable
	
	/**
	 * Represents the user id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long idUser;
	
	/**
	 * Represents the name
	 */
	@Column(name="name")
	private String name;
	
	/**
	 * Represents the lastname
	 */
	@Column(name="lastName")
	private String lastName;
	
	/**
	 * Represents the e-mail
	 */
	@Column(name="email")
	private String email;
	
	/**
	 * Represents the password
	 */
	@Column(name="password")
	private String password;
	
	/**
	 * Represents the born date
	 */
	@Column(name="birthdate")
	private LocalDate birthdate;
	
	/**
	 * Represents the creation date account
	 */
	@Column(name="dateCreation")
	private LocalDateTime dateCreation;
	
	/**
	 * Represents the lowlogic
	 */
	@Column(name="lowLogic")
	private Boolean lowLogic=false;
	
	/**
	 * Represents the ranking
	 */
	@JsonManagedReference
	@OneToOne(mappedBy = "user",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private RankingUser ranking;
	
	
	//Estas listas son todas de conocimiento sobre mi mismo, que comentarios hice, quienes son mis amigos,que solicitudes envie, que platos comparti,

	/**
	 * Represents user commentaries
	 */
	@JsonIgnore
	@OneToMany(mappedBy="user", cascade={CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH} ,fetch=FetchType.EAGER)
	private List<Commentary> commentaries = new ArrayList<>();
	
	/**
	 * Represents elements shared
	 */
	@JsonIgnore
	@OneToMany(mappedBy="userShare", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SharedElement> sharedElement = new ArrayList<>();
	
	/**
	 * Represents friend request sender
	 */
	@JsonIgnore
	@OneToMany(mappedBy="sender", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FriendRequest> requestsSubmitted = new ArrayList<>();
	
	/**
	 * Represents friend list
	 */
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="friends")
	private Set<User> friends = new HashSet<>();
	
	/*estas listas son informacion de cosas que me hicieron a mi siguiendo el ejemplo de facebook, 
	las solicitudes de amistad no pueden verse en el muro a no ser que hayan sidof aceptadas, por tal motivo estan separadas de los platos compartidos
	los platos compartidos estan implementados como una lista porque es el unico caso, pero en realidad representaria todo lo que es visible en el muro
	o visto desde otra forma, todo lo que nos hayan compartdo, en un futuro podria crearse una interface o una clase abstrata llamada shareable
	y la coleccion de platos compartidos seria en realidad una coleccion de objetos shareables que se comporten de la misma forma, saber quien lo compartio, cuando etc*/
		
	/**
	 * Represents friend request received
	 */
	@JsonIgnore
	@OneToMany(mappedBy="recepter", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FriendRequest> received = new ArrayList<>();
	
	/**
	 * Represents elements shared to user 
	 */
	@JsonIgnore
	@ManyToMany( cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SharedElement> sharedElementToMe = new ArrayList<>();
		
	/*las notificaciones las tiene el usuario y pueden ser ocultadas aparte de vistas
	representan sfolo la funcion de avisar al usuario un acontecimiento que le incumbe.
	algo asi como poner un postick en el escritorio de un compa;ero de oficina con una nota*/
		
	/**
	 * Represents notifications
	 */
	@JsonIgnore
	@OneToMany( cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Notification> notifications = new ArrayList<>();

	/**
	 * Default constructor
	 */
	public User(){
		this.dateCreation=LocalDateTime.now();
		this.ranking=new Visitor(this);
		this.commentaries=new ArrayList<>();
		this.friends=new HashSet<>();
		this.received=new  ArrayList<>();
		this.notifications=new  ArrayList<>();
		this.requestsSubmitted=new  ArrayList<>();
		this.sharedElement=new  ArrayList<>();
		this.sharedElementToMe=new  ArrayList<>();
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

	/**
	 * constructor to create a new user
	 * @param name
	 * @param lastName
	 * @param email
	 * @param password
	 * @param date1
	 */
	public User(String name, String lastName, String email, String password, LocalDate date1) {
		this.setDateCreation(LocalDateTime.now());
		this.setName(name);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		this.setBirthdate(date1);
		this.setRanking(new Visitor(this));
	}
	
	/**
	 * thats constructor is used only for responsible to create new responsible of a user's instance
	 * @param user
	 */
	protected User(User user){
		//queda pendiente por el observer capaz modifica cosas...
		
		//first stage generate the data of user again
		this.setName(user.getName()); 
		this.setLastName(user.getLastName());
		this.setEmail(user.getEmail());
		this.setPassword(user.getPassword());
		this.setBirthdate(user.getBirthdate());
		this.setRanking(user.getRanking());
		this.setDateCreation(user.getDateCreation());
		this.setCommentaries(user.getCommentaries());
		this.setFriends(user.getFriends());
		this.setReceived(user.getReceived());
		this.setRequestsSubmitted(user.getRequestsSubmitted());
		this.setSharedElement(user.getSharedElement());
		this.setSharedElementToMe(user.getSharedElementToMe());
		
		this.setNotifications(user.getNotifications());
		//second stage update the data in all object know the user, replace the old reference with this new reference of user, as responsible 
		user.getRanking().setUser(this);
		for (User friend: user.getFriends()){
			friend.getFriends().remove(user);
			friend.getFriends().add(this);
		}
		user.getFriends().stream().forEach(x->x.getFriends().remove(user));
		user.getFriends().stream().forEach(x-> x.getFriends().add(this));
		
		
		/*for (Commentary commentary: user.getCommentaries()){
		
			commentary.setUser(this);
		}*/
		user.getCommentaries().stream().forEach(x-> x.setUser(this));
		user.getReceived().stream().forEach(x->x.setRecepter(this));
		user.getRequestsSubmitted().stream().forEach(x->x.setSender(this));
		user.getSharedElement().stream().forEach(x->x.setUserShare(this));
		user.getSharedElementToMe().stream().forEach(x->x.getUsersToShare().remove(user));
		user.getSharedElementToMe().stream().forEach(x->x.getUsersToShare().add(this));
		
		
		
		//user.getCommentariesOfRestaurant().stream().forEach(x->this.addObserver((Restaurant) ((CommentRestaurant)x).getContent()));
		//remember, map dont modified the existent data, map generate a new stream with new data modified
		//user.getFriends().stream().map(x-> x.getFriends().add(responsible));
		//user.getReceived().stream().map(x->x.getSender(responsible));
		//user.deleteObservers();
		
		} 

	/**
	 * Return that User as new Responsible
	 * @return Responsible
	 */
	private Responsible promove(){
		return new Responsible(this);
	}
	
	/**
	 * Makes a user, or responsible, responsible for this restaurant
	 * @param restaurant
	 * @return responsible
	 */
	public Responsible  becomeResposibleOf(Restaurant restaurant){
		 Responsible responsible= this.promove();
		 responsible.addResponsibleOf( restaurant);
		 return responsible;
	}
	
	/**
	 * Return the user as responsible
	 * 
	 * @return
	 */
	public Responsible  becomeResposible(){
		 Responsible responsible= this.promove();
		 
		 return responsible;
	}
	/**
	 * 
	 * @return false
	 */
	public boolean haveRestaurant() {
		return false;
	}
	
	/**
	 * Compare if the user is the same with the param email
	 * 
	 * @param email
	 * @return
	 */
	public Boolean areYou(String email) {
		return (this.getEmail().equals(email)); 
	}
		
	/**
	 * 
	 * @param email
	 * @param password
	 * @return return true if the email and password match otherwise return false 
	 */
	public Boolean areAccessData(String email, String password) {
		return (this.getEmail()==email && this.getPassword()==password);
	}

	/**
	 * Call her state for add comentarie
	 * @param commentRestaurant
	 */
	public void addComentary(Commentary comment) {
		this.getRanking().addComentary(comment);
	}
	
	/*public void addComentaryOfRestaurant(CommentRestaurant commentRestaurant) {
		this.getRanking().addComentaryOfRestaurant(commentRestaurant);
		
	}*/
	
	
	/**
	 * Increments the user commentaries counter in Ranking User
	 * @param counters
	 */
	public void addCommentOfTypeUser(int[] counters) {
		this.getRanking().addCommentOfTypeUser(counters);
	}
	
	
	/**
	 * Decrements the user commentaries counter in Ranking User
	 * @param counters
	 */
	public void subCommentOfTypeUser(int[] counters) {
		this.getRanking().subCommentOfTypeUser(counters);
	}
	
	/**
	 * 
	 * @param stream3
	 * @return
	 */
	public Boolean interesting(Stream<Boolean> stream3) {
		return this.getRanking().interesting(stream3);
	}
	
	/*
	
	public List<SharedFoodDish> showSharedFoodToMe(){
		return this.sharedFoodDishToMe.stream().filter(SharedFood -> !(SharedFood.isViewed())).collect(Collectors.toList());
	}
	
	public List<FriendRequest> showFriendRequest(){
		return this.received.stream().filter(friendRequest -> !(friendRequest.isViewed())).collect(Collectors.toList());
	}
		*/
	
	/**
	 * Send a friend request
	 * @param user
	 */
	public void SendFriendRequest(User user){
		//FriendRequest friendRequest =;
		
		this.getRequestsSubmitted().add(new FriendRequest(this, user));
		
	}
	
	public void addFriendRequest(User user, ArrayList<FriendRequest> requestsSubmitted){
		
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
	
	/**
	 * Removes the commentary of user commentaries counter in Ranking User
	 * @param counters
	 */
	public void removeCommentary(Commentary commentary) {
		// TODO Auto-generated method stub
		this.getRanking().removeCommentary(commentary);
		
	}
	
	
/*

	public List<Commentary> getCommentariesOfRestaurant() {
		return this.commentaries.stream().filter(commentaries -> (commentaries.ofRestaurant())).collect(Collectors.toList());
		
	}
	
*/
	//GETTERS AND SETTERS
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return idUser;
	}
	public void setId(Long id) {
		this.idUser = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate date1) {
		this.birthdate = date1;
	}
	public RankingUser getRanking() {
		return ranking;
	}
	public void setRanking(RankingUser ranking) {
		this.ranking = ranking;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	protected void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	public List<Commentary> getCommentaries() {
		return commentaries;
	}
	private void setCommentaries(List<Commentary> commentaries) {
		this.commentaries = commentaries;
	}
	public Set<User> getFriends() {
		return friends;
	}
	private void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	public List<FriendRequest> getRequestsSubmitted() {
		return requestsSubmitted;
	}
	private void setRequestsSubmitted(List<FriendRequest> requestsSubmitted) {
		this.requestsSubmitted = requestsSubmitted;
	}
	public List<FriendRequest> getReceived() {
		return received;
	}
	private void setReceived(List<FriendRequest> received) {
		this.received = received;
	}
	public List<SharedElement> getSharedElement() {
		return sharedElement;
	}
	private void setSharedElement(List<SharedElement> sharedElement) {
		this.sharedElement = sharedElement;
	}
	public List<SharedElement> getSharedElementToMe() {
		return this.sharedElementToMe;
	}
	private void setSharedElementToMe(List<SharedElement> sharedElementToMe) {
		this.sharedElementToMe = sharedElementToMe;
	}
	public LocalDateTime getDateCreation() {
		return this.dateCreation;
	}
	private void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Boolean getLowLogic() {
		return lowLogic;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setLowLogic(Boolean lowLogic) {
		this.lowLogic = lowLogic;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
}
