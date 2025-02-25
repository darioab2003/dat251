package no.hvl.dat251.controller;

import java.io.IOException;

import java.sql.Connection;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import no.hvl.dat251.dao.JDBCUserDAOImpl;
import no.hvl.dat251.dao.UserDAO;
import no.hvl.dat251.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		//TODO complete the code here

		//If there is not a session, the Login.jsp must be involved, otherwise, the orders must be listed by using the ListOrderServlet.do
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
		response.sendRedirect("paginaInicio.do");
		}
		else {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/index.jsp");
		view.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 Logger logger = Logger.getLogger(LoginServlet.class.getName());
		 logger.info("Handling POST request for login");
		
		 Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		 String email = request.getParameter("email");
	     String password = request.getParameter("password");
	        
	     UserDAO userDao = new JDBCUserDAOImpl();
	     userDao.setConnection(conn);
	     User user = userDao.getEmail(email);
	        
	     if (user != null && user.getPassword().equals(password)) {
	    	 logger.info("Login successful for user: " + email);
	         HttpSession session = request.getSession();
	         session.setAttribute("user", user);
	         session.setAttribute("loginMessage", "Correct log in");
	         response.sendRedirect("pages/main.html");
	     } else {
	    	 logger.warning("Login failed for user: " + email + password);
	    	 request.setAttribute("loginMessage", "User or password incorrect");
		     RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/index.jsp");
		     view.forward(request,response);
	        }
	}

}
