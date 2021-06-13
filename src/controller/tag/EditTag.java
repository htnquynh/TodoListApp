package controller.tag;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TagDao;
import model.Tag;

@WebServlet("/editTag")
public class EditTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TagDao tagDao = null;
    HttpSession session = null;
    
    public EditTag() {
        super();
        tagDao = new TagDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			showEditTagForm(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showEditTagForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String from = request.getParameter("from");
		
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		Tag existingTag = tagDao.getTag(id);
		
		request.setAttribute("existingTag", existingTag);
		request.setAttribute("openFormEditTag", "open");
		
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
