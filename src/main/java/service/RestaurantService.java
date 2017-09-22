package service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import dao.RestaurantCategoryDao;
import dao.RestaurantDao;
import dao.UserDao;
import helpers.ResultObject;
import model.Commentary;
import model.Configuration;
import model.DomainObject;
import model.FilterByCategorieUser;
import model.FoodDish;
import model.Responsible;
import model.Restaurant;
import model.RestaurantCategory;
import model.User;
import model.Valoration;


public class RestaurantService implements IRestaurantService{
	@Autowired
	IFoodDishService foodDishService;
	
	@Autowired
	RestaurantDao restaurantDAO;
	
	@Autowired
	ResponsibleService responsibleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentaryService commentaryService;
	
	@Autowired
	UserDao userDAO;
	
	@Autowired
	RestaurantCategoryDao restaurantCategoryDAO;
	
	
	public ResultObject newRestaurant(Restaurant restaurant,String mail){
		ResultObject resultObject=new ResultObject();
		List<Restaurant> restaurants= this.getRestaurants();
		if (restaurantDAO.existWithThisNameAndDirection(restaurant.getName(),restaurant.getUbication().getDirection())) {
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR IN CONTENT", "Ya existe un restaurant con ese nombre en esa direccion");
		}else{
			if (responsibleService.existByMailOrUpgrade(mail)){
				Responsible responsible =responsibleService.getEntityByMail(mail);
				responsible.addResponsibleOf( restaurant);
				resultObject.setContent(restaurantDAO.persistSecond(restaurant));
				resultObject.setResult(true);
			}
		//
		}
		return resultObject;
	}
	
	public Restaurant getRestaurant(Restaurant restaurant){
		return null;
	}
	
	public Restaurant getRestaurantsByPartName(String name){
		return null;
	}
	
	public boolean updateRestaurant(Restaurant oldRestaurant,Restaurant newRestaurant){
		return false;
	}
	
	public boolean deleteRestaurant(Restaurant restaurant){
		return false;
	}


	public List<Restaurant> getRestaurants() {
		return  restaurantDAO.list();
	}
	
	public Restaurant getRestaurantById(Long id){
		return restaurantDAO.getEntity(id);
	}
	public List<Restaurant> getRestaurantsInProximity(String partOfName, int distance, double latitude, double longitude){
		
		return restaurantDAO.listOfRestaurantInProximity(partOfName,distance,latitude,longitude);
		
	};

	public List<DomainObject> getRestaurantsLikeWords(String partOfName){
		
		return restaurantDAO.listOfRestaurantsMatchWhitThisWords(partOfName);
		
	};
	
	public List<DomainObject> getRestaurantsAndFoodDishLikeWords(String partOfName){
		List<DomainObject> result = this.getRestaurantsLikeWords(partOfName);
		result.addAll(foodDishService.getFoodDishLikeWords(partOfName));
		
		return result;
		
	}

	@Override
	public ResultObject ChangeConfigurationOfRestaurantForResponsible(Restaurant restaurant, String email,
			List<Boolean> combination) {
		ResultObject resultObject=new ResultObject();
		Restaurant resto = this.getRestaurantById(restaurant.getIdDomainObject());
		Set<Configuration> configurations = resto.getConfiguration();
		for (Configuration configuration : configurations) {
			if(configuration.getResponsible().getEmail().equals(email)){
				FilterByCategorieUser filter = (FilterByCategorieUser) configuration.getFilter();
				filter.setCombination(combination);
				restaurantDAO.update(resto);
				resultObject.setResult(true);
				resultObject.getMessages().put("message", "Se ha modificado su configuracion para filtrar notificaciones exitosamente");
			}
		}

		return resultObject;
	}

	@Override
	public ResultObject saveNewFoodDishOfRestaurant(FoodDish foodDish) {
		ResultObject resultObject=new ResultObject();
		Restaurant restaurant =this.getRestaurantById(foodDish.getRestaurant().getIdDomainObject());
		resultObject.setResult(true);
		for(FoodDish foodDishOfMenu : restaurant.getMenu()){
			if (foodDishOfMenu.getName().equals(foodDish.getName()) && (foodDishOfMenu.getDescription().equals(foodDish.getDescription()))){
				resultObject.setResult(false);
			}
		}
		if (resultObject.getResult()){
			restaurant.getMenu().add(foodDish);
			foodDish.setRestaurant(restaurant);
			resultObject.setContent(foodDishService.saveFoodDish(foodDish));
		}else{
			resultObject.getMessages().put("ERROR NEW FOODDISH", "Ya existe un plato con el mismo nombre y la misma descripcion en el menu de este restaurant");
			resultObject.setContent(foodDish);
		}
		return resultObject;
	}

	@Override
	public ResultObject getRestaurantByIdForShow(Long idRestaurant) {
		ResultObject resultObject=new ResultObject();
		if (restaurantDAO.exist(idRestaurant)){
			resultObject.setContent(this.getRestaurantById(idRestaurant));
			resultObject.setResult(true);
		}else{
			resultObject.setResult(false);
		}
		return resultObject;
	}

	@Override
	public ResultObject deleteThisResponsibleOfThisRestaurant(Restaurant restaurant, Long idResponsible) {
		ResultObject resultObject=new ResultObject();

		Restaurant resto = this.getRestaurantById(restaurant.getIdDomainObject());
		
		Set<Configuration> configurations = resto.getConfiguration();
		int activeResponsibles=0;
		
		for (Configuration configuration : configurations) {
			if((!configuration.getResponsible().getLowLogic())&& (!configuration.getLowLogic())){
				activeResponsibles++;
			}
		}
		if(activeResponsibles>1){
			for (Configuration configuration : configurations) {
				if(configuration.getResponsible().getIdUser().equals(idResponsible)){
					configuration.setLowLogic(true);
					
				}
			}
			restaurantDAO.update(resto);
			resultObject.setResult(true);
		}else{
			
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR IN MODAL CHANGE RESPONSIBLES", "No se puede dejar un restaurant sin ningun responsable");
		}
		
	
		return resultObject;
	}

	@Override
	public ResultObject deleteThisFoodDishOfThisRestaurant(Restaurant restaurant, Long idFoodDish,Long idResponsible) {
		ResultObject resultObject=new ResultObject();
		Restaurant resto = this.getRestaurantById(restaurant.getIdDomainObject());
		Set<Configuration> configurations = resto.getConfiguration();
		resultObject.setResult(false);
		for (Configuration configuration : configurations) {
			if(configuration.getResponsible().getIdUser().equals(idResponsible)&& (!configuration.getResponsible().getLowLogic()) && (!configuration.getLowLogic())){
				resultObject.setResult(true);
			}
		}
		if (resultObject.getResult()){
			resultObject.setResult(false);
			List<FoodDish> menu = resto.getMenu();
			for (FoodDish foodDish : menu) {
				if(foodDish.getIdDomainObject().equals(idFoodDish)&& !foodDish.isLowlogic() ){
					foodDish.setLowlogic(true);
					resultObject.setResult(true);
				}
			}
			if (resultObject.getResult()){
				restaurantDAO.update(resto);
			}else{
				resultObject.getMessages().put("ERROR IN CONTENT", "No hemos llevado a cabo su accion de deshabilitar este plato porque ya se encontraba desahabilitado");
			}
		}else{
			resultObject.getMessages().put("ERROR IN CONTENT", "Lo sentimos pero solo un responsable del restaurant puede dar de deshabilitar un plato, posiblemente otro responsable le quito sus privilegios recientemente.");
		}
		return resultObject;
	}

	@Override
	public ResultObject changeThisFoodDishOfThisRestaurant(Restaurant restaurant, Long idFoodDish, Long idResponsible,
			String newNameFoodDish, float newPriceFoodDish, String newDescriptionFoodDish) {
		ResultObject resultObject=new ResultObject();
		Restaurant resto = this.getRestaurantById(restaurant.getIdDomainObject());
		Set<Configuration> configurations = resto.getConfiguration();
		resultObject.setResult(false);
		for (Configuration configuration : configurations) {
			if(configuration.getResponsible().getIdUser().equals(idResponsible)&& (!configuration.getResponsible().getLowLogic()) && (!configuration.getLowLogic())){
				resultObject.setResult(true);
			}
		}
		if (resultObject.getResult()){
			List<FoodDish> menu = resto.getMenu();
			for (FoodDish foodDish : menu) {
				if((foodDish.getName().equals(newNameFoodDish)) && (foodDish.getDescription().equals(newDescriptionFoodDish)) && (!foodDish.getIdDomainObject().equals(idFoodDish))){
					resultObject.setResult(false);
					resultObject.getMessages().put("ERROR IN MODAL CHANGE FOODDISH", "Lo sentimos pero ya hay otro plato en el menu de este restaurant con el nombre y descripcion ingresados");
					resultObject.getMessages().put("idFoodDish", idFoodDish.toString());
				}
			}
			if(resultObject.getResult()){
				resultObject.setResult(false);
				for (FoodDish foodDish : menu) {
					if(foodDish.getIdDomainObject().equals(idFoodDish)){
						foodDish.setName(newNameFoodDish);
						foodDish.setPrice(newPriceFoodDish);
						foodDish.setDescription(newDescriptionFoodDish);
						resultObject.setResult(true);
					}
				}
				if (resultObject.getResult()){
					restaurantDAO.update(resto);
				}else{
					resultObject.getMessages().put("ERROR IN CONTENT", "No hemos encontrado el plato en el menu para modificarlo ");
				}
			}
		}else{
			resultObject.getMessages().put("ERROR IN CONTENT", "Lo sentimos pero solo un responsable del restaurant puede modificar un plato, posiblemente otro responsable le quito sus privilegios recientemente.");
		}
		return resultObject;
	}

	@Override
	public ResultObject reactivateFoodDishOfThisRestaurant(Long idDomainObject, Long idFoodDish, Long idResponsible) {
		ResultObject resultObject=new ResultObject();
		
		Restaurant resto = this.getRestaurantById(idDomainObject);
		Set<Configuration> configurations = resto.getConfiguration();
		resultObject.setResult(false);
		for (Configuration configuration : configurations) {
			if(configuration.getResponsible().getIdUser().equals(idResponsible)&& (!configuration.getResponsible().getLowLogic()) && (!configuration.getLowLogic())){
				resultObject.setResult(true);
			}
		}
		
		if (resultObject.getResult()){
			List<FoodDish> menu = resto.getMenu();

			resultObject.setResult(false);
			for (FoodDish foodDish : menu) {
				if(foodDish.getIdDomainObject().equals(idFoodDish)){
					foodDish.setLowlogic(false);
					resultObject.setResult(true);
				}
			}
			if (resultObject.getResult()){
				restaurantDAO.update(resto);
			}else{
				resultObject.getMessages().put("ERROR IN CONTENT", "No hemos encontrado el plato en el menu para reactivarlo ");
			}
			
		}else{
			resultObject.getMessages().put("ERROR IN CONTENT", "Lo sentimos pero solo un responsable del restaurant puede ractivar un plato, posiblemente otro responsable le quito sus privilegios recientemente.");
		}
		
		return resultObject;
	}

	@Override
	public ResultObject saveComentaryOfRestaurant(Commentary commentary, Long idRestaurant, String valoration,
			String email) {

		ResultObject resultObject=new ResultObject();
		User user =userService.GetUserWithThisMail(email);
		Restaurant restaurant=restaurantDAO.getEntity(idRestaurant);
		for (Configuration configuration: restaurant.getConfiguration()){
			System.out.println(configuration.getResponsible().getNotifications().size());
		}
		resultObject.setResult(restaurant.comment(user, commentary.getDescription(), Valoration.valueOf(valoration)));/////punto de control
		for (Configuration configuration: restaurant.getConfiguration()){
			System.out.println(configuration.getResponsible().getNotifications().size());
			userDAO.update3(configuration.getResponsible());
		}

		
		if(resultObject.getResult()){
			userDAO.update3(user);
			userService.clearInconsistenceRanking(user);
			this.recalculateCategoryRestaurantForCommentsOfThisUser(user);
		}else{
			resultObject.getMessages().put("ERROR IN CONTENT", "lo sentimos per no puede comentar restaurants a menos de un kilometro de algun restaurant del que sea o haya sido responsable");
		}
		
		return resultObject;

		
	}

	private void recalculateCategoryRestaurantForCommentsOfThisUser(User userWhoComment) {
		User user =userService.GetUserWithThisMail(userWhoComment.getEmail());
		
		Set<Long> restaurants=new HashSet<>();
		for (Commentary commentary: user.getCommentaries()) {
			if(!restaurants.contains(commentary.getContent().getIdRestaurant())){
				restaurants.add(commentary.getContent().getIdRestaurant());
				Restaurant rest =restaurantDAO.getEntity(commentary.getContent().getIdDomainObject());
				//RestaurantCategory aux=rest.getCategory();
				if(rest.calculateCategory()){
					restaurantDAO.update(rest);
					this.clearInconsistenceCategory(rest);
				}
			}
			

		}
	}

	public void clearInconsistenceCategory(Restaurant restaurant) {
		restaurantDAO.normalceInconsistenceCategory(restaurant);

	}
	
	
	private void recalculateCategoryRestaurant(Restaurant restaurant) {
		Restaurant rest =this.getRestaurantById(restaurant.getIdDomainObject());
		for (Commentary commentary: rest.getCommentaries()) {

		}
	}
	
	
	@Override
	public ResultObject deleteComentaryOfRestaurant(Long idCommentary, String mail, Long idRestaurant) {//PENDIENTE
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
			DomainObject restaurant=((Commentary)resultObject.getContent()).getContent();
		
			restaurantDAO.updateReal((Restaurant)restaurant);
			//userService.updateAndAreConsistence(user);
		}
		
				
		

		
		
		return resultObject;
	}

	@Override
	public ResultObject changeCommentaryOfRestaurant(Long idCommentaryOfRestaurant,
			String newDescriptionCommentaryOfRestaurant, String newValorationCommentaryOfRestaurant,String mail) {
		ResultObject resultObject=commentaryService.getCommentaryWithThisId(idCommentaryOfRestaurant);
		
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
			

			//commentaryDAO.update(commentary);
			
			
			((Commentary)resultObject.getContent()).setDescription(newDescriptionCommentaryOfRestaurant);
			((Commentary)resultObject.getContent()).setDate(LocalDateTime.now());
			((Commentary)resultObject.getContent()).setValoration(Valoration.valueOf(newValorationCommentaryOfRestaurant));
			//commentaryService.updateCommentary((Commentary)resultObject.getContent());
			User user=((Commentary)resultObject.getContent()).getUser();
			DomainObject restaurant=((Commentary)resultObject.getContent()).getContent();
		
			restaurantDAO.updateReal((Restaurant)restaurant);
			//userService.updateAndAreConsistence(user);
		}
		return resultObject;
		
		
		
		
	
	}

	@Override
	public ResultObject deactivateRestaurant(Restaurant restaurant, String mail) {
		// TODO Auto-generated method stub
		ResultObject resultObject=new ResultObject();
		Restaurant resto = this.getRestaurantById(restaurant.getIdDomainObject());
		
		if(true){//faltaria revisar que exista el restaurant..
			
			Set<Configuration> configurations = resto.getConfiguration();
			resultObject.setResult(false);
			for (Configuration configuration : configurations) {
				if(configuration.getResponsible().getEmail().equals(mail)&& (!configuration.getResponsible().getLowLogic()) && (!configuration.getLowLogic())){
					resultObject.setResult(true);
					
				}
			}
			
			if(resultObject.getResult()){//soy responsable y no estoy dado de baja
				resto.setLowlogic(true);
				restaurantDAO.update(resto);
			}else{//o no soy responsable o estoy dado de baja
				resultObject.getMessages().put("ERROR IN CONTENT", "Para deshabilitar un restaurant debe ser un responsable activo del mismo. posiblemente le quitaron sus privilegios recientemente");
			}
			
			
		}else{
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR IN CONTENT", "No encontramos el restaurant que desea eliminar");
		}
		
		

		
		
		return resultObject;
	}

	@Override
	public ResultObject reactivateRestaurant(Restaurant restaurant, String mail) {

		ResultObject resultObject=new ResultObject();
		Restaurant resto = this.getRestaurantById(restaurant.getIdDomainObject());
		
		if(true){//faltaria revisar que exista el restaurant..
			
			Set<Configuration> configurations = resto.getConfiguration();
			resultObject.setResult(false);
			for (Configuration configuration : configurations) {
				if(configuration.getResponsible().getEmail().equals(mail)&& (!configuration.getResponsible().getLowLogic()) && (!configuration.getLowLogic())){
					resultObject.setResult(true);
					
				}
			}
			
			if(resultObject.getResult()){//soy responsable y no estoy dado de baja
				resto.setLowlogic(false);
				restaurantDAO.update(resto);
			}else{//o no soy responsable o estoy dado de baja
				resultObject.getMessages().put("ERROR IN CONTENT", "Para rehabilitar un restaurant debe ser un responsable activo del mismo. posiblemente le quitaron sus privilegios recientemente");
			}
			
			
		}else{
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR IN CONTENT", "No encontramos el restaurant que desea reactivar");
		}

		return resultObject;
	}

	@Override
	public ResultObject changeInformationOfRestaurant(Restaurant restaurant, String addresToChange, String mail) {
		// TODO Auto-generated method stub
		
		ResultObject resultObject=new ResultObject();
		Restaurant resto = this.getRestaurantById(restaurant.getIdDomainObject());
		
		if(true){//falta controlar que existe
			resultObject.setResult(false);
			if(!restaurantDAO.existWithThisNameAndDirectionButNotId(restaurant.getName(),restaurant.getUbication().getDirection(),resto.getIdDomainObject())){//controlar si existe otro restaurant con ese nombre y direcc
				
				resultObject.setResult(true);
			}else{
				resultObject.setResult(false);
				resultObject.getMessages().put("ERROR IN CONTENT", "Ya existe un restaurant con el nombre y direccion que esta ingresando");
			}
			
			
			
			Set<Configuration> configurations = resto.getConfiguration();
			resultObject.setResult(false);
			for (Configuration configuration : configurations) {
				if(configuration.getResponsible().getEmail().equals(mail)&& (!configuration.getResponsible().getLowLogic()) && (!configuration.getLowLogic())){
					resultObject.setResult(true);
				}
			}
			
			
			if(resultObject.getResult()){//controlar que sea responsable
				resto.setName(restaurant.getName());
				resto.getUbication().setDirection(addresToChange);
				resto.getUbication().setLatitude(restaurant.getUbication().getLatitude());
				resto.getUbication().setLongitude(restaurant.getUbication().getLongitude());
				restaurantDAO.update(resto);
				
			}else{
				resultObject.getMessages().put("ERROR IN CONTENT", "Para modificar un restaurant debe ser un responsable activo del mismo. posiblemente le quitaron sus privilegios recientemente");
				
			}
			
			
		}else{
			resultObject.setResult(false);
			resultObject.getMessages().put("ERROR IN CONTENT", "No encontramos el restaurant que desea modificar");
			
		}
		
		

		
		return resultObject;
	}

	public List<Restaurant> getActiveRestaurants() {
		return restaurantDAO.getActiveRestaurants();
		
	}

	
}
