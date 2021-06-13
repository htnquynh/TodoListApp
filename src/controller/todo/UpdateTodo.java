package controller.todo;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TagDao;
import dao.TodoDao;
import model.Tag;
import model.Todo;
import model.User;

@WebServlet("/updateTodo")
public class UpdateTodo extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	private TodoDao todoDao;
	private TagDao tagDao;
	HttpSession session = null;
	
    public UpdateTodo() {
        super();
		todoDao = new TodoDao();
		tagDao = new TagDao();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		try {
			updateTodo(request, response);
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
	
	private void updateTodo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException, ParseException {
		
		String from = request.getParameter("from");
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		String title = request.getParameter("title").trim();
		String priority = request.getParameter("priority").trim();
		
		Integer tagid = Integer.parseInt(request.getParameter("tagid").trim());

		Tag tag = tagDao.getTag(tagid);
		
		String date_str = request.getParameter("date");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse(date_str);
		
		Todo existingTodo = new Todo(id, title, priority, tag, date);
		
		
		if (title.equals("")) {
			request.setAttribute("titleError", "* You must enter title");
		}
		
		if (priority.equals("")) {
			request.setAttribute("priorityError", "* You must enter priority");
		}
		
		if (!title.equals("") && !priority.equals("")) {
			
			
			User user = (User) session.getAttribute("user");
			
			existingTodo.setUser(user);
			
			todoDao.updateTodo(existingTodo);
			
			
			if (from.equals("dashboard")) {
				response.sendRedirect("listDashboard");
			} else if (from.equals("tododay")) {
				response.sendRedirect("listTodo");
			} else if (from.equals("todoweek")) {
				response.sendRedirect("listTodoThisWeek");
			} else {
				response.sendRedirect("listTodoThisMonth");
			}
			
			
		} else {
			request.setAttribute("existingTodo", existingTodo);
			request.setAttribute("openFormEditTodo", "open");
			
			RequestDispatcher dispatcher;
			
			if (from.equals("dashboard")) {
				dispatcher = request.getRequestDispatcher("dashboard.jsp");
			} else if (from.equals("tododay")) {
				dispatcher = request.getRequestDispatcher("tododay.jsp");
			} else if (from.equals("todoweek")) {
				dispatcher = request.getRequestDispatcher("todoweek.jsp");
			} else {
				dispatcher = request.getRequestDispatcher("todomonth.jsp");
			}
			
			dispatcher.forward(request, response);
		}
	}

}
