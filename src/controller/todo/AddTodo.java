package controller.todo;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addTodo")
public class AddTodo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

    public AddTodo() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showNewTodoForm(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void showNewTodoForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("openForm", "open");
		String type=request.getParameter("type");
		if(type.equals("date"))
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("todos/index.jsp");
			dispatcher.forward(request, response);
		}
		else if(type.equals("week"))
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("todos/week.jsp");
			dispatcher.forward(request, response);
		}
		else if(type.equals("month"))
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("todos/month.jsp");
			dispatcher.forward(request, response);
		}
	}

}
