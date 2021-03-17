package application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class MainProgram {

	public static void main(String[] args) {
		
		//Creating SimpleDateFormat object to stance data at PreparedStatement object
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		//Initiating null variables with the JDBC Class-Types needed for selection
		Connection con = null;
		
		/*Using Method PreparedStatement to set query commands with parameters
		that will be fulfilled later*/	
		PreparedStatement pst = null;
		
		
		//Using try catch body to treat SQL exceptions
		try {
			
		//Opening db connection	
		con = DB.getConnection();
		/*Setting PreparedStatement Object pst with Insertion command 
		  with parameters passed as values to be replaced right after*/
		
		/*Also overriding prepareStatement method's contructor to include
		a new parameter - the Generated Key*/ 
		
		//OBS1: No case sensitivity at the command string
		pst = con.prepareStatement("insert into seller (Name, Email, BirthDate, BaseSalary, DepartmentId) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		//Replacing ? parameters with the desired data for new seller insertion
		
		pst.setString(1, "Anthony Hopkins");
		pst.setString(2, "tony2000@yahoo.com.br");
		/*To work with Data at SQL, java.sql.Date must be imported
		  instead of the usual java.util.Date*/
		pst.setDate  (3, new java.sql.Date(sdf.parse("27/06/2020 13:25:38").getTime()));
		pst.setDouble(4, 3000.0);
		pst.setInt   (5, 4);
		
		/*Executing command with all updates with an integer of net number of 
          lines as return, and saving this information in an int variable 
          rowsChanged
        */
		int rowsChanged = pst.executeUpdate();
		
		if(rowsChanged>0) {
			/*Getting all generated keys from Statement object and storing 
			  them at a Result Set object*/
			ResultSet rs = pst.getGeneratedKeys();
			//Going through ResultSet generated table to get the Keys
			while(rs.next()) {
				int id = rs.getInt(1);
				System.out.println("Done, ID = " + id + "!");
			}
		}
		else {
			System.out.println("No Rows were Changed!");
		}
		
		
		}//Handling specific exceptions
		catch (ParseException e) {
			e.getMessage();
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
		
		
		//Using finally block to ensure all external resources to JVM will be closed
		finally {
			
			DB.closeConnection();
			
			//Upcast in closeStatement as PreparedStatement pst is passed as a Statement parameter
			
			DB.closeStatement(pst);
			
			
		}


}}