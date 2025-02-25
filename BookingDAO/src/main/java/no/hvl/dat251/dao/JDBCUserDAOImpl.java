package no.hvl.dat251.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import no.hvl.dat251.model.User;

public class JDBCUserDAOImpl implements UserDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());
	
	@Override
	public User get(long id) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id ="+id);			 
			if (!rs.next()) 
				return null; 
			
			user  = new User();
			fromRsToUserObject(rs,user);
			logger.info("fetching User by id: "+id+" -> "+user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public User get(String name) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE name ='"+name+"'");			 
			if (!rs.next()) return null; 
			user  = new User();	 
			fromRsToUserObject(rs,user);
			logger.info("fetching User by name: "+ name + " -> "+ user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public User getEmail(String email) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE email ='"+email+"'");			 
			if (!rs.next()) return null; 
			user  = new User();	 
			fromRsToUserObject(rs,user);
			logger.info("fetching User by name: "+ email + " -> "+ user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	
	public List<User> getAll() {

		if (conn == null) return null;
		
		ArrayList<User> users = new ArrayList<User>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users");
			while ( rs.next() ) {
				User user = new User();
				fromRsToUserObject(rs,user);
				user.setPassword("********");//We return all users with a hidden password				
				users.add(user);
				logger.info("fetching users: "+user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	

	@Override
	public long add(User user) {
		long id=-1;
		long lastidu=-1;
		if (conn != null){

			Statement stmt;
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='users'");			 
				if (!rs.next()) return -1; 
				lastidu=rs.getInt("seq");
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Users (email,password,name,surname) VALUES('"
									+user.getEmail()+"','"
									+user.getPassword()+"','"
									+user.getName()+"','"
									+user.getSurname()+"')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='users'");			 
				if (!rs.next()) return -1; 
				id=rs.getInt("seq");
				if (id<=lastidu) return -1;
											
				logger.info("CREATING User("+id+"): "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return id;
	}

	@Override
	public boolean update(User user) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE users SET email='"+user.getEmail()
									+"', password='"+user.getPassword()
									+"', name='"+user.getName()
									+"', surname='"+user.getSurname()
									+"' WHERE id = "+user.getId());
				logger.info("updating User: "+user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return done;

	}

	@Override
	public boolean delete(long id) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM users WHERE id ="+id);
				logger.info("deleting User: "+id);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}
	
	public void fromRsToUserObject(ResultSet rs, User user) throws SQLException {		
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setSurname(rs.getString("surname"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
	public boolean emailExists(String email) {
	    boolean exists = false;
	    // Lógica para verificar si el correo electrónico existe en la base de datos
	    // Ejemplo:
	    try {
	        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?");
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            exists = count > 0;
	        }
	    } catch (SQLException e) {
	        // Manejo de excepciones
	        e.printStackTrace();
	    }
	    return exists;
	}
	
}