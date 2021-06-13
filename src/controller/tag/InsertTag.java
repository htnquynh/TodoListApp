package controller.tag;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TagDao;
import model.Tag;
import model.User;

@WebServlet("/insertTag")
public class InsertTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TagDao tagDao;
	HttpSession session = null;
	
	
    public InsertTag() {
        super();
        tagDao = new TagDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		try {
			insertTag(request, response);
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
		doGet(request, response);
	}
	
	public void insertTag(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException, ParseException {
		
		String from = request.getParameter("from");
		
		String tagname = request.getParameter("tagname");
		String color = request.getParameter("color");
		
		User user = (User)session.getAttribute("user");
		
		Tag newTag = new Tag(tagname, color, user);
		tagDao.saveTag(newTag);
		
		/* response.sendRedirect("listTag"); */
		
		/* request.setAttribute("openFormAddTag", "open"); */
		
		if (from.equals("dashboard")) {
			response.sendRedirect("listDashboard");
		} else if (from.equals("tododay")) {
			response.sendRedirect("listTodo");
		} else if (from.equals("todoweek")) {
			response.sendRedirect("listTodoThisWeek");
		} else {
			response.sendRedirect("listTodoThisMonth");
		}
		
	}

}
