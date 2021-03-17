package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	public static Connection con = null;
	
	//Creating method to open the connection with database
	public static Connection getConnection(){
		//Testing whether connection is null in order to open it
		if (con == null) {
		//Using try catch structure to treat any SQL Exceptions	
		try {	
			//Loading properties in an object props
			Properties props = loadProperties();
			//Setting a String variable with property url
			String url = props.getProperty("dburl");
			/*Initializing connection through DriverManager
			  with url and user/password props passed as 
			  getConnection parameters
			 */
			con = DriverManager.getConnection(url, props);
		}
		catch(SQLException e) {
			/*Throwing custom DBException (which extends RuntimeException
			  in order to avoid compilation interruptions (excessive try catches)*/ 
			throw new DBException(e.getMessage());
		}
	}
		return con;		
	}
	
	//Creating method to close database connection
	public static void closeConnection() {
		//Testing whether connection is null in order to close it
		if(con != null) {
			//Using try catch structure to treat any SQL Exceptions	
			try {
				con.close();
			}
			catch (SQLException e) {
			/*Throwing custom DBException (which extends RuntimeException
		  	  in order to avoid compilation interruptions (excessive try catches)*/ 				
			throw new DBException(e.getMessage());	
			}
			}
	}
	
	/*creating aux method to load db.properties and store
	  them at an specific object*/
	private static Properties loadProperties() {
		
		//Using try with resources to solve any I/O closing case
		
		/*Since db.properties is in the same folder, only its name is enough
		  for being passed as parameter at the new Stream object FileInputStream*/
		
		try(FileInputStream fs = new FileInputStream("db.properties")){
		Properties props = new Properties();
	    //using command .load to read db.properties and to store its data inside props object
		props.load(fs);
		return props;
		
		}
		//catching only IOException since FileNotFoundException is also an IOException
		catch(IOException e) {
			/*Throwing our custom DBException and passing IOException message as a parameter
			  in DBException constructor*/
			throw new DBException (e.getMessage());
		}
	}
	
	
	/*               =============== 
	                 CLOSING METHODS
	                 ===============
	*/
	
	/*Specific methods for closing Statement and ResultSet resources in order to
	 * avoid multiple try catches at MainProgram through reuse and delegation,
	 * once the specific methods will treat the exceptions with a try catch 
	 * structure and throw a DBException in case anything occurs, which comes in
	 * handy since DBException extends RuntimeException, and will not disturb the
	 * code with multiple compilation requests
	 */
	public static void closeStatement (Statement pst) {
		if(pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	public static void closeResultSet (ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	public static void closeConnection (Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
}
