package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Commensal;

public class CommensalDao implements GenericDao<Commensal> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Commensal getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Commensal commensal = (Commensal) session.load(Commensal.class, id);
		session.close();
		return commensal;
	}
	
	@Transactional
	@Override
	public void update(Commensal entity) {
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
		Commensal commensal = (Commensal) session.load(Commensal.class, id);
		session.delete(commensal);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Commensal commensal = (Commensal) session.load(Commensal.class, id);
		session.close();
		if(commensal != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Commensal entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Commensal> list() {
		Session session = sessionFactory.openSession();
		List<Commensal> commensalList = session.createQuery("from Commensal").list();
		session.close();
		return commensalList;
	}


}
