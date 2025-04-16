package API;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import es.unex.pi.dao.JDBCBookingDAOImpl;
import es.unex.pi.dao.JDBCBookingsAccommodationsDAOImpl;
import es.unex.pi.dao.BookingDAO;
import es.unex.pi.dao.BookingsAccommodationsDAO;
import es.unex.pi.model.Accommodation;
import es.unex.pi.model.Booking;
import es.unex.pi.model.BookingsAccommodations;
import es.unex.pi.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/bookings")
public class BookingAPI {

    @Context
    ServletContext sc;
    @Context
    UriInfo uriInfo;
    
    @GET
    @Path("/{userid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Booking> getBookings(@PathParam("userid") long userid, @Context HttpServletRequest request) {
        Connection conn = (Connection) sc.getAttribute("dbConn");
        BookingDAO bookingDao = new JDBCBookingDAOImpl();
        bookingDao.setConnection(conn);
        
        List<Booking> listBookings = bookingDao.getAll();
        List<Booking> userBookings = new ArrayList<>();
        
        for (Booking booking : listBookings) {
            if (booking.getIdu() == userid) {
                userBookings.add(booking);
            }
        }

        return userBookings; 
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(Booking booking, @Context HttpServletRequest request) {
        Connection conn = (Connection) sc.getAttribute("dbConn");
        HttpSession session = request.getSession();
        
        BookingDAO bookingDao = new JDBCBookingDAOImpl();
        bookingDao.setConnection(conn);
        
        User user = (User) session.getAttribute("user");
        booking.setIdu(user.getId());  // Asignamos el ID del usuario actual
        
        long bookingId = bookingDao.add(booking);  // Añadimos la reserva a la base de datos
        booking.setId(bookingId);  // Asignamos el ID generado a la reserva
        
        return Response.ok().entity(booking).build();
    }
}