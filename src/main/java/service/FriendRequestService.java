package service;

import org.springframework.beans.factory.annotation.Autowired;

import dao.FriendRequestDao;
import helpers.ResultObject;
import model.FriendRequest;
import model.User;

public class FriendRequestService implements IFriendRequestService{

	
	@Autowired
	FriendRequestDao friendRequestDAO;

	public ResultObject acceptFriendRequest(Long idOfFriendRequest, User user) {
		ResultObject result=new ResultObject();
		FriendRequest friendRequest = friendRequestDAO.getEntity(idOfFriendRequest);
		if (friendRequest.confirm(user)){
			friendRequestDAO.update(friendRequest);
			result.setResult(true);
		}else{
			result.setResult(false);
			result.getMessages().put("ERROR IN CONTENT", "La solicitud que trata de aceptar no le fue enviada a usted");
		}
		return result;
	}

	public ResultObject rejectFriendRequest(Long idOfFriendRequest, User user) {
		ResultObject result=new ResultObject();
		FriendRequest friendRequest = friendRequestDAO.getEntity(idOfFriendRequest);
		if(friendRequest.reject(user)){
			friendRequestDAO.update(friendRequest);
			result.setResult(true);
		}else{
			result.setResult(false);
			result.getMessages().put("ERROR IN CONTENT", "La solicitud que trata de rechazar no le fue enviada a usted");
		}
		return result;
	}
	
	
	
	
	
	
	
}
