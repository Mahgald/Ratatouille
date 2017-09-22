package service;

import org.springframework.beans.factory.annotation.Autowired;

import dao.UbicationDao;

import model.Ubication;

public class UbicationService {
	
	
	@Autowired
	UbicationDao ubicationDAO;
	
	public Ubication getUbication(Long id) {
		return ubicationDAO.getEntity(id);
	}
}
