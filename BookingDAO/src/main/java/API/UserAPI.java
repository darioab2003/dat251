package API;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import es.unex.pi.dao.AccommodationDAO;
import es.unex.pi.dao.FavoritosDAO;
import es.unex.pi.dao.JDBCAccommodationDAOImpl;
import es.unex.pi.dao.JDBCFavoritosDAOImpl;
import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Favoritos;
import es.unex.pi.model.Property;
import es.unex.pi.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/users")
public class UserAPI {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
  
      @GET
      @Produces(MediaType.APPLICATION_JSON)
		  public User getUser(@Context HttpServletRequest request) {
    	  System.out.println("Executing getUser method");
		  Connection conn = (Connection) sc.getAttribute("dbConn");
	      UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);
		  	
		  HttpSession session = request.getSession();
		  User user = (User) session.getAttribute("user");
		  User returnUser = new User();
		  if(user != null) {
			  returnUser = userDao.get(user.getName());
			  //returnUser.setPassword("**********");
		  }
		  System.out.println("Returning user: " + returnUser.getName());
		  return returnUser;
      }
      
      @GET
      @Path("/allUsers")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<User> getAllUsers(@Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
	      UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);
			
		  return userDao.getAll(); 
      }
      
      @GET
      @Path("/{userid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
      public Response getUserById(@PathParam("userid") long userid) {
          Connection conn = (Connection) sc.getAttribute("dbConn");
          UserDAO userDao = new JDBCUserDAOImpl();
          userDao.setConnection(conn);

          User user = userDao.get(userid);
          if (user != null) {
              return Response.ok(user).build();
          } else {
              return Response.status(Response.Status.NOT_FOUND).build();
          }
      }
      
      @GET
      @Path("/{userid: [0-9]+}/favorites")
      @Produces(MediaType.APPLICATION_JSON)
      public List<Long> getUserFavorites(@PathParam("userid") long userid) {
          Connection conn = (Connection) sc.getAttribute("dbConn");
          FavoritosDAO favoritosDao = new JDBCFavoritosDAOImpl();
          favoritosDao.setConnection(conn);

          List<Favoritos> favoritos = favoritosDao.getAllByUser(userid);
          List<Long> favoritePropertyIds = favoritos.stream()
                                                    .map(Favoritos::getIdp)
                                                    .collect(Collectors.toList());

          return favoritePropertyIds;
      }
      
      @GET
      @Path("/session")
      @Produces(MediaType.APPLICATION_JSON)
      public Response getSession(@Context HttpServletRequest request) {
          HttpSession session = request.getSession(false); // No crear una nueva sesión si no existe
          if (session != null) {
              User user = (User) session.getAttribute("user");
              if (user != null) {
                  return Response.ok(user).build(); // Devolver el usuario si está en la sesión
              }
          }
          return Response.status(Response.Status.UNAUTHORIZED).build(); // Devolver estado 401 si no hay sesión o usuario
      }
      
      @PUT
      @Path("/userCorrecto/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
      public boolean userCorrecto(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
          Connection conn = (Connection) sc.getAttribute("dbConn");
          HttpSession session = request.getSession();

          User user = (User) session.getAttribute("user");
          PropertyDAO propertyDAO = new JDBCPropertyDAOImpl();
          propertyDAO.setConnection(conn);
          System.out.println("HOALASDADADSA");
          // Obtener la propiedad por su ID
          Property property = propertyDAO.get(propertyid);

          // Verificar si la propiedad existe y si el usuario de la sesión es propietario
          if (property != null && user != null && property.getIdu() == user.getId()) {
              return true; // El usuario de la sesión es propietario de la propiedad
          } else {
              return false; // No coincide el usuario de la sesión con el propietario de la propiedad
          }
      }
      
      @PUT
      @Path("/userCorrectoAccomodation/{accommodationid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
      public boolean userCorrectoAccomodation(@PathParam("accommodationid") long accommodationid, @Context HttpServletRequest request) {
    	  Connection conn = (Connection) sc.getAttribute("dbConn");
    	  HttpSession session = request.getSession();

    	  User user = (User) session.getAttribute("user");
    	  PropertyDAO propertyDAO = new JDBCPropertyDAOImpl();
    	  propertyDAO.setConnection(conn);

    	  AccommodationDAO accommodationDAO = new JDBCAccommodationDAOImpl();
    	  accommodationDAO.setConnection(conn);

    	  Accommodation accommodation = accommodationDAO.get(accommodationid);
    	  Property property = propertyDAO.get(accommodation.getIdp());
    	  
    	  if (property != null && user != null && property.getIdu() == user.getId()) {
    	      return true;
    	  } else {
    	      return false; 
    	  }
      }

      
      @PUT
      @Path("/uExit")
      @Produces(MediaType.APPLICATION_JSON)
		  public boolean uExit(User user, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
	      UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);
		  List<User> listUser = userDao.getAll();

		  boolean uExit = false;
		  
		  for (User user2 : listUser) {
			if(user2.getName().equals(user.getName())) {
				uExit = true;
			}
		  }
			
		  return uExit; 
      }
	  
	  @PUT
	  @Path("/{userid: [0-9]+}")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response putUser(User userUpdate, @Context HttpServletRequest request) {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);
			
		  Response response = null;
					
		  User user = userDao.get(userUpdate.getId());
		  if (user != null){
			  userDao.update(userUpdate);	
			  HttpSession session = request.getSession();
			  session.setAttribute("user", userUpdate);
			  System.out.println("Updated user: " + userUpdate.getName());
		  }			
		  else {
	            System.out.println("User not found with id: " + userUpdate.getId());
		  }
		  return response;
	  }
	 
	  @POST
	  @Path("/created")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response postUser(User newUser, @Context HttpServletRequest request) {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);
			
		  Response response = null;
					
		  long id = userDao.add(newUser);
		  HttpSession session = request.getSession();
		  session.setAttribute("user", newUser);
		  response = Response
				   .created(
					uriInfo.getAbsolutePathBuilder()
						   .path(Long.toString(id))
						   .build())
				   .contentLocation(
					uriInfo.getAbsolutePathBuilder()
					       .path(Long.toString(id))
					       .build())
					.build();
		  return response;
	  }
	  
	  @POST
	  @Path("/login")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public boolean postUser2Session(User user, @Context HttpServletRequest request) {
		  HttpSession session = request.getSession();
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);
		  
		  List<User> listUser = userDao.getAll();
		  
		  boolean usuarioEnc = false;
		  
		  for (User user2 : listUser) {
			  if(user2.getName().equals(user.getName()) && user2.getPassword().equals(user.getPassword())) {
				  usuarioEnc = true;
			  }
		  }

		  if(usuarioEnc) {
			  user = userDao.get(user.getName());
			  session.setAttribute("user", user);
		  }
		  
		  return usuarioEnc;
	  }
	  
	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response quitUser2Session(@Context HttpServletRequest request) {
		  Response response = null;
          
		    HttpSession session = request.getSession();
		    session.removeAttribute("user");
		    session.invalidate();
		    
		    response = Response.ok().build();
		    return response;
	  }
	  
	  @DELETE
	  @Path("/{userid: [0-9]+}")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response deleteUser(@PathParam("userid") long userid, @Context HttpServletRequest request) {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);
		  
		  Response response = null;
					
		  User user = userDao.get(userid);
		  if (user != null){
			  userDao.delete(userid);											
		  }	
		  
		  HttpSession session = request.getSession();
		  session.removeAttribute("user");
		  
		  return response;
	  }
} 

