package Common;
import java.io.*;
import java.net.*;

public class RideABikeClient {

	
	public Object sendToServer(Object toSend){
		
		Object returnMessage = null;
		
		try{
	
		// set the URL/IP address of the server
		//currently just set to local Host for now
		String hostName = "localhost";

		// set the port number open on the server.
		int portNumber = 4444;

		@SuppressWarnings("resource")
		Socket socket = new Socket(hostName, portNumber);
		
		
		ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
		//testing output
		System.out.println("'Client'" + toSend + " being sent to server");
		
		//send to server
		toServer.writeObject(toSend);
		toServer.flush();
				
		returnMessage = fromServer.readObject();
		
				
		//socket.close();
		//toServer.close();
		//toServer.close();
				
				
				

				
				
			
		
		
		}catch(Exception e){
			e.printStackTrace(); 
		}
		return returnMessage;
		
		
	}
	
}
