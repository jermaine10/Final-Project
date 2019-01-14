package Entities;

public class User{
	private String userID;
	private String password;
	private String name;
	private boolean subscription;
	private double balance;
	private boolean admin;
	
	public User(String uID, String n,String pwd,  boolean s,double b, boolean ad){
		this.userID = uID;
		this.password = pwd;
		this.name = n;
		this.subscription = s;
		this.balance = b;
		this.admin = ad;
	}
	@Override
	public String toString(){
		String x = userID + "-" + password + "-" + name + "-" + subscription + "-" + balance;
		return x;
	}
}