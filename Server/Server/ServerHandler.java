package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;


public class ServerHandler implements Runnable {
	
	
	
	private Socket server;

	
	ServerHandler(Socket server){
		this.server=server;
	}
	
	public void run(){
		
		System.out.println("new handler created");
		
	

	try {
		
		ObjectInputStream inputFromClient = null;
		ObjectOutputStream outputToClient = null;
		
		

		System.out.println("server started at " + new Date());

		
		
		//set input and output streams.
		inputFromClient = new ObjectInputStream(server.getInputStream());
		outputToClient = new ObjectOutputStream(server.getOutputStream());
		
		
		
		//read the input from client
		Object fromClient = inputFromClient.readObject();
		
		//create new instance of protocol
		RideABikeProtocol protocol = new RideABikeProtocol();
		
		//set output to the what is returned from protocol	
		Object output = protocol.processInput(fromClient);
		


		//push reply to client	
		outputToClient.writeObject(output);
		
		//inputFromClient.close();
		//outputToClient.close();
		//server.close();
		

			

		

	} catch (SocketTimeoutException s) {
		System.out.println("Socket timed out!");
		
	} catch (IOException e) {
		e.printStackTrace();
		
	} catch (ClassNotFoundException e) {
		
		e.printStackTrace();
	}
}

}
