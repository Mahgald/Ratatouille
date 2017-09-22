package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Gourmet;

public class GourmetDao implements GenericDao<Gourmet> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Gourmet getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Gourmet gourmet = (Gourmet) session.load(Gourmet.class, id);
		session.close();
		return gourmet;
	}
	
	@Transactional
	@Override
	public void update(Gourmet entity) {
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
		Gourmet gourmet = (Gourmet) session.load(Gourmet.class, id);
		session.delete(gourmet);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Gourmet gourmet = (Gourmet) session.load(Gourmet.class, id);
		session.close();
		if(gourmet != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Gourmet entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Gourmet> list() {
		Session session = sessionFactory.openSession();
		List<Gourmet> gourmetList = session.createQuery("from Gourmet").list();
		session.close();
		return gourmetList;
	}


}
