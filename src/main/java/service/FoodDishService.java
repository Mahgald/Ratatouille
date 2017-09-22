package service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import dao.FoodDishDao;
import dao.UserDao;
import helpers.ResultObject;
import model.Commentary;
import model.DomainObject;
import model.FoodDish;
import model.Restaurant;
import model.User;
import model.Valoration;


public class FoodDishService implements IFoodDishService {

	@Autowired
	FoodDishDao foodDishDAO;

	@Autowired
	UserService userService;
	
	@Autowired
	CommentaryService commentaryService;
	
	@Autowired
	UserDao userDAO;

	@Autowired
	RestaurantService restaurantService;
	
	
	public List<DomainObject> getFoodDishLikeWords(String partOfName){
		
		return foodDishDAO.listOfFoodDishMatchWhitThisWords(partOfName);
		
	}

	@Override
	public FoodDish saveFoodDish(FoodDish foodDish) {
		return foodDishDAO.persistSecond(foodDish);
		
	};
	
	public ResultObject saveComentaryOfFoodDish(Commentary commentary, Long idFoodDish, String valoration,String email){
		ResultObject resultObject=new ResultObject();
		User user =userService.GetUserWithThisMail(email);
		FoodDish foodDish=foodDishDAO.getEntity(idFoodDish);
		
		resultObject.setResult(foodDish.comment(user, commentary.getDescription(), Valoration.valueOf(valoration)));//
		
		if(resultObject.getResult()){
			foodDishDAO.update3(foodDish);
			userService.clearInconsistenceRanking(user);
			resultObject.setResult(true);
		}else{
			resultObject.getMessages().put("ERROR IN CONTENT", "Lo sentimos pero no puede comentar un plato de un restaurant que pertenezca a un restaurant que este a menos de un kilometro de cualquier restaurant del que sea o haya sido responsable");
			
		}
		
		

		resultObject.setContent(foodDish);
		
		return resultObject;
		
	}

	@Override
	public ResultObject getRestaurantOfThisFoodDish(Long idFoodDish) {
		ResultObject resultObject=new ResultObject();
		resultObject.setContent(foodDishDAO.getRestaurantContainThisFoodDish(idFoodDish));
		resultObject.setResult(true);
		return resultObject;
	}

	@Override
	public ResultObject deleteComentaryOfFoodDish(Long idCommentary, String mail) {
		
		
		ResultObject resultObject=commentaryService.getCommentaryWithThisId(idCommentary);
		
		
		if (resultObject.getResult()){
			if (!((Commentary)resultObject.getContent()).getUser().getEmail().equals(mail)){
				resultObject.setResult(false);
				resultObject.getMessages().put("ERROR IN CONTENT","Este comentario no le pertenece");
			}
			if (((Commentary)resultObject.getContent()).getUser().getLowLogic()){
				resultObject.setResult(false);
				if (resultObject.getMessages().containsKey("ERROR IN CONTENT")){
					resultObject.getMessages().put("ERROR IN CONTENT", resultObject.getMessages().get("ERROR IN CONTENT")+" Usted esta dado de baja por lo cual no puede eliminar sus comentarios");
				}else{
					resultObject.getMessages().put("ERROR IN CONTENT", "Usted esta dado de baja por lo cual no puede eliminar sus comentarios");
				}
			}



			
		}else{
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR IN CONTENT", "No encontramos el comentario que desea eliminar");
		}
		if(resultObject.getResult()){
			((Commentary)resultObject.getContent()).delete();
			//commentaryService.updateCommentary((Commentary)resultObject.getContent());
			User user=((Commentary)resultObject.getContent()).getUser();
			DomainObject foodDish=((Commentary)resultObject.getContent()).getContent();
		
			foodDishDAO.update((FoodDish)foodDish);
			userService.updateAndAreConsistence(user);
		}


		
		return resultObject;
	}

	@Override
	public ResultObject changeComentaryOfFoodDish(Long idCommentaryOfFoodDish, String mail,
			String newDescriptionCommentaryOfFoodDish, String newValorationCommentaryOfFoodDish) {

		ResultObject resultObject=commentaryService.getCommentaryWithThisId(idCommentaryOfFoodDish);
		
		if(resultObject.getResult()){//existe el comentario
			if (!((Commentary)resultObject.getContent()).getUser().getEmail().equals(mail)){
				resultObject.setResult(false);
				resultObject.getMessages().put("ERROR IN CONTENT","Este comentario no le pertenece");
			}
			if (((Commentary)resultObject.getContent()).getUser().getLowLogic()){
				resultObject.setResult(false);
				if (resultObject.getMessages().containsKey("ERROR IN CONTENT")){
					resultObject.getMessages().put("ERROR IN CONTENT", resultObject.getMessages().get("ERROR IN CONTENT")+" Usted esta dado de baja por lo cual no puede eliminar sus comentarios");
				}else{
					resultObject.getMessages().put("ERROR IN CONTENT", "Usted esta dado de baja por lo cual no puede eliminar sus comentarios");
				}
			}
			
		}else{//no hay comentario
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR IN CONTENT", "No encontramos el comentario que desea eliminar");
		}
		
		if(resultObject.getResult()){
			((Commentary)resultObject.getContent()).setValoration(Valoration.valueOf(newValorationCommentaryOfFoodDish));
			((Commentary)resultObject.getContent()).setDescription(newDescriptionCommentaryOfFoodDish);
			((Commentary)resultObject.getContent()).setDate(LocalDateTime.now());
			commentaryService.updateCommentary((Commentary)resultObject.getContent());

		}
		return resultObject;
	}

	@Override
	public ResultObject ShareFoodDish(Restaurant restaurant, Long idFoodDishToShare, String descriptionToShareFoodDish,
			Long[] friendsToShareFoodDish, String mail) {
		ResultObject resultObject=new ResultObject();
		
		
		Restaurant resto = restaurantService.getRestaurantById(restaurant.getIdDomainObject());
		if(friendsToShareFoodDish != null){
			FoodDish foodDishToShare = null;
			for (FoodDish foodDish: resto.getMenu()){
				if (foodDish.getIdDomainObject().equals(idFoodDishToShare)){
					foodDishToShare=foodDish;
				}
			}
			User userShare=userService.GetUserWithThisMail(mail);
			Set<User> usersToShare=userDAO.getGroupOfUserWithThisIds(friendsToShareFoodDish);
			for (User userToShare:usersToShare){
				System.out.println(userToShare.getName());
			}
			foodDishToShare.share(userShare, descriptionToShareFoodDish, usersToShare);
		
			for (User amigo : usersToShare) {
				System.out.println(amigo.getSharedElementToMe().size());
				System.out.println(amigo.getName());
				
			}
			userDAO.update3(userShare);
		for (Long amigo : friendsToShareFoodDish) {
			System.out.println(amigo);
			
		}
		}else{
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR IN MODAL SHARE FOODDISH", "No selecciono ningun amigo para compartir este contenido");
			
			resultObject.getMessages().put("idFoodDish", idFoodDishToShare.toString());
		}
		System.out.println("que locuraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		
		
		
		return resultObject;
	}
}
