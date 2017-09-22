package dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Commentary;
import model.FoodDish;
import model.Restaurant;
import model.User;

public class CommentaryDao implements GenericDao<Commentary> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Commentary getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Commentary commentary = (Commentary) session.load(Commentary.class, id);
		session.close();
		return commentary;
	}
	
	@Transactional
	@Override
	public void update(Commentary entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		session.close();
	}
	
	@Transactional
	public void delete(Long id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("DELETE FROM Commentary as c WHERE c.id= :id ");
		query.setParameter("id", id);
		query.executeUpdate();
		tx.commit();
		session.close();
	
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT c FROM Commentary as c WHERE c.idDomainEvent=:idDomainEvent");
		query.setParameter("idDomainEvent", id);
	    List<Commentary> users = query.getResultList();
		session.close();
		if(users.isEmpty()){
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public void persist(Commentary entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Commentary> list() {
		Session session = sessionFactory.openSession();
		List<Commentary> commentaryList = session.createQuery("from Commentary").list();
		session.close();
		return commentaryList;
	}

	public void delete2(Long id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Commentary commentary = (Commentary) session.load(Commentary.class, id);
		session.delete(commentary);
		tx.commit();
		session.close();
		
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

	@Transactional

	public void update3(Commentary entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();
		session.close();
	}
}
