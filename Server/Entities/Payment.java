package Entities;

public class Payment {

		public static double countPayment(long t){
			double balance;
			if (t < 30){
				balance = 0.20;			
			}
		
			else {
				 balance = Math.ceil((t - 30 )/30) * 2;
					
			}
			
		return balance;	
		}
}
