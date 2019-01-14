package Server;
import java.io.IOException;

import Common.SocketCmd;



public class RideABikeProtocol {


	public Object processInput(Object fromClient) throws IOException {
		
		
		//serialised object
		SocketCmd cmd = null;
		cmd = (SocketCmd) fromClient;
		
		//parameters from SocketCmd
		//alter as new ones are added
		String
		username,
		password,
		userID,
		email;
		
		//
		
		
		int commandType = 0;
		
		System.out.println("protocol receieves message " + fromClient);
		
		//commented out for testing purposes.
		//Control control = new Control();
		
		

		// testing
		final socketTesting testing = new socketTesting();

		

		
		

		//takes command and changes  request type depending
		if ((cmd.command).equals("login")) {
			
			System.out.println(" 'protocol' request change to 1");
			commandType = 1;
		}

		if (cmd.equals("dock")) {
			System.out.println(" 'protocol' request change to 2");
			commandType = 2;
		}
		
		

		String request = "";
		
		//switch statement that calls the corresponding method based on the command send by client
		//in each case, take the parameters you need form the object (see SocketCmd) and call the method in Control.java using them
		switch (commandType) {

		//login case
		case 1:
			
			username = cmd.name;
			password = cmd.password;
			
			
			
			//test
			request = testing.login(username, password);
			
			//method to call
			//request = control.getLogin(username, password);
			break;
			
		//	
		case 2:
			
			
			//method to call
			request = testing.dockStatus();
			break;
			
		case 3:
			
			
			//method to call
			request = testing.dockStatus();
			break;
		
		case 4:
			
			
			//method to call
			request = testing.dockStatus();
			break;
			
		case 5:
			
			
			//method to call
			request = testing.dockStatus();
			break;
			
		case 6:
			
			
			//method to call
			request = testing.dockStatus();
			break;
			
		
		default:
			//error
			request = "something went wrong";
			

		}
		System.out.println("'protocol' returning 'request' to server");
		return  request;

	}


}
