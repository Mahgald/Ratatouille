package service;

import java.util.List;
import java.util.Set;


import helpers.ResultObject;
import model.Commentary;
import model.DomainObject;
import model.FoodDish;
import model.Restaurant;

public interface IRestaurantService {


	public ResultObject newRestaurant(Restaurant restaurant, String mail);
	
	public Restaurant getRestaurant(Restaurant restaurant);
	
	
	public List<Restaurant> getRestaurants();
	public Restaurant getRestaurantsByPartName(String name);
	
	public boolean updateRestaurant(Restaurant oldRestaurant,Restaurant newRestaurant);
	
	public boolean deleteRestaurant(Restaurant restaurant);

	public Restaurant getRestaurantById(Long idRestaurant);

	public List<DomainObject> getRestaurantsLikeWords(String partOfName);

	public List<Restaurant> getRestaurantsInProximity(String partOfName, int distance, double latitude,	double longitude);

	public List<DomainObject> getRestaurantsAndFoodDishLikeWords(String partOfName);

	public ResultObject ChangeConfigurationOfRestaurantForResponsible(Restaurant restaurant, String attribute,
			List<Boolean> combination);

	public ResultObject saveNewFoodDishOfRestaurant(FoodDish foodDish);

	public ResultObject getRestaurantByIdForShow(Long idRestaurant);

	public ResultObject deleteThisResponsibleOfThisRestaurant(Restaurant restaurant, Long idResponsible);

	public ResultObject deleteThisFoodDishOfThisRestaurant(Restaurant restaurant, Long idFoodDish,Long idResponsible);

	public ResultObject changeThisFoodDishOfThisRestaurant(Restaurant restaurant, Long idFoodDish, Long attribute,
			String newNameFoodDish, float newPriceFoodDish, String newDescriptionFoodDish);

	public ResultObject reactivateFoodDishOfThisRestaurant(Long idDomainObject, Long idFoodDish, Long attribute);

	public ResultObject saveComentaryOfRestaurant(Commentary commentary, Long idRestaurant, String valoration,
			String attribute);

	public ResultObject deleteComentaryOfRestaurant(Long idCommentary, String attribute,Long idRestaurant);

	public ResultObject changeCommentaryOfRestaurant(Long idCommentaryOfRestaurant,
			String newDescriptionCommentaryOfRestaurant, String newValorationCommentaryOfRestaurant,String mail);

	public ResultObject deactivateRestaurant(Restaurant restaurant, String attribute);

	public ResultObject reactivateRestaurant(Restaurant restaurant, String attribute);

	public ResultObject changeInformationOfRestaurant(Restaurant restaurant, String addresToChange, String attribute);

	


	
}

