package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RoutesDAO {
	Connection connection;
	public RoutesDAO(Connection connection){
		this.connection = connection;
		
	}
	
	public ArrayList<Integer> getPopularRoutes(){
		ArrayList<Integer> x = new ArrayList<Integer>();
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select ID FROM ROUTINGS ORDER BY USES");
			if(rs.next()){
				x.add(rs.getInt("ID"));
				while(rs.next()){
					x.add(rs.getInt("id"));
					
				}
				
			}else{
				x = null;
				
			}
			
			
		}catch(Exception e ){
			
		}
		return x;
		
	}
	
	
	public void increaseUsage(int fromstat,int tostat){
		try{
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT USES FROM ROUTINGS where FROMSTAT = " + fromstat +", TOSTAT =" + tostat);
			if (rs.next()){
				int i = rs.getInt("USES");
				i++;
				stmt.executeUpdate("UPDATE ROUTINGS SET USES = " + i + " WHERE FROMSTAT = "+  fromstat + " ,TOSTAT = "+ tostat);
			
			}
			
		}catch(Exception e){
			e.printStackTrace();
	
		}
		
	}
	

}
