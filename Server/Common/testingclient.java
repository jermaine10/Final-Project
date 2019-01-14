package Common;
import java.io.IOException;
import java.net.UnknownHostException;


public class testingclient {
	
	//just a test class to make sure things are going through the sockest correctly.
	
	public static void main (String[] args) throws UnknownHostException, IOException{
		
		
		//new instance of the client
		RideABikeClient toServer = new RideABikeClient();
		
		//instance of Socketcmd
		SocketCmd tosend = new SocketCmd();
		
		//some test parameters, see protocol and SocketCmd to know what to set.
		tosend.command = "login";
		tosend.name = "gd113";
		tosend.password = "1234";
		
		Object fromServer = toServer.sendToServer(tosend);
		
		//print return as a string
		print((String) fromServer);
		
		//second test to make server can handle multiple messages.
		System.out.println("Second test");
		tosend.name = "gd114";
		Object fromServer2 = toServer.sendToServer(tosend);
		print((String) fromServer2);
			
		
		
		
		
		
	}
	
	public static void print (String print){
		System.out.println("app finally recieves back " + print);
	}

}
