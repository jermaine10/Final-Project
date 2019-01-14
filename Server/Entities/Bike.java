package Entities;

import java.sql.Timestamp;

public class Bike{

	public int bikeID;
	public String status;
	public int uses;
	public Timestamp dateTimes;
	public String userID;
	public Bike(int i,String s, int u,String uid,Timestamp t) {
		this.bikeID = i;
		this.status = s;
		this.uses = u;
		this.userID = uid;
		this.dateTimes = t;
		
	}

}