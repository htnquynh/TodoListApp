package controller.todo;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TodoDao;
import model.Todo;

@WebServlet("/editTodo")
public class EditTodo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TodoDao todoDao;
	
	
    public EditTodo() {
        super();
		todoDao = new TodoDao();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			showEditTodoForm(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	private void showEditTodoForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		String from = request.getParameter("from").trim();
		
		System.out.println("From: ");
		System.out.println(from);
		
		/* String type = request.getParameter("type"); */
		
		Todo existingTodo = todoDao.getTodo(id);
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
