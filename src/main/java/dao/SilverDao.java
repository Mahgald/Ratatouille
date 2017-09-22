package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Silver;

public class SilverDao implements GenericDao<Silver> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Silver getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Silver silver = (Silver) session.load(Silver.class, id);
		session.close();
		return silver;
	}
	
	@Transactional
	@Override
	public void update(Silver entity) {
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
		Silver silver = (Silver) session.load(Silver.class, id);
		session.delete(silver);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Silver silver = (Silver) session.load(Silver.class, id);
		session.close();
		if(silver != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Silver entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Silver> list() {
		Session session = sessionFactory.openSession();
		List<Silver> silverList = session.createQuery("from Silver").list();
		session.close();
		return silverList;
	}


}
