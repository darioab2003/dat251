package API;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import es.unex.pi.dao.JDBCPropertyDAOImpl;
import es.unex.pi.dao.JDBCReviewDAOImpl;
import es.unex.pi.dao.PropertyDAO;
import es.unex.pi.dao.ReviewDAO;
import es.unex.pi.model.Property;
import es.unex.pi.model.Review;
import es.unex.pi.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

@Path("/reviews")
public class ReviewAPI {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
      
	  @GET
      @Path("/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public List<Review> getReviews(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
	      PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  
		  ReviewDAO reviewDao = new JDBCReviewDAOImpl();
		  reviewDao.setConnection(conn);
		  
		  List<Review> listReviews = reviewDao.getAll();
		  List<Review> listReview2 = new ArrayList<Review>();
		  
		  for (Review review : listReviews) {
			if(review.getIdp() == propertyid) {
				listReview2.add(review);
			}
		  }

		  return listReview2; 
      }
	  
	  @GET
	    @Path("/getReview/{propertyid: [0-9]+}/{userid: [0-9]+}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getReview(@PathParam("propertyid") long propertyid, @PathParam("userid") long userid,
	                              @Context HttpServletRequest request) {
	        Connection conn = (Connection) sc.getAttribute("dbConn");

	        ReviewDAO reviewDao = new JDBCReviewDAOImpl();
	        reviewDao.setConnection(conn);

	        Review review = reviewDao.get(propertyid, userid);

	        if (review != null) {
	            return Response.ok(review).build();
	        } else {
	            return Response.status(Response.Status.NOT_FOUND).build();
	        }
	    }
      
	  @GET
	    @Path("/yaExiste/{propertyid: [0-9]+}/{userid: [0-9]+}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public boolean verificarExistenciaReview(@PathParam("propertyid") long propertyid, @PathParam("userid") long userid,
	                                  @Context HttpServletRequest request) {
	        Connection conn = (Connection) sc.getAttribute("dbConn");
	          
	        ReviewDAO reviewDao = new JDBCReviewDAOImpl();
	        reviewDao.setConnection(conn);
	          
	        List<Review> listReviews = reviewDao.getAll();
	  
	        boolean reviewExists = false;
	          
	        for (Review review : listReviews) {
	            if(review.getIdp() == propertyid && review.getIdu() == userid) {
	                reviewExists = true;
	                break;
	            }
	        }
	  
	        return reviewExists; 
	    }
      
	  @POST
      @Path("/{propertyid: [0-9]+}")
      @Produces(MediaType.APPLICATION_JSON)
		  public Response addReview(@PathParam("propertyid") long propertyid, Review review, @Context HttpServletRequest request) {
    	  HttpSession session = request.getSession();
    	  Connection conn = (Connection) sc.getAttribute("dbConn");
		  
		  ReviewDAO reviewDao = new JDBCReviewDAOImpl();
		  reviewDao.setConnection(conn);
		  
	      PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
		  propertyDao.setConnection(conn);
		  
		  Response response = null;
		  
		  User user = (User) session.getAttribute("user");
		  
		  review.setIdp(propertyid);
		  review.setIdu(user.getId());
		  
		  reviewDao.add(review);
		  
		  int media = 0;
		  int num = 0;
		  
		  List<Review> listaReview = reviewDao.getAll();
		  for (Review review2 : listaReview) {
			if(review2.getIdp() == propertyid) {
			  num++;
			  media = media + review2.getGrade();
			}
		  }
		  
		  media = media / num;
		  
		  Property property = propertyDao.get(propertyid);
		  property.setGradesAverage(media);
		  propertyDao.update(property);
		  
		  return response; 
      }
	  
	  @PUT
	  @Path("/{propertyid: [0-9]+}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Response updateReview(@PathParam("propertyid") long propertyid, Review review,
	                                 @Context HttpServletRequest request) {
	        HttpSession session = request.getSession();
	        Connection conn = (Connection) sc.getAttribute("dbConn");

	        ReviewDAO reviewDao = new JDBCReviewDAOImpl();
	        reviewDao.setConnection(conn);

	        PropertyDAO propertyDao = new JDBCPropertyDAOImpl();
	        propertyDao.setConnection(conn);

	        Response response = null;

	        User user = (User) session.getAttribute("user");

	        review.setIdp(propertyid);
	        review.setIdu(user.getId());

	        boolean updated = reviewDao.update(review);

	        if (updated) {
	            response = Response.ok().build();
	        } else {
	            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	        }

	        return response;
	    }
}