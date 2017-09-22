package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.RankingUser;

public class RankingDao implements GenericDao<RankingUser> {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public RankingUser getEntity(Long id) {
		Session session = sessionFactory.openSession();
		RankingUser ranking = (RankingUser) session.load(RankingUser.class, id);
		session.close();
		return ranking;
	}
	
	@Transactional
	@Override
	public void update(RankingUser entity) {
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
		RankingUser ranking = (RankingUser) session.load(RankingUser.class, id);
		session.delete(ranking);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		RankingUser ranking = (RankingUser) session.load(RankingUser.class, id);
		session.close();
		if(ranking != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(RankingUser entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<RankingUser> list() {
		Session session = sessionFactory.openSession();
		List<RankingUser> rankingList = session.createQuery("from Ranking").list();
		session.close();
		return rankingList;
	}

}
