package dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.DomainObject;
import model.RankingUser;
import model.Restaurant;
import model.RestaurantCategory;
import model.User;



public class RestaurantDao implements GenericDao<Restaurant> {
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean existWithThisName(String name) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Transactional
	@Override
	public Restaurant getEntity(Long id) {
		Session session = sessionFactory.openSession();
	
		Restaurant restaurant = (Restaurant) session.load(Restaurant.class, id);
		System.out.println(restaurant.getName());
		session.close();
		System.out.println(restaurant.getName());
		System.out.println("---------------------------------------------------------------------------------------------");
		return restaurant;
	}
	
	@Transactional
	@Override
	public void update(Restaurant entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
		session.close();
	}
	@Transactional
	
	public void updateReal(Restaurant entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		session.close();
	}
	@Transactional
	@Override
	public void delete(Long id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Restaurant restaurant = (Restaurant) session.load(Restaurant.class, id);
		session.delete(restaurant);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT r FROM Restaurant as r WHERE r.idDomainObject=:idDomainObject ");
		query.setParameter("idDomainObject", id);
	    List<Restaurant> restaurants =   query.getResultList();
		session.close();
		if(restaurants.isEmpty()){
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public void persist(Restaurant entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Transactional
	@Override
	public Restaurant persistSecond(Restaurant entity) {
		Session session = sessionFactory.openSession();
		try {
			
			Transaction tx = session.beginTransaction();
			session.save(entity);
			tx.commit();
		}catch (RuntimeException e) {
			entity=null;
		}finally{
			session.close();
		}
		
		return entity;
		
		
	}
	
	@Override
	public List<Restaurant> list() {
		Session session = sessionFactory.openSession();
		List<Restaurant> restaurantList = session.createQuery("from Restaurant").list();
		session.close();
		return restaurantList;
	}

	@Transactional
	
	public boolean existWithThisNameAndDirection(String name,String direction ){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT r FROM Restaurant as r INNER JOIN r.ubication as u WHERE r.name=:name AND u.direction=:direction");
		query.setParameter("name", name);
		query.setParameter("direction", direction);
	    List<Restaurant> restaurants =   query.getResultList();
		session.close();
		if(restaurants.isEmpty()){
			return false;
		}
		return true;
	}
	public boolean existWithThisNameAndDirectionButNotId(String name,String direction,Long id ){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT r FROM Restaurant as r INNER JOIN r.ubication as u WHERE r.name=:name AND u.direction=:direction AND u.idDomainObject!=:id");
		query.setParameter("name", name);
		query.setParameter("direction", direction);
		query.setParameter("id", id);
	    List<Restaurant> restaurants =   query.getResultList();
		session.close();
		if(restaurants.isEmpty()){
			return false;
		}
		return true;
	}
	

	public List<Restaurant> listOfRestaurantInProximity(String partOfName, int distance, double latitude,double longitude) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Restaurant as r WHERE (r.name LIKE :words ) AND r.lowlogic = false");
		query.setParameter("words", "%"+partOfName+"%");
		List<Restaurant> restaurants = query.getResultList();
		session.close();
		return restaurants;
	}


	public List<DomainObject> listOfRestaurantsMatchWhitThisWords(String partOfName) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Restaurant as r WHERE (r.name LIKE :words ) AND r.lowlogic = false");
		query.setParameter("words", "%"+partOfName+"%");
		List<DomainObject> restaurantsAndFoodDish = query.getResultList();
		session.close();
		return restaurantsAndFoodDish;

		
	}

	public List<Restaurant> getActiveRestaurants() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Restaurant as r WHERE r.lowlogic = false");
		
		List<Restaurant> activeRestaurants = query.getResultList();
		session.close();
		return activeRestaurants;
	}
	public void normalceInconsistenceCategory(Restaurant restaurant) {
		
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT r FROM RestaurantCategory as r WHERE r.restaurant.idDomainObject=:idRestaurant ORDER BY r.idCategory ");
			query.setParameter("idRestaurant", restaurant.getIdDomainObject());
		    List<RestaurantCategory> categorys =   query.getResultList();
			session.close();
			if(categorys.size()>1){
				

					session = sessionFactory.openSession();
					Transaction tx = session.beginTransaction();
					query = session.createQuery("DELETE FROM RestaurantCategory as r WHERE r.idCategory= :id ");
					query.setParameter("id", categorys.get(0).getIdCategory());
					query.executeUpdate();
					tx.commit();
					session.close();
				
			
				
				
				/*session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				RankingUser ranking = (RankingUser) session.load(RankingUser.class, rankings.get(0).getIdRanking());
				session.delete(ranking);
				tx.commit();
				session.close();*/
				
			}
			
		
	}

}
