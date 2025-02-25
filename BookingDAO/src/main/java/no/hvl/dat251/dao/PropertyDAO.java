package no.hvl.dat251.dao;

import java.sql.Connection;
import java.util.List;

import no.hvl.dat251.model.Property;
import no.hvl.dat251.model.User;


public interface PropertyDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Gets a property from the DB using id.
	 * 
	 * @param id
	 *            Property Identifier.
	 * 
	 * @return Property object with that id.
	 */
	public Property get(long id);

	/**
	 * Gets all the notes from the database.
	 * 
	 * @return List of all the properties from the database.
	 */
	public List<Property> getAll();
	
	/**
	 * Gets all the properties from the database that contain a text in the name.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the properties from the database that contain a text either in the name.
	 */	
	public List<Property> getAllBySearchName(String search);


	/**
	 * Gets all the properties from the database that belong to a user.
	 * 
	 * @param idu
	 *            User identifier.
	 * 
	 * @return List of all the properties from the database that belong to a user
	 */	
	public List<Property> getAllByUser(long idu);
	
	

	/**
	 * Adds a property to the database.
	 * 
	 * @param property
	 *            Property object with the property details.
	 * 
	 * @return Property identifier or -1 in case the operation failed.
	 */
	
	public long add(Property property);

	/**
	 * Updates an existing property in the database.
	 * 
	 * @param property
	 *            Property object with the new details of the existing property.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	public boolean update(Property property);

	/**
	 * Deletes a property from the database.
	 * 
	 * @param id
	 *            Property identifier.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	public boolean delete(long id);
	List<Property> getByCity(String city);
	User getOwner(long propertyId);
	public List<Property> getAllOrderedByRating();
	public User getPropertyOwner(long propertyId);
}
