package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import helpers.RestaurantWithCounter;
import helpers.StadisticsOfUsers;
import helpers.UserWithCounter;
import model.Commensal;
import model.Commentary;
import model.Configuration;
import model.DomainObject;
import model.Restaurant;
import model.User;
import model.Visitor;
import service.RestaurantService;
import service.UserService;



@Controller
public class SharedController {

	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	UserService userService;
	
	
	
	
	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view=new ModelAndView("Index");
		 Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		 if (inputFlashMap != null) {
			  view.addObject("error",inputFlashMap.get("error"));
		  }
		 if((int)request.getSession().getAttribute("typeUserLoged")>0){
			 User user = userService.GetUserById((Long)request.getSession().getAttribute("idUser"));
				view.addObject("user",user);
		 }
		
		 
		 
		return view;
		
		
	}
	
	
	
	@RequestMapping(value = "Query/RestaurantsWithMoreCommentariesInRangeOfDate/{from}/{to}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RestaurantWithCounter>> RestaurantsWithMoreCommentariesInRangeOfDate(@PathVariable("from") String fromParam,@PathVariable("to") String toParam) {
		String[] partsFrom = fromParam.split("-");
		String[] partsTo = toParam.split("-");
		LocalDateTime from=LocalDateTime.of( Integer.parseInt(partsFrom[2]), Integer.parseInt(partsFrom[1]), Integer.parseInt(partsFrom[0]), 0, 0);
		LocalDateTime to=LocalDateTime.of(Integer.parseInt(partsTo[2]), Integer.parseInt(partsTo[1]), Integer.parseInt(partsTo[0]), 23, 59);
		
	List<Restaurant> restaurants = restaurantService.getActiveRestaurants();
	//int minTemporal=0;
	List<RestaurantWithCounter> listOfRestaurantWithCounters=new ArrayList<>();
	for (Restaurant restaurant : restaurants){
		
		int countTemporal=0;
		//List<User> responsibles=new ArrayList<>();
		List<Long> responsiblesIds=new ArrayList<>();
		for(Configuration configuration : restaurant.getConfiguration()){
			//responsibles.add(configuration.getResponsible());
			responsiblesIds.add(configuration.getResponsible().getId());
		}

		for(Commentary commentary : restaurant.getCommentaries()){
			if(commentary.getDate().isAfter(from) && commentary.getDate().isBefore(to) ){
				boolean auxNoAreResponsible=true;
				for(Long id : responsiblesIds){
					if(id.equals(commentary.getUser().getId())){
						auxNoAreResponsible=false;
					}
				}
				if(auxNoAreResponsible){
					countTemporal++;
				}
			}

		}

		RestaurantWithCounter restaurantWithCounter=new RestaurantWithCounter(restaurant,countTemporal);
		listOfRestaurantWithCounters.add(restaurantWithCounter);
	}
	Collections.sort(listOfRestaurantWithCounters);
	//ordenar la estructura de control
	System.out.println(listOfRestaurantWithCounters);

	//quedarse solo con diez
	
	
	List<RestaurantWithCounter> result=new ArrayList<>();
	int index=0;
	for (RestaurantWithCounter element :listOfRestaurantWithCounters){
		if (index<10){
			index++;
			result.add(element);
		}
	}


	//devolver la estructura en la respuesta

	if(result.isEmpty()){
		return new ResponseEntity<List<RestaurantWithCounter>>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<List<RestaurantWithCounter>>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "Query/UsersWithMoreCommentaries", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<UserWithCounter>> UsersWithMoreCommentaries() {
	List<User> users = userService.getActiveUsers();
	
	
	List<UserWithCounter> listOfUsertWithCounters=new ArrayList<>();
	for(User user:users){
		List<Commentary> commentaries =user.getCommentaries();
		int countTemporal=0;
		for (Commentary commentary:commentaries){
			if(!commentary.getContent().areResponsible(user)){
				countTemporal++;
			}
		}
		UserWithCounter userWithCounter=new UserWithCounter(user,countTemporal);
		listOfUsertWithCounters.add(userWithCounter);
		
	}
	Collections.sort(listOfUsertWithCounters);
	//ordenar la estructura de control
	System.out.println(listOfUsertWithCounters);

	//quedarse solo con diez
	
	

	List<UserWithCounter> result=new ArrayList<>();
	int index=0;
	for (UserWithCounter element :listOfUsertWithCounters){
		if (index<10){
			index++;
			result.add(element);
		}
	}
	
	
	if(result.isEmpty()){
		return new ResponseEntity<List<UserWithCounter>>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<List<UserWithCounter>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "Query/TotalOfUserInTheSystem", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<StadisticsOfUsers> TotalOfUserInTheSystem() {
	List<User> users = userService.getUsers();
	StadisticsOfUsers stadisticsUsers=new StadisticsOfUsers();
	for(User user: users){
		if(user.getLowLogic()){//dado de baja
			if(user.getRanking()instanceof Visitor){//visitor
				stadisticsUsers.addNoActiveVisitor();
			}else if(user.getRanking()instanceof Commensal){//commensal
				stadisticsUsers.addNoActiveCommensal();
			}else{//gourmet
				stadisticsUsers.addNoActiveGourmet();
			}
		}else{
			if(user.getRanking()instanceof Visitor){//visitor
				stadisticsUsers.addActiveVisitor();
			}else if(user.getRanking()instanceof Commensal){//commensal
				stadisticsUsers.addActiveCommensal();
			}else{//gourmet
				stadisticsUsers.addActiveGourmet();
			}
		}
	}
	
	return new ResponseEntity<StadisticsOfUsers>(stadisticsUsers, HttpStatus.OK);
	}
	
	
	
	
	
/*	@RequestMapping(value = "Query/RestaurantsProximity/{latitude}/{longitude}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Restaurant>> RestaurantsProximity(@PathVariable("partOfName") String partOfName) {
	List<Restaurant> restaurants = restaurantService.getRestaurantsAndFoodDishLikeWords();
	
	if(domainObjects.isEmpty()){
		return new ResponseEntity<List<Restaurant>>(HttpStatus.NO_CONTENT);
	}
	return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
	}*/
	
}