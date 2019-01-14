package Entities;

import java.util.Date;

public class Feedbacks {
	public int routeid;
	public String uid, message;
	public Date dates;
	public double ratings;
	public Feedbacks(int routeid, String uid, String message, Date dates,
			double ratings) {
		this.routeid = routeid;
		this.uid = uid;
		this.message = message;
		this.dates = dates;
		this.ratings = ratings;
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString(){
		String x = routeid + "-" + uid + "-" + message + "-" + dates +"-"+ratings;
		return x;
		
	}

}
