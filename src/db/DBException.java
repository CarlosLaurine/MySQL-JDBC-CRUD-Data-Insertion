package db;

/*Creating custom exception DBException extending 
RuntimeException for best development purposes
*/
public class DBException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/*Generating manual constructor to force the exception
	  to receive a String message and send it to its superclass 
	 */
	public DBException (String msg) {
		super(msg);
	}

}
