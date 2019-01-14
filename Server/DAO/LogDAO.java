package DAO;

import java.sql.*;
import java.util.ArrayList;

import Entities.Payment;

public class LogDAO {
	private Connection connection;

	public LogDAO(Connection c){
		this.connection = c;
	}
	
	@SuppressWarnings("finally")
	public boolean saveLogs(String uid, long time){
	
		boolean result = false;
		
		try{
			float cost = (float) Payment.countPayment(time);
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate("Insert into Logs (USERID,TIMETAKEN,COST) VALUES ('" + uid +"'," + time + "," + cost + ")");
			result = true;			
			
		}catch(Exception e){
			result = false;
			
		}finally{
			return result;
			
		}
		
	}
	
	public ArrayList<String> getPastMonth(String uid){
	
		ArrayList<String> result = new ArrayList<String>();
		try{
			
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from LOGS WHERE USERID = '" + uid +"'AND DATES > DATE_SUB(NOW(), INTERVAL 1 MONTH);" );
			if (rs.next()){
				float f = rs.getFloat("COST");
				Date d = rs.getDate("DATES");
				int t = rs.getInt("TIMETAKEN");
				String y ="time taken = " + t  + " minutes at " + d + " cost = " + f + " $ ";
				result.add(y);
				while(rs.next()){
					f = rs.getFloat("COST");
					d = rs.getDate("DATES");
					t = rs.getInt("TIMETAKEN");
					y ="time taken = " + t  + " minutes at " + d + " cost = " + f + " $ ";
					result.add(y);			
					
				}
			}else{
				result = null;
			
			}
		
		}catch(Exception e){	
			e.printStackTrace();
		
		}
		
		return result;
	
	}

	public void clearLogs() {
		try{
		Statement stmt = this.connection.createStatement();
		stmt.executeUpdate("DELETE FROM LOGS WHERE DATES < DATE_SUB(NOW(), INTERVAL 3 MONTH)");
		// TODO Auto-generated method stub
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
