package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Platinum;

public class PlatinumDao implements GenericDao<Platinum> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Platinum getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Platinum platinum = (Platinum) session.load(Platinum.class, id);
		session.close();
		return platinum;
	}
	
	@Transactional
	@Override
	public void update(Platinum entity) {
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
		Platinum platinum = (Platinum) session.load(Platinum.class, id);
		session.delete(platinum);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Platinum platinum = (Platinum) session.load(Platinum.class, id);
		session.close();
		if(platinum != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Platinum entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Platinum> list() {
		Session session = sessionFactory.openSession();
		List<Platinum> platinumList = session.createQuery("from Platinum").list();
		session.close();
		return platinumList;
	}


}
