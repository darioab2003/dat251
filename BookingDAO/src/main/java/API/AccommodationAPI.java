package API;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import es.unex.pi.dao.AccommodationDAO;
import es.unex.pi.dao.JDBCAccommodationDAOImpl;
import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.model.Accommodation;
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

@Path("/accommodations")
public class AccommodationAPI {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
      
	  @GET
      @Path("/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Accommodation> getAccommodations(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
	      PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);
		  
		  List<Accommodation> listAccommodations = accommodationDao.getAll();
		  List<Accommodation> listAccommodations2 = new ArrayList<Accommodation>();
		  
		  for (Accommodation accommodation : listAccommodations) {
			if(accommodation.getIdp() == propertyid) {
				listAccommodations2.add(accommodation);
			}
		  }

		  return listAccommodations2; 
      }
      
	  @GET
      @Path("/accommodation/{accommodationid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Accommodation getAccommodation(@PathParam("accommodationid") long accommodationid, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);
		  
		  return accommodationDao.get(accommodationid); 
      }
      
	  @GET
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Accommodation> getAllAccommodations(@Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);
		  
		  return accommodationDao.getAll(); 
      }
      
	  @PUT
      @Path("/sumarPrecio/{accommodationid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public int sumarPrecio(@PathParam("accommodationid") long accommodationid, int precio, @Context HttpServletRequest request) {
    	  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);
    	  
    	  precio = precio + accommodationDao.get(accommodationid).getPrice();
    	  
		  return precio; 
      }
      
	  @PUT
      @Path("/restarPrecio/{accommodationid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public int restarPrecio(@PathParam("accommodationid") long accommodationid, int precio, @Context HttpServletRequest request) {
    	  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);
    	  
    	  precio = precio - accommodationDao.get(accommodationid).getPrice();
    	  
		  return precio; 
      }
      
	  @PUT
      @Path("/{accommodationid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Response putAccommodation(Accommodation accommodationUpdate, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);

		  Response response = null;
		  
		  Accommodation accommodation = accommodationDao.get(accommodationUpdate.getId());
		  if (accommodation != null){
			  accommodationDao.update(accommodationUpdate);	
		  }
		  
		  return response; 
      }
      
	  @PUT
      @Path("/anadir/{accommodationid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Accommodation> anadirAlojamiento(@PathParam("accommodationid") long accommodationid, List<Accommodation> accommodations, @Context HttpServletRequest request) {
    	  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);
		  
		  accommodations.add(accommodationDao.get(accommodationid));
		  
		  return accommodations; 
      }
      
	  @PUT
      @Path("/quitar/{accommodationid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Accommodation> quitarAlojamiento(@PathParam("accommodationid") long accommodationid, List<Accommodation> accommodations, @Context HttpServletRequest request) {
    	  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);
		  
		  accommodations.remove(accommodationDao.get(accommodationid));
		  
		  return accommodations; 
      }
      
	  @POST
	  @Path("{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Response postAccommodation(@PathParam("propertyid") long propertyid, Accommodation newAccommodation, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDAO = new JDBCAccommodationDAOImpl();
		  accommodationDAO.setConnection(conn);

		  Response response = null;
		  
		  newAccommodation.setIdp(propertyid);
		  accommodationDAO.add(newAccommodation);
		  
		  return response; 
      }
      
	  @DELETE
      @Path("/{accommodationid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Response deleteAccommodation(@PathParam("accommodationid") long accommodationid, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  AccommodationDAO accommodationDao = new JDBCAccommodationDAOImpl();
		  accommodationDao.setConnection(conn);

		  Response response = null;
		  
		  accommodationDao.delete(accommodationid);
		  
		  return response; 
      }
} 