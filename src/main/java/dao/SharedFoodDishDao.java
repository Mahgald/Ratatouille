package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.SharedFoodDish;

public class SharedFoodDishDao implements GenericDao<SharedFoodDish> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public SharedFoodDish getEntity(Long id) {
		Session session = sessionFactory.openSession();
		SharedFoodDish sharedFoodDish = (SharedFoodDish) session.load(SharedFoodDish.class, id);
		session.close();
		return sharedFoodDish;
	}
	
	@Transactional
	@Override
	public void update(SharedFoodDish entity) {
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
		SharedFoodDish sharedFoodDish = (SharedFoodDish) session.load(SharedFoodDish.class, id);
		session.delete(sharedFoodDish);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		SharedFoodDish sharedFoodDish = (SharedFoodDish) session.load(SharedFoodDish.class, id);
		session.close();
		if(sharedFoodDish != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(SharedFoodDish entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<SharedFoodDish> list() {
		Session session = sessionFactory.openSession();
		List<SharedFoodDish> sharedFoodDishList = session.createQuery("from SharedFoodDish").list();
		session.close();
		return sharedFoodDishList;
	}


}
