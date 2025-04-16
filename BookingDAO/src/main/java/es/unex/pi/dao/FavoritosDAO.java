package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;
import es.unex.pi.model.Favoritos;
import es.unex.pi.model.Property;

public interface FavoritosDAO {

    /**
     * Adds a favorite to the database.
     * 
     * @param favorito
     *            Favoritos object with the favorite details.
     * 
     * @return True if the operation was successful, false otherwise.
     */
	
	public long add(Favoritos favorito);
	public boolean delete(long idu, long idp);
    /**
     * Deletes a favorite from the database.
     * 
     * @param idu
     *            User identifier.
     * @param idp
     *            Property identifier.
     * 
     * @return True if the operation was successful, false otherwise.
     */

    /**
     * Gets all favorites of a specific user from the database.
     * 
     * @param idu
     *            User identifier.
     * 
     * @return List of all favorites of the user.
     */
    public List<Favoritos> getAllByUser(long idu);
    
    public List<Favoritos> getAll();

    /**
     * Checks if a property is a favorite of a specific user.
     * 
     * @param idu
     *            User identifier.
     * @param idp
     *            Property identifier.
     * 
     * @return True if the property is a favorite of the user, false otherwise.
     */
    
    public Property getPropertyById(long idp);
    
    
    boolean isFavorite(long userId, long propertyId);
    List<Long> getFavoritePropertyIds(long userId);
    
    /**
	 * Sets the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

}
