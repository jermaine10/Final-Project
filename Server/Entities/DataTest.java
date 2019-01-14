package Entities;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;

import DAO.*;
import Entities.User;
import Server.Control;
public class DataTest{
	public static void main(String[] args) {
		Connection connection = null;
		try{
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/" , "SA","");
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
			
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try{
			Control controls = new Control();
			String userInput = "";
			while(!userInput.equals("quit")){
		            System.out.print("Command > ");
		            userInput = in.readLine();
		
					switch (userInput){
						case "1": { // check if the userid and passwords are the same
							controls.sendBrokenBikeNotification();
								
						}
						break;
						
						case "2":{
							System.out.println("Registering new admin");
							System.out.println("Insert new admin id:");
							String y = in.readLine();
							System.out.println("Insert password:");
							String x = in.readLine();		
							System.out.println("Insert name:");
							String z = in.readLine();
							if(controls.registerAdmin(y, x, z)){
								System.out.println("success registering new admin");
								
							}else{
								System.out.println("registration failed");
							
							}
							
						}
						break;
						
						case "3":{
							System.out.println("Inserting bike into dock");
							System.out.println("Insert bike id");
							int x = Integer.parseInt(in.readLine());
							System.out.println("Insert dock id");
							int y = Integer.parseInt(in.readLine());
							if(controls.insertBike(y, x)){
								System.out.println("Success");
								
							}else{
								System.out.println("Fail");
								
							}
						}
						break;
						
						case "4":{
							System.out.println("Getting all station with broken bike");
							for(String y :controls.getAllStationsWithBrokenBike()){
								System.out.println(y);
							
							}
							
						}
						break;
						
						case "5":{
							System.out.println("Removing broken bike");
							System.out.println("Insert dock with broken bike");
							int x = Integer.parseInt(in.readLine());
							if(controls.removeBrokenBike(x)){
								System.out.println("success");
								
							}else{
								System.out.println("Fail");
								
							}
							
						}
						break;

					}
			
				}
			}catch(Exception e){
			
			
			
		}
		
	}
		
	public static String promptForValue(String question){
			System.out.print(question + " > ");
			return System.console().readLine();
			
	}
		
	
}
