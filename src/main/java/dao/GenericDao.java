package dao;

import java.util.List;

import model.FoodDish;
import model.Restaurant;

public interface GenericDao<T> {
	
	public T getEntity(Long id);
	public void update(T entity);
	public void delete(Long id);
	public boolean exist(Long id);
	public void persist(T entity);
	public List<T> list();
	boolean existWithThisName(String name);
	Restaurant persistSecond(Restaurant entity);
	

	
	
	
}
