package Entities;

public class Dock{

	private int stationID;
	private String status;
	private Bike bike;	

	public Dock(int sid,String s) {
		this.stationID = sid;
		status = s;

	}
}