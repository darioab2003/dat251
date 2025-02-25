package no.hvl.dat251.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import no.hvl.dat251.dao.AccommodationDAO;
import no.hvl.dat251.dao.JDBCAccommodationDAOImpl;
import no.hvl.dat251.model.Accommodation;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestAccommodationDAO {

	static DBConn dbConn;
	static AccommodationDAO accommodationDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    accommodationDAO = new JDBCAccommodationDAOImpl();
		accommodationDAO.setConnection(conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		dbConn.destroy(conn);
	    
	}

	@Before
	public void setUpBeforeMethod() throws Exception {
	
	}

	@Test
	public void test1BaseData() {
		
		Accommodation accommodation01 = accommodationDAO.get(1);
		assertEquals(accommodation01.getId(),1);
		assertEquals(accommodation01.getName(),"Doble Superior");
		
		Accommodation accommodation02 = accommodationDAO.get(2);
		assertEquals(accommodation02.getId(),2);
		assertEquals(accommodation02.getName(),"Suite");

	}
	
	
	@Test
	public void test2Add(){
		Accommodation accommodation01 = new Accommodation();
		accommodation01.setName("newAccommodation");
		accommodation01.setDescription("newDescription");
		accommodation01.setNumAccommodations(15);
		
		long value = accommodationDAO.add(accommodation01);
		
		Accommodation accommodation02 = accommodationDAO.get("newAccommodation");
		assertEquals(accommodation01.getName(),accommodation02.getName());
	}
	
	@Test
	public void test3Modify(){
		Accommodation accommodation01 = accommodationDAO.get("newAccommodation");
		accommodation01.setName("newAccommodationUpdated");
		accommodationDAO.update(accommodation01);
		
		Accommodation accommodation02 = accommodationDAO.get("newAccommodationUpdated");		
		assertEquals(accommodation01.getName(),accommodation02.getName());
	}
	
	@Test
	public void test4Delete(){
		 Accommodation accommodation01 = accommodationDAO.get("newAccommodationUpdated");
		 accommodationDAO.delete(accommodation01.getId());
		 
		 Accommodation accommodation02 = accommodationDAO.get("newAccommodationUpdated");
 		 assertEquals(null, accommodation02);
 		 
 		accommodationDAO.getAll();
	}

}
