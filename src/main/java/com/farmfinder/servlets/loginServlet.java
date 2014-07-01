package com.farmfinder.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.farmfinder.commands.CategoryRepo;
import com.farmfinder.commands.FarmRepo;
import com.farmfinder.config.MongoConfig;
import com.farmfinder.model.Farm;

@WebServlet(name="LoginServlet", urlPatterns="/login")
public class loginServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username") ;
		String password = request.getParameter ("password") ;
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		FarmRepo repo = (FarmRepo) ctx.getBean(FarmRepo.class) ;
		System.out.println(username + password) ;
		try{
			request.login(username,password) ; 
			Farm farm = repo.findByEmail(username) ;
 			response.setStatus(200) ;
			HttpSession session = request.getSession();
			//session.setAttribute("farm_id", farm.getId());
			Cookie userCookie = new Cookie("farm_id", farm.getId()) ;
			response.addCookie(userCookie) ;
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home.html")) ;;
		}		  
		catch(Exception e){
			e.printStackTrace();
			try {
				response.sendError(500, "Unable to log user in");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
}
