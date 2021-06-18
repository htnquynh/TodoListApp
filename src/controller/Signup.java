package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

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
		
		String email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
		request.setAttribute("email", email);
		String password =StringEscapeUtils.escapeHtml4(request.getParameter("password"));
		request.setAttribute("password", password);
		String fullname = StringEscapeUtils.escapeHtml4(request.getParameter("fullname"));
		request.setAttribute("fullname", fullname);
		
		boolean gender = Boolean.parseBoolean(StringEscapeUtils.escapeHtml4(request.getParameter("gender")));
		System.out.println("BBBB: " + gender);
		request.setAttribute("gender", gender);
		
		String birthdate_str = StringEscapeUtils.escapeHtml4(request.getParameter("birthdate"));
		request.setAttribute("birthdate_str", birthdate_str);
		
		
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
			Pattern pattern=Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
			if(pattern.matcher(password).matches()) {
				try {
					User newUser = new User(email, password, fullname, gender);
					
					if (!birthdate_str.equals("")) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date birthdate = df.parse(birthdate_str);
						newUser.setBirthdate(birthdate);
					}
					
					if(email.length()<256&&password.length()<256&&fullname.length()<256) {
						userDao.saveUser(newUser);
					}else {
						System.out.println("Khong them user nay vi so luong ki tu lon hon gioi han cho phep!!!");
					}
					Tag defaultTag = new Tag(0, "Other", "#cccccc", newUser);
					tagDao.saveTag(defaultTag);
					
					url="/login.jsp";
					
						
				} catch (Exception e) {
					url="/signup.jsp";
					request.setAttribute("emailError", "* Email registered");	
				}
			}else {
				url="/signup.jsp";
				request.setAttribute("passwordError", "Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters!");
			}
		}
			

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
