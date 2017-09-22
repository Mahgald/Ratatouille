package dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Responsible;
import model.Restaurant;

public class ResponsibleDao implements GenericDao<Responsible> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Responsible getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Responsible responsilbe = (Responsible) session.load(Responsible.class, id);
		session.close();
		return responsilbe;
	}
	
	@Transactional
	@Override
	public void update(Responsible entity) {
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
		Responsible responsible = (Responsible) session.load(Responsible.class, id);
		session.delete(responsible);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Responsible responsible = (Responsible) session.load(Responsible.class, id);
		session.close();
		if(responsible != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Responsible entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Responsible> list() {
		Session session = sessionFactory.openSession();
		List<Responsible> responsibleList = session.createQuery("from Responsible").list();
		session.close();
		return responsibleList;
	}

	public boolean existWithThisMail(String mail) {
		
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT r FROM Responsible as r WHERE r.email=:mail");
		query.setParameter("mail", mail);
	    List<Responsible> responsibles =   query.getResultList();
		session.close();
		if(responsibles.isEmpty()){
			return false;
		}
		return true;
	}

	public Responsible getEntityByMail(String mail) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT r FROM Responsible as r WHERE r.email='"+mail +"'");
	    Responsible responsible =   (Responsible) query.getSingleResult();
		session.close();
		return responsible;
	}


}
