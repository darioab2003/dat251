package API;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.AccommodationDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.dao.JDBCAccommodationDAOImpl;
import es.unex.pi.dao.JDBCPropertiesCategoriesDAOImpl;
import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.dao.JDBCReviewDAOImpl;
import es.unex.pi.dao.PropertiesCategoriesDAO;
import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.dao.ReviewDAO;
import es.unex.pi.model.Category;
import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Property;
import es.unex.pi.model.PropertiesCategories;
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

@Path("/properties")
public class PropertyAPI {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
  
      @GET
      @Produces(MediaType.APPLICATION_JSON)
      	  public List<Property> getListaProperties(@Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
	      PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
			
		  return propertyDao.getAll(); 
      }
      
      @GET
      @Path("/{userid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Property> getProperties(@PathParam("userid") long userid, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  
		   
		  List<Property> listProperties = propertyDao.getAll();
		  List<Property> listProperties2 = new ArrayList<Property>();
		  
		  for (Property property : listProperties) {
			if(property.getIdu() == userid) {
				listProperties2.add(property);
			}
		  }

		  return listProperties2; 
      }
      
      
      @GET
      @Path("/property/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Property getProperty(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
	      PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
			
		  return propertyDao.get(propertyid); 
      }
      
      @GET
      @Path("/similaresCategoria/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Set<Property> similaresCategoria(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  
		  PropertiesCategoriesDAO propertiesCategoriesDao = new JDBCPropertiesCategoriesDAOImpl();
		  propertiesCategoriesDao.setConnection(conn);
		  
		  Set<Property> listPropertySimilar = new HashSet<Property>();
		  List<Property> listProperty = propertyDao.getAll();
		  List<PropertiesCategories> listPropertiesCategories = propertiesCategoriesDao.getAll();
		  List<Long> listCategoryId = new ArrayList<Long>();
		  
		  for (PropertiesCategories propertiesCategories : listPropertiesCategories) {
			  if(propertiesCategories.getIdp() == propertyid) {
				  listCategoryId.add(propertiesCategories.getIdct());
			  }
		  }
		  
		  for (Property property : listProperty) {
			  if(property.getId() != propertyid) {
				  for (PropertiesCategories propertiesCategories : listPropertiesCategories) {
					  for (Long catId : listCategoryId) {
						  if(property.getId() == propertiesCategories.getIdp() && propertiesCategories.getIdct() == catId) {
							  listPropertySimilar.add(property);
						  }
					  }
				  }
			  }
		  }
		  
		  return listPropertySimilar;
      }
      
      @GET
      @Path("/byCity/{city}")
      @Produces(MediaType.APPLICATION_JSON)
      public List<Property> getPropertiesByCity(@PathParam("city") String city, @Context HttpServletRequest request) {
          Connection conn = (Connection) sc.getAttribute("dbConn");
          PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
          propertyDao.setConnection(conn);

          List<Property> properties = propertyDao.getByCity(city);

          return properties;
      }
      
      @GET
      @Path("/owner/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
      public User getPropertyOwner(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
          Connection conn = (Connection) sc.getAttribute("dbConn");
          PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
          propertyDao.setConnection(conn);

          Property property = propertyDao.get(propertyid);
          if (property != null) {
              User owner = propertyDao.getOwner(property.getId());
              return owner;
          } else {
              return null;
          }
      }
      
      @GET
      @Path("/orderedByRating")
      @Produces(MediaType.APPLICATION_JSON)
      public List<Property> getAllOrderedByRating(@Context HttpServletRequest request) {
          Connection conn = (Connection) sc.getAttribute("dbConn");
          PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
          propertyDao.setConnection(conn);
          
          return propertyDao.getAllOrderedByRating();
      }
      
      
      @GET
      @Path("/similaresLocalidad/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Set<Property> similaresLocalidad(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  
		  Set<Property> listPropertySimilar = new HashSet<Property>();
		  List<Property> listProperty = propertyDao.getAll();
		  Property property = propertyDao.get(propertyid);
		  
		  for (Property property2 : listProperty) {
			  if(property2.getId() != propertyid && property2.getCity().equals(property.getCity())) {
				  listPropertySimilar.add(property2);
			  }
		  }
		  
		  return listPropertySimilar;
      }
      
      
     
      
      
      @GET
      @Path("/similaresValoracion/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Set<Property> similaresValoracion(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  
		  Set<Property> listPropertySimilar = new HashSet<Property>();
		  List<Property> listProperty = propertyDao.getAll();
		  Property property = propertyDao.get(propertyid);
		  
		  for (Property property2 : listProperty) {
			  if(property.getGradesAverage() - 0.5 < property2.getGradesAverage() 
				&& property.getGradesAverage() + 0.5 > property2.getGradesAverage() 
				&& property2.getId() != propertyid) {
				  listPropertySimilar.add(property2);
			  }
		  }
		  
		  return listPropertySimilar;
      }
      
      @PUT
      @Path("/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Response putProperty(Property propertyUpdate, @Context HttpServletRequest request) {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
			
		  Response response = null;
					
		  Property property = propertyDao.get(propertyUpdate.getId());
		  if (property != null){
			  propertyDao.update(propertyUpdate);	
		  }			
		  
		  return response;
      }
       
      @PUT
      @Path("/disponibles")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Property> verDisponibles(@Context HttpServletRequest request) {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
			
		  List<Property> listProperties = propertyDao.getAll();
		  List<Property> listProperties2 = new ArrayList<Property>();
		  
		  for (Property property : listProperties) {
			if(property.getAvailable() == 1) {
				listProperties2.add(property);
			}
		  }
		  
		  return listProperties2;
      }
      
      @PUT
      @Path("/nodisponibles")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Property> verNoDisponibles(@Context HttpServletRequest request) {
    	  Connection conn = (Connection)sc.getAttribute("dbConn");
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
			
		  List<Property> listProperties = propertyDao.getAll();
		  List<Property> listProperties2 = new ArrayList<Property>();
		  
		  for (Property property : listProperties) {
			if(property.getAvailable() == 0) {
				listProperties2.add(property);
			}
		  }
		  
		  return listProperties2;
      }
      
      @PUT
      @Path("/ordenar")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Property> ordenar (List<Property> listProperties, @Context HttpServletRequest request) {
		  
    	  listProperties.sort((r1, r2) -> Double.compare(r2.getGradesAverage(), r1.getGradesAverage()));
		  
		  return listProperties;
      }
      
      @PUT
      @Path("/noOrdenar")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Property> noOrdenar (List<Property> listProperties, @Context HttpServletRequest request) {
		  
    	  listProperties.sort((r2, r1) -> Long.compare(r2.getId(), r1.getId()));
		  
		  return listProperties;
      }
      
      @DELETE
      @Path("/{propertyid: [0-9]+}")
      public Response deleteProperty(@PathParam("propertyid") long propertyid,
    		  @Context HttpServletRequest request) {
    	
    	  Connection conn = (Connection) sc.getAttribute("dbConn");
    	  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  Response response = null;
			
		  Property property = propertyDao.get(propertyid);
		  if (property != null){
			  propertyDao.delete(propertyid);
		  }
    	
		  return response; 
      }
      
	  @POST
	  @Path("/created")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response postProperty (Property newProperty, @Context HttpServletRequest request) {
		  HttpSession session = request.getSession();
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  
		  Response response = null;
		  
		  User user = (User) session.getAttribute("user");
		  
		  newProperty.setIdu((int) user.getId());
					
		  long id = propertyDao.add(newProperty);
		  
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
		  
		  session.setAttribute("id", id);
		  return response;
	  }
	  
	  @GET
	  @Path("/orderedByEcoFriendly")
	  public Response getAllOrderedByEcoFriendly() {
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
	      List<Property> list = propertyDao.findAllOrderByEcoFriendly();
	      return Response.ok(list).build();
	  }
} 
