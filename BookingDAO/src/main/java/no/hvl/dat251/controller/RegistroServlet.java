package no.hvl.dat251.controller;

import java.io.IOException;

import java.sql.Connection;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

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
@WebServlet("/RegistroServlet.do")
public class RegistroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = 
			Logger.getLogger(HttpServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistroServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Handling GET");

        HttpSession session = request.getSession();
        if (session.getAttribute("order") != null) {
            logger.info("Removing existing 'order' attribute from session");
            session.removeAttribute("order");
        }

        logger.info("Session id: " + session.getId());
        logger.info("Session new? " + session.isNew());

        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/registro.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        logger.info("Handling POST");

        Connection conn = (Connection) getServletContext().getAttribute("dbConn");
        if (conn == null) {
            logger.severe("Database connection is null");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection is not available");
            return;
        }

        UserDAO userDao = new JDBCUserDAOImpl();
        userDao.setConnection(conn);

        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));

        logger.info("Client name: " + user.getName());
        logger.info("Client email: " + user.getEmail());

        Map<String, String> messages = new HashMap<>();

        // Verificar si el correo electrónico ya existe en la base de datos
        if (userDao.emailExists(user.getEmail())) {
            messages.put("email", "El correo electrónico ya está registrado");
        }

        // Validar los datos del usuario
        if (!user.validate(messages)) {
            logger.warning("User validation failed");
            for (Map.Entry<String, String> message : messages.entrySet()) {
                logger.warning(message.getKey() + ": " + message.getValue());
            }
            request.setAttribute("messages", messages);
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/registro.jsp");
            view.forward(request, response);
            return;
        }

        try {
            long userId = userDao.add(user);
            if (userId == -1) {
                logger.severe("Failed to add user to the database");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to register user");
                return;
            }
            user.setId(userId);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Debugging statement: checking if the user was correctly added to the session
            if (session.getAttribute("user") == null) {
                logger.severe("Failed to set user in session");
            } else {
                logger.info("User successfully added to session");
            }

            response.sendRedirect("pages/main.html");
        } catch (Exception e) {
            logger.severe("Exception occurred while adding user to database: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
}


