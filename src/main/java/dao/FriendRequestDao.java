package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.FriendRequest;

public class FriendRequestDao implements GenericDao<FriendRequest> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public FriendRequest getEntity(Long id) {
		Session session = sessionFactory.openSession();
		FriendRequest friendRequest = (FriendRequest) session.load(FriendRequest.class, id);
		session.close();
		return friendRequest;
	}
	
	@Transactional
	@Override
	public void update(FriendRequest entity) {
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
		FriendRequest friendRequest = (FriendRequest) session.load(FriendRequest.class, id);
		session.delete(friendRequest);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		FriendRequest friendRequest = (FriendRequest) session.load(FriendRequest.class, id);
		session.close();
		if(friendRequest != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(FriendRequest entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<FriendRequest> list() {
		Session session = sessionFactory.openSession();
		List<FriendRequest> friendRequestList = session.createQuery("from FriendRequest").list();
		session.close();
		return friendRequestList;
	}


}
