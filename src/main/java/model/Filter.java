package model;

import javax.persistence.MappedSuperclass;

/**
 * @class Filter 
 * 
 * Used to represents filter interface
 * 
 * @author Grupo7
 */
@MappedSuperclass
public interface Filter {
	
	public Long getIdFilter();
	public void setIdFilter(Long idFilter);
	abstract Boolean filter(Commentary coment);
}
