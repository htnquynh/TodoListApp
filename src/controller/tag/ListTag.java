package controller.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.Tag;
import model.User;

@WebServlet("/listTag")
public class ListTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao userDao = null;
	HttpSession session = null;

	public ListTag() {
		super();
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession(true);
		listTag(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void listTag(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User user = (User) session.getAttribute("user");
		
		List<Tag> listTag = userDao.getTagsByUser(user.getId());
		
		/* request.setAttribute("listTag", listTag); */
		
		session.setAttribute("listTag", listTag);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("tags/index.jsp");
		dispatcher.forward(request, response);
	}

}
