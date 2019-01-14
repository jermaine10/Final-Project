package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Entities.Feedbacks;


public class FeedbacksDAO {
	
	private Connection connection;
	private HashMap<Integer, ArrayList<Feedbacks>> cache;
	
	public FeedbacksDAO(Connection c){
		this.connection = c;
		cache = new HashMap<>();
	}
	
	public void addFeedbacks(int routeid, String uid, String message, int rating){
		try{
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate("INSERT INTO FEEDBACKS (ROUTEID,USERID,MESSAGE,RATING) VALUES ("+routeid+ ",'" +uid + "','" + message + "' ," + rating+")");
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
	}
	
	public ArrayList<String> getOveralRatingonAllRoute(){
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<Integer> routeids = new ArrayList<Integer>();
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT ROUTEID FROM FEEDBACKS");
			if(rs.next()){
				int temproutes =rs.getInt("ROUTEID");
				routeids.add(temproutes);
				while(rs.next()){
					temproutes =rs.getInt("ROUTEID");
					routeids.add(temproutes);
				}
			
			}else{
				results = null;				
			}
			for (int i : routeids){
				rs = stmt.executeQuery("Select RATING From FEEDBACKS WHERE ROUTEID = " + i);
				
				if(rs.next()){
					double x = ((double)rs.getInt("RATING")/2);
					while (rs.next()){
						x = (x +  ((double)rs.getInt("RATING")/2))/2 ;
				
					}
					String result = i + " " + x;
					results.add(result);
			
				} 
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
	
		}
		return results;
		
	}
	
	public ArrayList<Feedbacks> getAllFeedbacksOnRoute(int routeid){
		ArrayList<Feedbacks> results = new ArrayList<Feedbacks>();
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM FEEDBACKS WHERE ROUTEID = "+routeid);
			if (rs.next()){
				String uid = rs.getString("USERID");
				String message = rs.getString("MESSAGE");
				Date dates = rs.getDate("DATES");
				double ratings = (double)rs.getInt("Rating");
				Feedbacks f = new Feedbacks(routeid,uid,message,dates,ratings);
				results.add(f);
				while(rs.next()){
					 uid = rs.getString("USERID");
					 message = rs.getString("MESSAGE");
					 dates = rs.getDate("DATES");
					 ratings = (double)rs.getInt("Rating");
					 f = new Feedbacks(routeid,uid,message,dates,ratings);
					 results.add(f);

				}
				
			}else{
				results = null;
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return results;
			
	}
	
	
}
