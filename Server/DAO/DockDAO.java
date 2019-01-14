package DAO;

import Entities.*;
import Exceptions.notABrokenBikeException;
import Exceptions.userHasRentedAbikeException;

import java.sql.*;
import java.util.*;

import org.hsqldb.auth.JaasAuthBean.UPCallbackHandler;

public class DockDAO {
	
	private Connection connection;
	private HashMap<Integer, User> cache;
	public StationDAO stationDAO;
	public LogDAO logDAO;
	public BikeDAO bikeDAO;
	public UserDAO userDAO;

	public DockDAO(Connection c){
		this.connection = c;
		cache = new HashMap<>();
		
	}
	
	public ArrayList<String> getDockFromStation(int x){
        ArrayList<String> docks = new ArrayList<String>();

		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs =  stmt.executeQuery("SELECT ID,STATUS FROM DOCK WHERE STATIONID = " + x );
				if(rs.next()){
					int id = rs.getInt("ID");
                	String s = rs.getString("STATUS");
                	Dock d = new Dock(id,s);
                	String result = id +"-"+ s;
                	docks.add(result);
					while (rs.next()) {
						id = rs.getInt("ID");
	                	s = rs.getString("STATUS");
	                	d = new Dock(id,s);
	                	result = id +"-"+ s;
	                	docks.add(result);
	                	
	            	}
					
				}else{
					System.out.println("Invalid dockid");
					docks= null;
					
				}
			
		}catch(SQLException e ){
			System.out.println("invalid station id");
			e.printStackTrace();
			
		}
		return docks;
		
	}

	public int getNumberOfAvailableDock(int sid) {
		int x = 0;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs =  stmt.executeQuery("SELECT * FROM DOCK WHERE STATIONID = " + x + " AND STATUS = 'available'" );
			if(rs.next()){
				x++;
				while (rs.next()) {
	            	x++;
	            	
	            }
				
			}else{
				System.out.println("invalid station id");
				x = -1;
				
			}
			
		}catch(Exception e ){
			e.printStackTrace();
			
		}
		return x;
	
	}

	public ArrayList<String> findBrokenDOCK() {
		ArrayList<String> results = new ArrayList<String>();
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs =  stmt.executeQuery("select DOCK.STATIONID,DOCK.ID from DOCK Inner JOIN BIKE ON BIKE.ID = DOCK.BIKEID WHERE BIKE.STATUS = 'broken'");
	           if (rs.next()){
	        	   String y = "Dock and bike that require maintanance : ";
	        	   results.add(y);
	           		int x = rs.getInt("DOCK.STATIONID");
	           		 y = stationDAO.getStationNameById(x);
	           		String did = Integer.toString(rs.getInt("DOCK.ID"));
	           		String result = y +" dock number : " + did;
	           		results.add(result);
	           		while (rs.next()) {
		            	 x = rs.getInt("DOCK.STATIONID");
		            	 y = stationDAO.getStationNameById(x);
		            	 did = Integer.toString(rs.getInt("DOCK.ID"));
		            	 result = y + " dock number : " + did;
		            	 results.add(result);
		            	 
	           			}
	           		
	           }else{
	        	   String y = "no broken docks on all station";
	        	   results.add(y);
	        	   
	           }
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return results;
		
	}
	
	public boolean insertBike(int dockid,int bikeid){
		boolean result = false;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT STATUS FROM DOCK WHERE ID = " + dockid);	
			if(rs.next()){
				if (rs.getString("STATUS").equalsIgnoreCase("available")){
					rs = stmt.executeQuery("select BIKE.USES from DOCK Inner JOIN BIKE ON BIKE.ID = DOCK.BIKEID WHERE BIKE.ID =" + bikeid);
						if(rs.next()){
							System.out.println("invalid bike id , it exist in other dock");
							result = false;
							
						}else {
							Bike d = bikeDAO.getBikeByID(bikeid);
							if(d!= null){
								if (!(d.status.equalsIgnoreCase("broken"))){
									System.out.println(d.status);
									String uid = d.userID;
									java.sql.Timestamp  sqlDate = d.dateTimes;
									java.sql.Timestamp  currentTime =  new java.sql.Timestamp(new java.util.Date().getTime());
									float x = userDAO.getBalance(uid);
									if(x!=(-999)){
										float currBalance = x;
										long diff= currentTime.getTime() - sqlDate.getTime();
										long diffMinutes = diff / (60 * 1000);
										float newBalance = (float) (Payment.countPayment(diffMinutes) + currBalance);
										userDAO.updateNewBalance(newBalance,uid);
										logDAO.saveLogs(uid, diffMinutes);
										stmt.executeUpdate("update dock set bikeid ="+  bikeid +", status = 'unavailable' where id =" + dockid);
										bikeDAO.resetBikeStats(bikeid,(d.uses + 1));
										result = true;
										
									}else{
										System.out.println("User is not in the database/ hasnt rented a bike");
										result = false;
										
									}
									
								}else {
									stmt.executeUpdate("update dock set bikeid ="+  bikeid +", status = 'unavailable' where id =" + dockid);
									bikeDAO.restoreBike(bikeid);
									result = true;
									
								}
								
							}else{
								System.out.println("bike id doesnt exist");
								result = false;
								
							}

						}
						
				}else{
					System.out.println("dock has a bike");
					result = false;
					
				}

			}else{
				System.out.println("dock doesnt exist");
				result = false;
				
			}
		
		}catch(Exception e){
			e.printStackTrace();
			result = false;
			
		}
		return result;

	}
	
	public boolean reportBrokenBike(int bikeid){
		boolean result = false;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID FROM DOCK WHERE BIKEID =  " + bikeid );
			if(rs.next()){
				bikeDAO.setBrokenBike(bikeid);
				int i = rs.getInt("ID");
				stmt.executeUpdate("UPDATE DOCK SET STATUS = 'broken' where id = " +i );
				result = true;
				
			}else{
				System.out.println("bike doesnt exist in any dock");
				result = false;
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return result;
	
	}
	

	public boolean removeBike(String userid, int dockid) throws userHasRentedAbikeException {
		boolean result;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT STATUS,BIKEID FROM DOCK WHERE ID = " + dockid);
			
			if(rs.next()){
				int bid = rs.getInt("BIKEID");
				if (rs.getString("STATUS").equalsIgnoreCase("available")){
					System.out.println("dock is empty cannot remove bike");
					result = false;
		
				}
				
				else{
					boolean x = bikeDAO.findBike(userid);
					if (x == true){
						System.out.println("user already had rented a bike");
						throw new userHasRentedAbikeException();
						
					}else{
						bikeDAO.updateBikeStatus(userid,bid); // set the bike status so that it records time
						stmt.executeUpdate("update dock set bikeid = null,status = 'available' where id =" + dockid);
						System.out.println("succesfully removed dock from bike");
						result = true;
						
					}
					
				}
				
			}else{
				System.out.println("dock doesnt exist");
				result = false;
			
			}
			
		}catch(SQLException e){
			System.out.println("cannot remove bike.");
			e.printStackTrace();
			result = false;
			
		}
		return result;
		
	}
	
	public boolean removeBrokenBike(int dockid) throws notABrokenBikeException{
		boolean b = false;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select STATUS,BIKEID FROM DOCK WHERE ID = " +dockid);
			if (rs.next()){
				if (rs.getString("STATUS").equalsIgnoreCase("broken")){
					System.out.println("Test");
					stmt.executeUpdate("UPDATE DOCK SET BIKEID = NULL,STATUS = 'available' WHERE ID =" + dockid );
					b = true;
				}else{
					throw new notABrokenBikeException();
					
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return b;
	
	}

	public void maintainBike() {
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DOCK.ID , BIKEID from DOCK Inner "
					+ "JOIN BIKE ON BIKE.ID = DOCK.BIKEID WHERE BIKE.USES > " + 50);
			if(rs.next()){
				reportBrokenBike(rs.getInt("BIKEID"));
				while(rs.next()){
					reportBrokenBike(rs.getInt("BIKEID"));

				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
	
		}
		
	}
	

}
