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
    
   
    // This method centralizes the process of obtaining the database connection.
    private Connection getConnection() {
        return (Connection) sc.getAttribute("dbConn");
    }
    
    // Factory Method to create and configure the Accommodation DAO.
    private AccommodationDAO getAccommodationDAO() {
        AccommodationDAO dao = new JDBCAccommodationDAOImpl();
        dao.setConnection(getConnection());
        return dao;
    }
    
    // Helper method to update the price, avoiding duplication in adding or subtracting.
    private int updatePrice(long accommodationid, int precio, int multiplier) {
        AccommodationDAO dao = getAccommodationDAO();
        return precio + multiplier * dao.get(accommodationid).getPrice();
    }
    
    // ********** Refactored endpoints **********
    
    @GET
    @Path("/{propertyid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Accommodation> getAccommodations(@PathParam("propertyid") long propertyid, @Context HttpServletRequest request) {
        AccommodationDAO dao = getAccommodationDAO();
        List<Accommodation> allAccommodations = dao.getAll();
        List<Accommodation> filtered = new ArrayList<>();
        for (Accommodation accommodation : allAccommodations) {
            if (accommodation.getIdp() == propertyid) {
                filtered.add(accommodation);
            }
        }
        return filtered;
    }
    
    @GET
    @Path("/accommodation/{accommodationid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Accommodation getAccommodation(@PathParam("accommodationid") long accommodationid, @Context HttpServletRequest request) {
        return getAccommodationDAO().get(accommodationid);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Accommodation> getAllAccommodations(@Context HttpServletRequest request) {
        return getAccommodationDAO().getAll();
    }
    
    @PUT
    @Path("/sumarPrecio/{accommodationid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public int sumarPrecio(@PathParam("accommodationid") long accommodationid, int precio, @Context HttpServletRequest request) {
        return updatePrice(accommodationid, precio, 1);
    }
    
    @PUT
    @Path("/restarPrecio/{accommodationid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public int restarPrecio(@PathParam("accommodationid") long accommodationid, int precio, @Context HttpServletRequest request) {
        return updatePrice(accommodationid, precio, -1);
    }
    
    @PUT
    @Path("/{accommodationid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putAccommodation(Accommodation accommodationUpdate, @Context HttpServletRequest request) {
        AccommodationDAO dao = getAccommodationDAO();
        Accommodation accommodation = dao.get(accommodationUpdate.getId());
        if (accommodation != null) {
            dao.update(accommodationUpdate);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @PUT
    @Path("/anadir/{accommodationid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Accommodation> anadirAlojamiento(@PathParam("accommodationid") long accommodationid, List<Accommodation> accommodations, @Context HttpServletRequest request) {
        AccommodationDAO dao = getAccommodationDAO();
        accommodations.add(dao.get(accommodationid));
        return accommodations;
    }
    
    @PUT
    @Path("/quitar/{accommodationid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Accommodation> quitarAlojamiento(@PathParam("accommodationid") long accommodationid, List<Accommodation> accommodations, @Context HttpServletRequest request) {
        AccommodationDAO dao = getAccommodationDAO();
        accommodations.remove(dao.get(accommodationid));
        return accommodations;
    }
    
    @POST
    @Path("{propertyid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postAccommodation(@PathParam("propertyid") long propertyid, Accommodation newAccommodation, @Context HttpServletRequest request) {
        AccommodationDAO dao = getAccommodationDAO();
        newAccommodation.setIdp(propertyid);
        dao.add(newAccommodation);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{accommodationid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAccommodation(@PathParam("accommodationid") long accommodationid, @Context HttpServletRequest request) {
        AccommodationDAO dao = getAccommodationDAO();
        dao.delete(accommodationid);
        return Response.ok().build();
    }
}


