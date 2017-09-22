package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Ubication;
import model.User;


@Repository
public class UbicationDao implements GenericDao<Ubication>{

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Ubication getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Ubication ubication = (Ubication) session.load(Ubication.class, id);
		session.close();
		return ubication;
	}

	@Transactional
	@Override
	public void update(Ubication entity) {
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
		Ubication ubication = (Ubication) session.load(Ubication.class, id);
		session.delete(ubication);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Ubication ubication = (Ubication) session.load(Ubication.class, id);
		session.close();
		if(ubication != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Ubication entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Ubication> list() {
		Session session = sessionFactory.openSession();
		List<Ubication> ubicationList = session.createQuery("from Ubication").list();
		session.close();
		return ubicationList;
	}
}
