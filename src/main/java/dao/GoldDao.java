package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Gold;

public class GoldDao implements GenericDao<Gold> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Gold getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Gold gold = (Gold) session.load(Gold.class, id);
		session.close();
		return gold;
	}
	
	@Transactional
	@Override
	public void update(Gold entity) {
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
		Gold gold = (Gold) session.load(Gold.class, id);
		session.delete(gold);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Gold gold = (Gold) session.load(Gold.class, id);
		session.close();
		if(gold != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Gold entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Gold> list() {
		Session session = sessionFactory.openSession();
		List<Gold> goldList = session.createQuery("from Gold").list();
		session.close();
		return goldList;
	}


}
