package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.RestaurantCategory;

public class RestaurantCategoryDao implements GenericDao<RestaurantCategory> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public RestaurantCategory getEntity(Long id) {
		Session session = sessionFactory.openSession();
		RestaurantCategory restaurantCategory = (RestaurantCategory) session.load(RestaurantCategory.class, id);
		session.close();
		return restaurantCategory;
	}
	
	@Transactional
	@Override
	public void update(RestaurantCategory entity) {
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
		RestaurantCategory restaurantCategory = (RestaurantCategory) session.load(RestaurantCategory.class, id);
		session.delete(restaurantCategory);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		RestaurantCategory restaurantCategory = (RestaurantCategory) session.load(RestaurantCategory.class, id);
		session.close();
		if(restaurantCategory != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(RestaurantCategory entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<RestaurantCategory> list() {
		Session session = sessionFactory.openSession();
		List<RestaurantCategory> restaurantCategoryList = session.createQuery("from RestaurantCategory").list();
		session.close();
		return restaurantCategoryList;
	}


}
