package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import model.Configuration;

public class ConfigurationDao implements GenericDao<Configuration> {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public Configuration getEntity(Long id) {
		Session session = sessionFactory.openSession();
		Configuration configuration = (Configuration) session.load(Configuration.class, id);
		session.close();
		return configuration;
	}
	
	@Transactional
	@Override
	public void update(Configuration entity) {
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
		Configuration configuration = (Configuration) session.load(Configuration.class, id);
		session.delete(configuration);
		tx.commit();
		session.close();
		
	}
	
	@Transactional
	@Override
	public boolean exist(Long id) {
		Session session = sessionFactory.openSession();
		Configuration configuration = (Configuration) session.load(Configuration.class, id);
		session.close();
		if(configuration != null){
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public void persist(Configuration entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
	}
	
	@Override
	public List<Configuration> list() {
		Session session = sessionFactory.openSession();
		List<Configuration> configurationList = session.createQuery("from Configuration").list();
		session.close();
		return configurationList;
	}


}
