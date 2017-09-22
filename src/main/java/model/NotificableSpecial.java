package model;

import java.time.LocalDateTime;

/**
 * @class NotificableSpecial 
 * 
 * Used to represent NotificableSpecial
 *  
 * @author Grupo7
 */
public interface NotificableSpecial extends Notificable{

	abstract public LocalDateTime getDateSpecial(); 
	abstract String showReducedSpecial();
}
