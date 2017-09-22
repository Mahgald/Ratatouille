package service;

import org.springframework.beans.factory.annotation.Autowired;

import dao.CommentaryDao;
import dao.FoodDishDao;
import helpers.ResultObject;
import model.Commentary;

public class CommentaryService implements ICommentaryService {

	@Autowired
	CommentaryDao commentaryDAO;
	
	public ResultObject getCommentaryWithThisId(Long idCommentary) {
		ResultObject result=new ResultObject();
		if(commentaryDAO.exist(idCommentary)){
			result.setContent(commentaryDAO.getEntity(idCommentary));
			result.setResult(true);
		}else{
			result.setResult(false);
			result.getMessages().put("ERROR IN CONTENT", "lo sentimos pero no encontramos el comentario que trato de eliminar");
		}
		return result;
	}

	public void updateCommentary(Commentary commentary) {
		commentaryDAO.update(commentary);
		
	}

}
