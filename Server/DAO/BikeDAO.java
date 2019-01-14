package DAO;

import java.sql.*;
import java.util.*;

import Entities.*;

public class BikeDAO {
	
	private Connection connection;
	private HashMap<Integer, Bike> cache;
	
	public BikeDAO(Connection c){
		this.connection = c;
		cache = new HashMap<>();
	}
	
	public Bike getBikeByID(int id){
		Bike result = null;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs =  stmt.executeQuery("SELECT * FROM BIKE WHERE ID = " + id );
			if(rs.next()){
				int i = id;
				String s = rs.getString("STATUS");
				int u = rs.getInt("USES");
				Timestamp t = rs.getTimestamp("DATETIMES");
				String uid = rs.getString("USERID");
				Bike b = new Bike(i, s, u,uid,t);
				result = b;
			
			}else{
				System.out.println("invalid bike id");
				
			}
			
		}catch(Exception e ){
			e.printStackTrace();
			
		}
		return result;
		
	}
	
	
	public boolean restoreBike(int bid){
		boolean result;
		try{
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate("UPDATE BIKE SET STATUS = 'good',USES = " + 0 + " WHERE ID = " + bid);
			result = true;
			
		}catch(Exception e){
			e.printStackTrace();
			result = false;
			
		}
		return result;
		
	}

	public void setBrokenBike(int bikeid) {
		try{
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate("UPDATE BIKE SET STATUS = 'broken' where id = " +bikeid);

		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}

	public boolean findBike(String userid) {
		boolean x = false;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT USERID FROM BIKE WHERE USERID = '" + userid + "'");
			if(rs.next()){
				x = true;
			
			}else{
				x = false;
				
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return x;
		
	}

	public void updateBikeStatus(String userid,int bid) {
		try{
			java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
			String query = "update BIKE set DATETIMES = ?, USERID = ? where ID = ? ";
			PreparedStatement pstmt = this.connection.prepareStatement(query); // create a statement
			pstmt.setTimestamp(1, sqlDate);
			pstmt.setString(2, userid); // set input parameter 2
			pstmt.setInt(3, bid);
			pstmt.executeUpdate(); // execute update statement		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void resetBikeStats(int bikeid,int uses) {
		try{
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate("UPDATE BIKE SET USERID = NULL , DATETIMES = NULL, USES = " + uses + " WHERE ID = " + bikeid );
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		// TODO Auto-generated method stub
		
	}

}
