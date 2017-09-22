package model;

import java.time.LocalDateTime;

public interface Notificable {
	
	abstract Notificable goToView();

	abstract String showReduced();

	abstract public LocalDateTime getDate(); 


}
