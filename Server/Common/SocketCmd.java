package Common;

public class SocketCmd implements java.io.Serializable {

	/**
	 * This class contains all the various parameters
	 * you can use to send date through
	 * the sockets.
	 */
	
	private static final long serialVersionUID = 1L;
	
	//this must always be set and called to determine what parameters will exist
	public String command;
	
	//users name
	public String name;
	
	//users password
	public String password;
	
	//users ID
	public String uid;
	
	//users email
	public String email;
	
	//feel free to add more as needed.

}
