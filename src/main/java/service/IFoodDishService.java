package service;

import java.util.Collection;
import java.util.List;

import helpers.ResultObject;
import model.Commentary;
import model.DomainObject;
import model.FoodDish;
import model.Restaurant;

public interface IFoodDishService {

	public List<DomainObject> getFoodDishLikeWords(String partOfName);

	public FoodDish saveFoodDish(FoodDish foodDish);

	public ResultObject saveComentaryOfFoodDish(Commentary commentary, Long idFoodDish, String valoration,
			String attribute);

	public ResultObject getRestaurantOfThisFoodDish(Long idFoodDish);

	public ResultObject deleteComentaryOfFoodDish(Long idCommentary, String attribute);

	public ResultObject changeComentaryOfFoodDish(Long idCommentaryOfFoodDish, String attribute,
			String newDescriptionCommentaryOfFoodDish, String newValorationCommentaryOfFoodDish);

	public ResultObject ShareFoodDish(Restaurant restaurant, Long idFoodDishToShare, String descriptionToShareFoodDish,
			Long[] friendsToShareFoodDish, String mail);

}
