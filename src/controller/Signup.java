package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TagDao;
import dao.UserDao;
import model.Tag;
import model.User;

@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private TagDao tagDao;
	
    public Signup() {
        super();
        userDao = new UserDao();
        tagDao = new TagDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			signup(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void signup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url="";
		
		String email = request.getParameter("email");
		String password =request.getParameter("password");
		String fullname = request.getParameter("fullname");
		
		boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
		
		String birthdate_str = request.getParameter("birthdate");
		
		
		if (email.equals("") == true)
		{
			request.setAttribute("emailError", "* You must enter email");
			url="/signup.jsp";
		}
		
		if (fullname.equals("") == true)
		{
			request.setAttribute("fullnameError", "* You must enter name");
			url="/signup.jsp";
		}
		
		if (password.equals(""))
		{
			request.setAttribute("passwordError", "* You must enter password");
			url="/signup.jsp";
		}
		
				
		if (!email.equals("") && !fullname.equals("") && !password.equals("")) {
			try {
				User newUser = new User(email, password, fullname, gender);
				
				if (!birthdate_str.equals("")) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date birthdate = df.parse(birthdate_str);
					newUser.setBirthdate(birthdate);
				}
				
				//url="/signup-confirm.jsp";
				//request.getSession().setAttribute( "newuser", newUser);
                //String code = MailAPI.Send(email);
                //request.getSession().setAttribute("code",code);
					
				userDao.saveUser(newUser);
				Tag defaultTag = new Tag(0, "Other", "#cccccc", newUser);
				tagDao.saveTag(defaultTag);
				
				url="/login.jsp";
				
					
			} catch (Exception e) {
				url="/signup.jsp";
				request.setAttribute("emailError", "* Email registered");	
			}
		}
			

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
