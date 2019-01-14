/**
 * 
 */
package JUnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Entities.Feedbacks;
import Exceptions.notABrokenBikeException;
import Exceptions.userAlreadyExistException;
import Exceptions.userHasRentedAbikeException;
import Server.Control;

/**
 * @author jermin
 *
 */
public class ControlFunctionTest {
	private Control controls;
	private int sid1; // valid values for station id
	private int bid1; // bike with broken status inside a dock
	private int bid2; // bike currently rented
	private int bid3; // bike with broken status NOT IN any dock
	private int bid4; // bike for report broken bike test
	private String uid1; // username for login test
	private String uid2; // username for bike renting test 
	private String uid3; // used to test register admin
	private String uid4; // used to test register user
	private String name; // string for name change
	private String pwd1; // valid password for uid1
	private String pwd2; // wrong password for uid1
	private String pwd3; // for password changing test
	private int did1;	//  dock id with available status(FOR INSERT RENTED BIKE)
	private int did2;	//  dock id with unavailable status 
	private int did3;	//  dock id with broken status
	private int did4;	//  dock id with available status (FOR INSERT BROKEN BIKE)
	
	
	@Before
	public void setUp() throws Exception{
		connectionTest();
		 sid1 = 1;
		 bid1 = 5;
		 bid2 = 15;
		 bid3 = 13;
		 bid4 = 1;
		 uid1 = "j1076@student.le.ac.uk";
		 uid2 = "jermaine9310@gmail.com";
		 uid3 = "newadmin@test.com";
		 uid4 = "newuser@test.com";
		 name = "testNameChange";
		 pwd1 = "testpass";
		 pwd2 = "invalidpass";
		 pwd3 = "pwdchanged";
		 did1 = 15;
		 did2 = 1;
		 did3 = 5;
		 did4 = 13;
		 
	}
	
	public void connectionTest(){
		controls = new Control(); // conncecting to database exception will pop out if database hasnt been properly initialized
		
	}
	
	@Test
	public void addFeedbacks(){
		System.out.println("Testing adding feedbacks");
		controls.addFeedbacks(1, uid1, "testtest", 10);
		System.out.println("======= END OF TEST ======");
		
	}
	
	@Test
	public void testGetAllFeedbacksOnRoute(){
		System.out.println("Testing getting all feedbacks on route");
		assertNotNull(controls.getAllFeedBacksonRoute(1));
		for(Feedbacks f : controls.getAllFeedBacksonRoute(1)){
			System.out.println(f);
			
		}
		
		System.out.println("======= END OF TEST ======");		
	}
	
	@Test
	public void testGetOverallRatingonAllRoute(){
		System.out.println("Testing getting overall rating on every route");
		assertNotNull(controls.getOveralRatingonAllRoute());
		for(String x : controls.getOveralRatingonAllRoute()){
			System.out.println(x);
			
		}
		System.out.println("======= END OF TEST ======");		
	}
	
	@Test
	public void testGetAllDockFromStation(){
		try{
			System.out.println("Testing getting dock from station");
			assertNotNull(controls.getAllDockFromStation(sid1));
			for(String s :controls.getAllDockFromStation(sid1)){	
				System.out.println(s);
				
			}
			System.out.println("======= END OF TEST ======");
			
		}catch(Exception e){
			e.printStackTrace();
		
		}
		
	}

	

	@Test
	public void testGetAllStations() {
		try{
			System.out.println("Testing getting all stations name and id");
			assertNotNull(controls.getAllStations());
			for(String x : controls.getAllStations()){
				System.out.println(x);
		
			}
			System.out.println("======= END OF TEST ======");

		}catch (Exception e) {
			e.printStackTrace();
		
		}

	}
	
	@Test
	public void testGetAllStationsWithBrokenBike() {
		System.out.println("Testing getting all stations with broken bike");
		assertNotNull(controls.getAllStationsWithBrokenBike());
		for(String x  : controls.getAllStationsWithBrokenBike()){
			System.out.println(x);
		
		}
		System.out.println("======= END OF TEST ======");
		
	}
	
	@Test
	public void testRemoveBrokenBike(){
		try{
			System.out.println("Testing removing broken bike ");
			boolean x = controls.removeBrokenBike(bid1);
			System.out.println("Result is "+ x);
			assertTrue(x);
			System.out.println("======= END OF TEST ======");
	
		}catch(notABrokenBikeException e){
			System.out.println("The bike to be removed is not a broken bike");
		}

	}

	
	@Test
	public void testGetLogin() {
		try {
			System.out.println("Testing login");
			assertNotNull(controls.getLogin(uid1, pwd1));
			System.out.println(controls.getLogin(uid1, pwd1));
			System.out.println("======= END OF TEST ======");

		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}
	
	@Test
	public void testGetLongitudeLatitude() {
		try {
			System.out.println("Testing get Longitude and latitude");
			assertNotNull(controls.getLongitudeLatitude(sid1));
			System.out.println(controls.getLongitudeLatitude(sid1));
			System.out.println("======= END OF TEST ======");

		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}
	
	@Test
	public void getMostUsedRoutes(){
		try{
			System.out.println("Testing Most used routes");
			assertNotNull(controls.getMostUsedRoutes());
			for (int i : controls.getMostUsedRoutes()){
				System.out.println(i);
				
			}
			System.out.println("======= END OF TEST ======");
			
		}catch(Exception e){
			e.printStackTrace();
			
		}

	}

	@Test
	public void testGetNumberOfAvailableDockInStation() {
		try {
			System.out.println("Testing Getting available docks in station");
			assertNotSame(-1, controls.getNumberOfAvailableDockInStation(sid1));
			System.out.println(controls.getNumberOfAvailableDockInStation(sid1));
			System.out.println("======= END OF TEST ======");

		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}
	
	@Test
	public void testMaintainBike(){
		try{
			System.out.println("Testing Maintain Bike");
			controls.maintainBike();
			System.out.println("======= END OF TEST ======");
	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	

	@Test
	public void testInsertBike() {
		try {
			System.out.println("Testing inserting bike");
			assertTrue(controls.insertBike(15, 15));
			System.out.println("======= END OF TEST ======");

		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}

	@Test
	public void testRegisterAdmin(){
		try{
			System.out.println("Testing register admin");
			boolean x = controls.registerAdmin(uid3, pwd1, name);
			assertTrue(x);
			System.out.println("Result is "+ x);
			System.out.println("======= END OF TEST ======");

		}catch(userAlreadyExistException e){
			System.out.println("userid existed in the database");
		
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	@Test
	public void testRegisterUser(){
		try{
			System.out.println("Testing register user");
			boolean x = controls.registerUser(uid4, pwd1, name, false);
			assertTrue(x);
			System.out.println("Result is "+ x);
			System.out.println("======= END OF TEST ======");

			
		}catch(userAlreadyExistException e){
			System.out.println("userid existed in the database");
		
		}catch(Exception e){
			e.printStackTrace();
		
		}
		
	}
	
	
	
	
	
	@Test
	public void testRemoveBike() {
		try{
			System.out.println("Testing remove bike");
			boolean x = controls.removeBike(uid2,did1);
			assertTrue(x);
			if (x = true){
				System.out.println("successfully removed bike");			// NOTE EACH USER CAN ONLY RENT 1 BIKE 

			}
			System.out.println("======= END OF TEST ======");
			
		}catch(userHasRentedAbikeException e){
			System.out.println("Only 1 bike can be rented by each user");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	@Test
	public void testReportBrokenBike(){
		try{
			System.out.println("Testing reporting broken bike");
			boolean x = controls.reportBrokenBike(bid4);
			assertTrue(x);
			System.out.println("Result is " + x);
			System.out.println("======= END OF TEST ======");
			
		}catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}
	

	@Test
	public void testInsertBrokenBike(){
		try{
			System.out.println("Testing inserting broken bike");
			boolean x = controls.insertBike(did4, bid3);
			assertTrue(x);
			System.out.println("Result is " + x);
			System.out.println("======= END OF TEST ======");
			
		}catch (Exception e) {
			e.printStackTrace();
	
		}
		
	}
	
	/*
	 *  seems to be working in linux but due to some reason mail is not sent when using lab computer
	 *  doesnt work in windows 7/8 since it no longer support smtp server on localhost.
	 */
	@Test
	public void testSendBrokenBikeNotification(){
		try{
			System.out.println("Testing send notification");
			controls.sendBrokenBikeNotification(); 
			System.out.println("======= END OF TEST ======");

		}catch(Exception e){
			e.printStackTrace();
		
		}
	
	}
	@Test
	public void testSendMonthlyBalance(){
		try{
			System.out.println("Testing send monthly balance");
			controls.sendMonthlyBalance();
			System.out.println("====== END OF TEST ======");
			
		}catch(Exception e){	
			e.printStackTrace();
			
		}
	
	}	
		

	
	@Test
	public void testUpdateUname() {
		try {
			System.out.println("Testing updating name");
			boolean x = controls.updateUname(uid1, name);
			assertTrue(x);
			System.out.println("Result is "+ x);
			System.out.println("======= END OF TEST ======");

		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}

	@Test
	public void testUpdateUPassword() {
		try {
			System.out.println("Testing updating password"); // Should fail on second runtime;
			boolean x = controls.updateUpassword(uid2, pwd1, pwd3);
			assertTrue(x);
			System.out.println("Result is "+ x);
			System.out.println("======= END OF TEST ======");

		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}

	
	@Test(expected =Exception.class)
	public void testWrongLogin(){
		try {
			System.out.println("test wrong login");
			assertSame("fail",(controls.getLogin(uid1, pwd2)));
		
		} catch (Exception e) {
			e.printStackTrace();
		
		}	
		
	}
	

}
