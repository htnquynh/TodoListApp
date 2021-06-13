package controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;

@WebServlet("/updateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao userDao = null;
	HttpSession session = null;
       
    public UpdateUser() {
        super();
        userDao = new UserDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		session = request.getSession(true);
		try {
			updateUser(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException, ParseException {
		
		String email = request.getParameter("email").trim();
		String password = request.getParameter("password").trim();
		String fullname = request.getParameter("fullname").trim();
		String gender = request.getParameter("gender");
		
		String birthdate_str = request.getParameter("birthdate");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate = df.parse(birthdate_str);
		
        Pattern emailPattern = Pattern.compile("\\w+@\\w+(.\\w+)*");
        Matcher emailMatcher = emailPattern.matcher(email);
        if (!emailMatcher.matches()) {
        	request.setAttribute("email", email);
            request.setAttribute("emailError", " is not an email address!");
        } else {
        	User user = (User) session.getAttribute("user");
    		user.setEmail(email);
    		user.setPassword(password);
    		user.setFullname(fullname);
    		
    		
    		
    		if(gender.equals("male")) {
    			user.setGender(false);	// false = 0 --> Male
    		} else {
    			user.setGender(true);	// true = 1 --> Female
    		}
    		
    		user.setBirthdate(birthdate);
    		userDao.updateUser(user);
    		session.setAttribute("user", userDao.getUser(user.getId()));
        }
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
		dispatcher.forward(request, response);
	}

}
