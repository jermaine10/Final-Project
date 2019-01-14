package DAO;

import Entities.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class StationDAO {
	
	private Connection connection;
	private HashMap<Integer, Station> cache;
	
	public StationDAO(Connection c){
		this.connection = c;
		cache = new HashMap<>();
		
	}
	
	public String getStationNameById(int id){
		String result = null;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM STATION WHERE ID = '" + id + "'");
			if(rs.next()){
				String n = rs.getString("NAME");
				result = n;
			
			}else{
				System.out.println("Invalid station");
				
			}
			
		}catch(Exception e ){
			e.printStackTrace();
			
		}
		return result;		
		
	}
	
	public ArrayList<String> getAllStations(){
		ArrayList<String> results = new ArrayList<String>();
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID , NAME FROM STATION");
			if(rs.next()){
				String x = rs.getString("ID") + "-" + rs.getString("Name");
				results.add(x);
				while(rs.next()){
					x = rs.getString("ID") + "-" + rs.getString("Name");
					results.add(x);
				
				}

			}
			
		}catch(Exception e){
			System.out.println("Some unforseen error occured in the database;");
		
		}
		return results;
	
	}
	
	
	public BigDecimal[] getLatLong(int id){
		BigDecimal[] x = new BigDecimal[2];
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT LONGITUDE,LATITUDE FROM STATION WHERE ID = '" + id + "'");
			if(rs.next()){
				System.out.println("test");
				BigDecimal y = rs.getBigDecimal("LONGITUDE");
				System.out.println(y);
				BigDecimal z = rs.getBigDecimal("LATITUDE");
				System.out.println(z);
				x[0] = y;
				x[1] = z;
				
			}else{ 
				x= null;
				
			}
			
		}catch(Exception e){
			System.out.println("something went wrong");
			e.printStackTrace();
		
		}
		return x;
		
	}
	
}
	