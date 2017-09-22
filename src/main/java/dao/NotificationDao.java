package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Notification;

public class NotificationDao implements GenericDao<Notification> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Notification getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Notification notification = (Notification) session.load(Notification.class, id);
		session.close();
		return notification;
	}
	
	@Transactional
	@Override
	public void update(Notification entity) {
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
		Notification notification = (Notification) session.load(Notification.class, id);
		session.delete(notification);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Notification notification = (Notification) session.load(Notification.class, id);
		session.close();
		if(notification != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Notification entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Notification> list() {
		Session session = sessionFactory.openSession();
		List<Notification> notificationList = session.createQuery("from Notification").list();
		session.close();
		return notificationList;
	}


}
