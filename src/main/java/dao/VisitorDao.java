package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Visitor;

public class VisitorDao implements GenericDao<Visitor> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Visitor getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Visitor visitor = (Visitor) session.load(Visitor.class, id);
		session.close();
		return visitor;
	}
	
	@Transactional
	@Override
	public void update(Visitor entity) {
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
		Visitor visitor = (Visitor) session.load(Visitor.class, id);
		session.delete(visitor);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Visitor visitor = (Visitor) session.load(Visitor.class, id);
		session.close();
		if(visitor != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Visitor entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Visitor> list() {
		Session session = sessionFactory.openSession();
		List<Visitor> visitorList = session.createQuery("from Visitor").list();
		session.close();
		return visitorList;
	}


}
