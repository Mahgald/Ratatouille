package dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.DomainObject;
import model.FoodDish;
import model.Restaurant;
import model.User;

public class FoodDishDao implements GenericDao<FoodDish> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public FoodDish getEntity(Long id) {
		System.out.println("1vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		Session session = sessionFactory.openSession();
		System.out.println("2vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		FoodDish foodDish = (FoodDish) session.load(FoodDish.class, id);
		System.out.println("3vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		session.close();
		System.out.println("4vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		return foodDish;
	}
	
	@Transactional
	@Override
	public void update(FoodDish entity) {
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
		FoodDish foodDish = (FoodDish) session.load(FoodDish.class, id);
		session.delete(foodDish);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		FoodDish foodDish = (FoodDish) session.load(FoodDish.class, id);
		session.close();
		if(foodDish != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(FoodDish entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Transactional

	public FoodDish persistSecond(FoodDish entity) {
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
	public List<FoodDish> list() {
		Session session = sessionFactory.openSession();
		List<FoodDish> foodDishList = session.createQuery("from FoodDish").list();
		session.close();
		return foodDishList;
	}

	
	
	@Override
	public boolean existWithThisName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Restaurant persistSecond(Restaurant entity) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<DomainObject> listOfFoodDishMatchWhitThisWords(String partOfName) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM FoodDish as f WHERE (f.name LIKE :words OR f.description LIKE :words) AND f.lowlogic = false");
		query.setParameter("words", "%"+partOfName+"%");
		List<DomainObject> restaurantsAndFoodDish = query.getResultList();
		session.close();
		return restaurantsAndFoodDish;
	}
	
	@Transactional
	public void update3(FoodDish entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
		session.close();
		}

	public Restaurant getRestaurantContainThisFoodDish(Long idFoodDish) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT f.restaurant FROM FoodDish as f WHERE f.idDomainObject=:idFoodDish");
		query.setParameter("idFoodDish", idFoodDish);
	    Restaurant restaurant =   (Restaurant) query.getSingleResult();
		session.close();
		return restaurant;

	}

}
