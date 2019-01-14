package DAO;

import java.sql.*;
import java.util.*;

import Entities.*;
import Exceptions.userAlreadyExistException;

public class UserDAO{
	
	private Connection connection;
//	private HashMap<Integer, User> cache;
	
	public UserDAO(Connection c){
		this.connection = c;
	}
	
	public User getLogin(String username, String password){
		User result = null;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE ID = '" + username +"'" +"AND PASSWORD = '" +password +"'");
			if(rs.next()){
				String i = rs.getString("ID");
				String n = rs.getString("NAME");
				String p = rs.getString("PASSWORD");
				boolean bs = rs.getBoolean("SUBSCRIPTION");
				double db = rs.getDouble("BALANCE");
				boolean ba = rs.getBoolean("ADMIN"); 
				User u = new User(i ,n ,p ,bs, db, ba );
				result = u;

			}else{
				System.out.println("invalid username or password");
				result = null;
			}
			
		}catch(Exception e ){
			e.printStackTrace();
			
		}
		return result;
		
	}

	public boolean updateName(String uid, String nname) {
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ID FROM USER WHERE ID = '" +uid + "'");
			if (rs.next()){
				stmt.executeUpdate("UPDATE USER SET NAME = '" + nname + "' WHERE  ID = '" + uid + "'");
				return true;
			
			}else{
				return false;
				
			}
			
		}catch(SQLException e){
            System.out.println(e.getMessage());     
            e.printStackTrace();
            return false;
            
		}
		
	}

	public boolean updatePassword(String uid, String opw, String npw) {
		boolean result;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE ID = '" + uid +"'" +"AND PASSWORD = '" +opw +"'");
			if(rs.next()){
				stmt.executeUpdate("Update USER SET PASSWORD = '" + npw + "' WHERE  ID = '" + uid + "'");
				result = true;
			}else{
				System.out.println("Old password doesnt match");
				result = false;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			result = false;
			
		}
		return result;
	
	}
	
	public boolean registerUser(String uid, String pwd, String name,boolean subs ) throws userAlreadyExistException{
		boolean result = false;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select ID FROM USER WHERE ID ='" + uid +"'" );
			if(rs.next()){
				result = false;
				System.out.println("ID HAS ALREADY EXISTED");
				throw new userAlreadyExistException();
				
			}else{
				stmt.executeUpdate("INSERT INTO USER (ID,NAME,PASSWORD,SUBSCRIPTION) VALUES ('" + uid + "','" + name +"','" + pwd +"',"+ subs+ ")");
				result = true;
				
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return result;
		
	}
	
	public boolean registerAdmin(String uid, String pwd, String name)throws userAlreadyExistException{
		boolean result = false;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select ID FROM USER WHERE ID ='" + uid +"'" );
			if(rs.next()){
				result = false;
				System.out.println("ID HAS ALREADY EXISTED");
				throw new userAlreadyExistException();

				
			}else{
				stmt.executeUpdate("INSERT INTO USER (ID,NAME,PASSWORD,ADMIN) VALUES ('" + uid + "','" + name +"','" + pwd +"',"+ true+ ")");
				result = true;
				
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return result;
		
	}
	
	
	public ArrayList<String> getAllAdmin(){ // for maintanance and stuff
		ArrayList<String> result = new ArrayList<String>();
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select ID from USER WHERE ADMIN = " + true);
			if(rs.next()){
				result.add(rs.getString("ID"));
				while (rs.next()){
					result.add(rs.getString("ID"));
					
				}		
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return result;
		
	}
	
	
	public ArrayList<String> getAllSubscriber(){ // for newsletter
		ArrayList<String> result = new ArrayList<String>();
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select ID from USER WHERE SUBSCRIPTION = " + true);
			if(rs.next()){
				result.add(rs.getString("ID"));
				while (rs.next()){
					result.add(rs.getString("ID"));
					
				}	
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return result;
		
	}
	
	public ArrayList<String> getAllUser(){ // for monthly usage and balance;
		ArrayList<String> result = new ArrayList<String>();
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select ID from USER");
			if(rs.next()){
				result.add(rs.getString("ID"));
				while (rs.next()){
					result.add(rs.getString("ID"));
				}		
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}

	public float getBalance(String uid) {
		float x = 0;
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT BALANCE FROM USER WHERE ID = '" + uid + "'");
			if(rs.next()){
				x = rs.getFloat("BALANCE");
				
			}else{
				x = -999; // error code
				
			}
			
		}catch(Exception e){
			e.printStackTrace();

		}

		return x;
	}

	public void updateNewBalance(float newBalance, String uid) {
		try{
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate("UPDATE USER SET BALANCE = "+newBalance + "where id = '" + uid +"'");	

		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
	}
 	
	public void makePayment(String uid,float x){
		try{
			Statement stmt = this.connection.createStatement();
			float oldBalance = getBalance(uid);
			float newBalance = oldBalance - x;
			stmt.executeUpdate("UPDATE USER SET BALANCE = " + newBalance +" WHERE ID = '" +uid + "'");
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	
}
