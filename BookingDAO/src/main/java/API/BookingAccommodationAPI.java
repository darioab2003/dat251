package API;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.dao.JDBCBookingsAccommodationsDAOImpl;
import es.unex.pi.dao.BookingsAccommodationsDAO;
import es.unex.pi.model.BookingsAccommodations;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/bookingsAccommodations")
public class BookingAccommodationAPI {

    @Context
    ServletContext sc;
    @Context
    UriInfo uriInfo;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookingsAccommodations> getAllBookingAccommodations(@Context HttpServletRequest request) {
        Connection conn = (Connection) sc.getAttribute("dbConn");
        BookingsAccommodationsDAO bookingsAccommodationsDao = new JDBCBookingsAccommodationsDAOImpl();
        bookingsAccommodationsDao.setConnection(conn);
        return bookingsAccommodationsDao.getAll(); 
    }
    
    @GET
    @Path("/byBooking/{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookingsAccommodations> getBookingAccommodations(@PathParam("bookingId") long bookingId, @Context HttpServletRequest request) {
        Connection conn = (Connection) sc.getAttribute("dbConn");
        BookingsAccommodationsDAO bookingsAccommodationsDao = new JDBCBookingsAccommodationsDAOImpl();
        bookingsAccommodationsDao.setConnection(conn);
        return bookingsAccommodationsDao.getByBooking(bookingId); 
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBookingAccommodation(BookingsAccommodations bookingAccommodation, @Context HttpServletRequest request) {
        Connection conn = (Connection) sc.getAttribute("dbConn");

        BookingsAccommodationsDAO bookingsAccommodationsDao = new JDBCBookingsAccommodationsDAOImpl();
        bookingsAccommodationsDao.setConnection(conn);

        bookingsAccommodationsDao.add(bookingAccommodation);

        return Response.ok().entity(bookingAccommodation).build();
    }
}