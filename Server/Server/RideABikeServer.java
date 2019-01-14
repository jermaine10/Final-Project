package Server;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class RideABikeServer {
	
	private static Control controls = new Control();

	@SuppressWarnings("resource")
	public static void main(String[] args) throws ClassNotFoundException {

		// the port number to listen to
		int portNumber = 4444;

		
		
		try {
			
			// set listen port
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket server;
			
			/*
			 * added by jermaine for scheduled maintanance of database and routines mailing 
			 */
			final ScheduledExecutorService scheduler =  Executors.newScheduledThreadPool(3);
			ScheduledFuture<?> schedules = scheduler.scheduleWithFixedDelay(dailyMaintanance,1, 1, TimeUnit.DAYS);
			ScheduledFuture<?> schedules2 = scheduler.scheduleWithFixedDelay(MonthlyBalance,0, controls.remainingDays(), TimeUnit.DAYS);
			/*
			 *  finished
			 */

			while(true){
			// listen for connection request
			server = serverSocket.accept();
			ServerHandler handler = new ServerHandler(server);
			
			//thread for server handler
			Thread t = new Thread(handler);
			t.start();
			
			}
			
			
		} catch (SocketTimeoutException s) {
			System.out.println("Socket timed out!");
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	
	/*
	 * added by jermaine for scheduled maintanance of database and routines mailing 
	 */
	static final Runnable dailyMaintanance = new Runnable(){
		@Override
		public void run() {
			
			//controls.sendBrokenBikeNotification();
			controls.clearLogs();			// clear all data that has dated 3 months or above.
			System.out.println("test1");
		
		}
		
	};
	
	
	static final Runnable MonthlyBalance = new Runnable(){
		@Override
		public void run(){
			controls.maintainBike();
				//controls.sendMonthlyBalance();	
			System.out.println("test2");
			
		}
	
	};
	
	/*
	 *  finished
	 */

	
}
