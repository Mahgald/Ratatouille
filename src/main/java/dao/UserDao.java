package dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.RankingUser;
import model.Responsible;
import model.Restaurant;
import model.User;

public class UserDao implements GenericDao<User> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public User getEntity(Long id) {
		Session session = sessionFactory.openSession();
		User user = (User) session.load(User.class, id);
		session.close();
		return user;
	}
	
	
	@Override
	public void update(User entity) {
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
		User user = (User) session.load(User.class, id);
		session.delete(user);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		User user = (User) session.load(User.class, id);
		session.close();
		if(user != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(User entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<User> list() {
		Session session = sessionFactory.openSession();
		List<User> userList = session.createQuery("from User").getResultList();
		session.close();
		return userList;
	}
	
	
	
	public List<User> listOfUserMatchWhitThisWords(String words) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM User as u WHERE (u.name LIKE :words OR u.lastName LIKE :words ) AND u.lowLogic = false");
		query.setParameter("words", "%"+words+"%");
		List<User> users = query.getResultList();
		session.close();
		return users;
	}
	
	
	
	public boolean existWithThisMail(String mail) {
		
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT r FROM User as r WHERE r.email=:mail");
		query.setParameter("mail", mail);
	    List<User> users = query.getResultList();
		session.close();
		if(users.isEmpty()){
			return false;
		}
		return true;
	}
	
	public User getEntityByMail(String mail) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT u FROM User as u WHERE u.email=:mail");
		query.setParameter("mail", mail);
	    User user =   (User) query.getSingleResult();
		session.close();
		return user;
	}

	public boolean isAccessData(User user) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT u FROM User as u WHERE u.email=:mail AND u.password=:password");
		query.setParameter("mail", user.getEmail());
		query.setParameter("password", user.getPassword());
	    List<User> users =   query.getResultList();
		session.close();
		if(users.isEmpty()){
			return false;
		}
		return true;
	}

	@Override
	public boolean existWithThisName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<User> getGroupOfUserWithThisIds(Long[] friendsToShareFoodDish) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT u FROM User as u WHERE u.idUser in :friendsToShare");//in (1,2,3)
		((org.hibernate.query.Query) query).setParameterList("friendsToShare", friendsToShareFoodDish);
		//query.setParameter("password", user.getPassword());
		List<User> listita=query.getResultList();
		for(User user:listita){
			System.out.println(user.getName());
		}
		System.out.println("por dios2");
		Set<User> users = new HashSet<User>(listita);

		session.close();
		return users;
	}
	@Transactional
	
	public void update2(User entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("UPDATE  User u set u=:user WHERE u.idUser = :idUser");
		query.setParameter("user", entity);
		query.setParameter("idUser", entity.getId());
		int result = query.executeUpdate();
		//session.update(entity);
		tx.commit();

		session.close();
	}
	@Transactional
	
	public void update3(User entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.merge(entity);
		tx.commit();

		session.close();
	}


	public void normalceInconsistenceRanking(User user) {
		
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("SELECT r FROM RankingUser as r WHERE r.user.idUser=:idUser ORDER BY r.idRanking ");
			query.setParameter("idUser", user.getId());
		    List<RankingUser> rankings =   query.getResultList();
			session.close();
			if(rankings.size()>1){
				

					session = sessionFactory.openSession();
					Transaction tx = session.beginTransaction();
					query = session.createQuery("DELETE FROM RankingUser as r WHERE r.idRanking= :id ");
					query.setParameter("id", rankings.get(0).getIdRanking());
					query.executeUpdate();
					tx.commit();
					session.close();
				
			
				
				
				/*session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				RankingUser ranking = (RankingUser) session.load(RankingUser.class, rankings.get(0).getIdRanking());
				session.delete(ranking);
				tx.commit();
				session.close();*/
				
			}
			
		
	}


	public List<User> getActiveUsers() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM User as u WHERE u.lowLogic = false");
		
		List<User> activeUsers = query.getResultList();
		session.close();
		return activeUsers;
		
	}



	
	
}
