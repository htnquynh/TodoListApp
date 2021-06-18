package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UserDao userDao = null;
    
    public Login() {
        super();
        userDao = new UserDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			login(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/*
	 * private void login(HttpServletRequest request, HttpServletResponse response)
	 * throws Exception { String email = request.getParameter("email").trim();
	 * String password = request.getParameter("password").trim();
	 * 
	 * User user = userDao.login(email, password);
	 * 
	 * if (user != null) { session.setAttribute("user", user);
	 * response.sendRedirect("listTag");
	 * 
	 * } else { throw new Exception("Login not successful..."); } }
	 */
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "";
		String email = request.getParameter("email").trim();
		System.out.println("Email: " + email);
		String password = request.getParameter("password").trim();
		System.out.println("Password: " + password);

		if (email.equals("")== true)
		{
			request.setAttribute("emailError", "* You must enter email");
			url="/login.jsp";
		}
		else
		{
			User user = userDao.EmailExist(email);
			
			if (user != null) {
				if (user.getPassword().equals(password))
				{
					HttpSession session = request.getSession(false);
					session.setAttribute("user", user);
					
					/*
					 * List<Todo> listTodo = userDao.getTodosByUser(user.getId()); List<Tag> listTag
					 * = userDao.getTagsByUser(user.getId());
					 * 
					 * request.setAttribute("listTodo", listTodo); request.setAttribute("listTag",
					 * listTag);
					 */
					
					/* url="/todolist.jsp"; */
				}
				else
				{
					url="/login.jsp";
					request.setAttribute("passwordError", "* Password incorrect");
				}
				
			} else {
				url="/login.jsp";
				request.setAttribute("emailError", "* Email not registered");
			}	
		}
		
		if(password.equals(""))
		{
			request.setAttribute("passwordError", "* You must enter password");
			url="/login.jsp";
		}
		
		if (url.equals("")) {
			System.out.println("Chuyen qua listDashboard");
			response.sendRedirect("listDashboard");
			
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		
	}
	
	
}
