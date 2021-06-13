package controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;

@WebServlet("/resetPassword")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	
    public ResetPassword() {
        super();
        userDao = new UserDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			resetPassword(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "";
		String email = request.getParameter("email").trim();
		String password = request.getParameter("password").trim();
		
		Pattern emailPattern = Pattern.compile("\\w+@\\w+(.\\w+)*");
        Matcher emailMatcher = emailPattern.matcher(email);
        
        if (!emailMatcher.matches()) {
        	
        	request.setAttribute("email", email);
            request.setAttribute("emailError", "This is not an email address!");
            request.setAttribute("password", password);
            url="reset-password.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			
        } else if (password.equals("")){
        	request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("passError", "Please enter your new password!");
            url="reset-password.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
        } 
        else {
        	User user = userDao.existUser(email);
        	if (user != null) {
				/*
				 * userDao.resetPassword(email, password); RequestDispatcher dispatcher =
				 * request.getRequestDispatcher("login.jsp"); dispatcher.forward(request,
				 * response);
				 */
        		
        		//request.getSession().setAttribute( "email", email);
				//request.getSession().setAttribute( "password", password);
				
				url="/reset-password-success.jsp";
	            userDao.resetPassword(email, password);
	            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
	            dispatcher.forward(request, response);
    			
    		} else {
    			request.setAttribute("email", email);
                request.setAttribute("emailError", "User does not exist!");
                request.setAttribute("password", password);
                url="reset-password.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
    			dispatcher.forward(request, response);
    		}
        }
		
	}

}
