package Server;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import DAO.BikeDAO;
import DAO.DockDAO;
import DAO.FeedbacksDAO;
import DAO.LogDAO;
import DAO.RoutesDAO;
import DAO.StationDAO;
import DAO.UserDAO;
import Entities.Dock;
import Entities.Feedbacks;
import Entities.User;
import Exceptions.notABrokenBikeException;
import Exceptions.userAlreadyExistException;
import Exceptions.userHasRentedAbikeException;

public class Control  {
	private UserDAO userDAO;
	private DockDAO dockDAO;
	private BikeDAO bikeDAO;
	private StationDAO stationDAO;
	private LogDAO logDAO;
	private RoutesDAO routesDAO;
	private FeedbacksDAO feedbacksDAO;
	Timer timer;

	public Control(){
	Connection connection = null;
		try{	
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/" , "SA","");
			userDAO = new UserDAO(connection);
			dockDAO = new DockDAO(connection);
			bikeDAO = new BikeDAO(connection);
			logDAO = new LogDAO(connection);
			stationDAO = new StationDAO(connection);
			routesDAO = new RoutesDAO(connection);
			feedbacksDAO = new FeedbacksDAO(connection);
			dockDAO.stationDAO = stationDAO;
			dockDAO.logDAO= logDAO;
			dockDAO.userDAO = userDAO;
			dockDAO.bikeDAO = bikeDAO;

		}catch(Exception e){
			System.out.println("Connection refused ");
			System.exit(0);
			
		}
		
	}
	
	//SEE CONTROLFUNCTIONTEST TO SEE HOW TO USE THE FUNCTION
	//RENT BIKE FUNCTION STARTS
	public ArrayList<String> getAllDockFromStation(int sid) throws Exception{
		ArrayList<String> d= dockDAO.getDockFromStation(sid);
		if(d==null){
			String x ="Invalid/unregistered Station id";
			d = new ArrayList<String>();
			d.add(x);
			
		}
		return d; //result is Dockid-STATUS
		
	}
	
	
	//NOTE FOR REMOVE BIKE IT CAN ONLY REMOVE BIKE FROM DOCK WITH UNAVAILABLE STATUS.
	//A USER CAN ONLY REMOVE 1 (ONE) BIKE
	public boolean removeBike(String uid,int dockid) throws userHasRentedAbikeException{
		boolean result = dockDAO.removeBike(uid, dockid);
		return result; //if false fails, true success 
		
	}
	
	// ROUTING FUNCTIONS TO BE IMPLEMENTED SOMEWHERE
	public BigDecimal[] getLongitudeLatitude(int stationid) throws Exception{
		BigDecimal[] x = stationDAO.getLatLong(stationid);
		if(x == null){
			throw new Exception("Station id doesnt exist");
	
		}
		return x;

	}
	
	public boolean insertBike(int dockid,int bikeid) throws Exception{
		boolean x =dockDAO.insertBike(dockid, bikeid);
		return x;	//balance are calculated and log are also updated inside insert bike.
					//if the bike inserted is a bike with broken status then it resets the bike usage and status to good.
		
	}
	
	//RENT BIKE ENDS
	
	
	
	//LOGIN AND USER CONTROLS FUNCTION STARTS
	public String getLogin(String username, String password) throws Exception{
		User u = userDAO.getLogin(username,password);
		String x;
		if (u != null){
			x = u.toString();
			
		}else{
			x = "fail";
			
		}
		return x; //if successfull return in format of username-password-name-subscription-balance if not return fail.
		
	}
	
	public boolean registerUser(String uid, String password, String name,boolean subs) throws userAlreadyExistException{
		boolean b = userDAO.registerUser(uid, password, name, subs);
		return b; //Return true if uid is unique.
		
	}
	
	public boolean registerAdmin(String uid,String password, String name) throws userAlreadyExistException{
		boolean b = userDAO.registerAdmin(uid, password, name); //assuming admin doesnt need newsletter.
		return b; //Return true if uid is unique.
		
	}
	
	public boolean updateUname(String uid, String name) throws Exception{
		boolean x =userDAO.updateName(uid,name);
		return x; //update the name using the uid. 
		
	}
	
	public Boolean updateUpassword(String uid, String opw, String npw) throws Exception{
		boolean x =userDAO.updatePassword(uid,opw,npw);
		return x; //if false fails, true success
		
	}
	
	//LOGIN AND USER CONTROLS ENDS
	
	
	
	
	//OTHER FUNCTION STARTS
	
	public boolean reportBrokenBike(int bikeid){
		boolean x = dockDAO.reportBrokenBike(bikeid);
		return x; // reporting a bike changing its attached dock and its status to broken.
		
	}
	
	public boolean removeBrokenBike(int dockid) throws notABrokenBikeException{
		boolean x = dockDAO.removeBrokenBike(dockid);
		return x; // removing broken bike can only be done with user with admin status.
		
	}
	
	public ArrayList<String> getAllStations(){
		ArrayList<String> x = stationDAO.getAllStations(); // get all station and its id.
		return x; // format of string is in ID-stationName;
		
	}
	
	
	public int getNumberOfAvailableDockInStation(int sid) throws Exception{
		int i = dockDAO.getNumberOfAvailableDock(sid);
		return i; //get the number of docks that is available.
	
	}
	
	public ArrayList<String> getOveralRatingonAllRoute(){
		ArrayList<String> s = feedbacksDAO.getOveralRatingonAllRoute();
		return s;
		
	}
	
	public ArrayList<Feedbacks> getAllFeedBacksonRoute(int routeid){
		ArrayList<Feedbacks> s = feedbacksDAO.getAllFeedbacksOnRoute(routeid);
		return s;
		
	}
	
	public void addFeedbacks(int routeid, String uid, String message, int rating){
		feedbacksDAO.addFeedbacks(routeid, uid, message, rating);
		
	}
	
	//NOTE UNIMPLEMENTED SINCE ROUTING ALGORITHM IS NOT YET IMPLEMENTED
	//USAGE -> everytime the routing algorithm is called between stations A and B, its route usage increased by 1
	public ArrayList<Integer> getMostUsedRoutes(){
		ArrayList<Integer> x = routesDAO.getPopularRoutes(); //returns the id of most used routes.
		return x; 
		
	}
	
	public ArrayList<String> getAllStationsWithBrokenBike(){
		ArrayList<String> s = dockDAO.findBrokenDOCK(); // get all stations and its dock number with a broken status. used with mailings
		return s;
	
	}

	//im assuming subscription = subscriber mail.
	public void sendSubscribersMail(String subject,String bodies, String filename){
		Mailings.sendMailWithAttachments(subject, bodies, userDAO.getAllSubscriber(), filename); // send mail with attachments
		
	}
	

	//ROUTINE JOB START, mainly are used for sending emails to admin to maintain bike, cleaning data, and bike maintanance.
	
	public void sendBrokenBikeNotification(){
		Mailings.sendNotification("Dock and bike that require maintanance",dockDAO.findBrokenDOCK(),userDAO.getAllAdmin()); 

	}
	
	public void maintainBike(){
		dockDAO.maintainBike(); // set all bike that has usage number of >50 to broken, scheduled at once a month at server.
	}
	
	public void clearLogs(){
		logDAO.clearLogs();			// clear all data that has dated 3 months or above, scheduled everyday.

	}
	
	public void sendMonthlyBalance(){ // Sending all user emails about their usage for the past month
		ArrayList<String> users = userDAO.getAllUser();
		for (int i =0 ; i<users.size(); i ++){	
			if (logDAO.getPastMonth(users.get(i)) == null){
								// if user didnt use the service for the past month, no email is sent.
				
			}else{
				Mailings.sendBalance("Monthly balance",logDAO.getPastMonth(users.get(i)), users.get(i));
			
			}
	
		}
		
	}

	
	public int remainingDays(){//
		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		int LastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int x = LastDay - currentDay;
		return x;
		
	}

	

	//ROUTINES JOB END
	
}
