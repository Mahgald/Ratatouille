package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * @class FilterByCategorieUser 
 * 
 * Used to represent filter by categorie user
 * 
 * @author Grupo7
 */
@Entity
@Table(name="FilterByCategorieUser")
public class FilterByCategorieUser extends FilterAbstract{
	/*
	private Long idFilter;
	*/
		
	//@OneToMany(cascade=CascadeType.ALL,mappedBy="filter")
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Boolean> combination=new ArrayList<Boolean>();
	
	FilterByCategorieUser(){
		
	}
	
	FilterByCategorieUser(Boolean visitor,Boolean comensal,Boolean gourmet){
		this.getCombination().add(visitor);
		this.getCombination().add(comensal);
		this.getCombination().add(gourmet);
	}
	
	FilterByCategorieUser(ArrayList<Boolean> combination){
		this.setCombination(combination);
	}
	
	@Override
	public Boolean filter(Commentary coment) {
		Stream<Boolean> stream1=Stream.iterate(0,i->i+1).map(x->this.getCombination().stream().reduce(true, (a,b)->a&&b));
		Stream<Boolean> stream3 = Stream.concat(getCombination().stream(),stream1);
		return coment.getUser().interesting(stream3);
		//return coment.getUser().interesting(Stream.concat(getCombination().stream(),Stream.iterate(0,i->i+1).map(x->this.getCombination().stream().reduce(true, (a,b)->a&&b))));
				
	}
	
	
	public List<Boolean> getCombination() {
		return combination;
	}

	public void setCombination(List<Boolean> combinatio) {
		this.combination = combinatio;
	}

	public Long getIdFilter() {
		return idFilter;
	}

	public void setIdFilter(Long idFilter) {
		this.idFilter = idFilter;
	}
}