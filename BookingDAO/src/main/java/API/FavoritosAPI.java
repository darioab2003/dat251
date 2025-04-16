package API;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import es.unex.pi.dao.JDBCFavoritosDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.dao.FavoritosDAO;
import es.unex.pi.model.Favoritos;
import es.unex.pi.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/favoritos")
public class FavoritosAPI {

    @Context
    ServletContext sc;
    
    @Context
    UriInfo uriInfo;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
	  public Response addFavorito(Favoritos newFavorito, @Context HttpServletRequest request) {
    	  Connection conn = (Connection) sc.getAttribute("dbConn");
          FavoritosDAO favoritosDao = new JDBCFavoritosDAOImpl();
          favoritosDao.setConnection(conn);
			
		  Response response = null;
					
		  long id = favoritosDao.add(newFavorito);
		  HttpSession session = request.getSession();
		  session.setAttribute("favorito", newFavorito);
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

    @DELETE
    @Path("/delete/{idu}/{idp}")
    public Response deleteFavorito(@PathParam("idu") long idu, @PathParam("idp") long idp) {
        Connection conn = (Connection) sc.getAttribute("dbConn");
        FavoritosDAO favoritosDao = new JDBCFavoritosDAOImpl();
        favoritosDao.setConnection(conn);
        
        boolean deleted = favoritosDao.delete(idu, idp);
        
        return Response.ok(deleted).build();
    }
    
    @GET
    @Path("/isFavorite/{idu}/{idp}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isFavorite(@PathParam("idu") long idu, @PathParam("idp") long idp) {
        Connection conn = (Connection) sc.getAttribute("dbConn");
        FavoritosDAO favoritosDao = new JDBCFavoritosDAOImpl();
        favoritosDao.setConnection(conn);
        
        boolean isFavorite = favoritosDao.isFavorite(idu, idp);
        
        return Response.ok(isFavorite).build();
    }
    
    @GET
    @Path("/{userid: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Favoritos> getFavoritos(@PathParam("userid") long userid, @Context HttpServletRequest request) {
        Connection conn = (Connection) sc.getAttribute("dbConn");
        
        FavoritosDAO favoritosDao = new JDBCFavoritosDAOImpl();
        favoritosDao.setConnection(conn);
        
        List<Favoritos> listFavoritos = favoritosDao.getAll();
        List<Favoritos> userFavoritos = new ArrayList<>();
        
        for (Favoritos favorito : listFavoritos) {
            if (favorito.getIdu() == userid) {
                userFavoritos.add(favorito);
            }
        }
        
        return userFavoritos;
    }
}
