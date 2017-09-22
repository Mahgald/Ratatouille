package model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @class DomainEventSpecial 
 * 
 * Used to represent the friend request as a different kind of notification
 *
 * @author Grupo7
 */
@Entity
@Table(name="DomainEventSpecial")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DomainEventSpecial extends DomainEvent{

}
