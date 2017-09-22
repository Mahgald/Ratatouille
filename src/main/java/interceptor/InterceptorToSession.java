package interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import model.User;

public class InterceptorToSession implements HandlerInterceptor  {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session=request.getSession(true);
		  if(session.getAttribute("typeUserLoged")==null){
			   session.setAttribute("typeUserLoged", 0);
			  }
		System.out.println("Pre-handle");
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("entre");
		HttpSession session = request.getSession(false);
		User us =new User();
		System.out.println(session.getAttribute("typeUserLoged"));
		if(modelAndView==null){
			modelAndView = new ModelAndView();
		}
		if(session.getAttribute("typeUserLoged").equals(0)){
			System.out.println("Post-handle hola");
			modelAndView.addObject("UserToLog", us);
			System.out.println(modelAndView.getViewName());
		}
		System.out.println("Post-handle");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("After completion handle");
	}
}
