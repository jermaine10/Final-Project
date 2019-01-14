package Server;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class Mailings {
	public static void sendNotification(String subjects,ArrayList<String> bodies, ArrayList<String> to){
		  String from = "web@gmail.com";
	      String host = "localhost";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      Session session = Session.getDefaultInstance(properties);
	      try{
		     String bodiesmsg = "";
		     String newline =System.getProperty("line.separator"); 

	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[to.size()];
	         for (int i = 0; i < to.size(); i++){
	              addressTo[i] = new javax.mail.internet.InternetAddress(to.get(i));

	         }
	         message.addRecipients(Message.RecipientType.TO,addressTo);
	         // Set Subject: header field
	         message.setSubject(subjects);

	         // Now set the actual message
	         for (int i = 0; i<bodies.size();i++){
	        	 bodiesmsg = bodies.get(i) + newline;
	        	 System.out.println(bodiesmsg);
		 
	         }	
	         
	         
	         message.setText(bodiesmsg);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	
	      }
	   
	}
	


	public static void sendBalance(String subjects,ArrayList<String> bodies, String uid){
		  String from = "web@gmail.com";
	      String host = "localhost";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      Session session = Session.getDefaultInstance(properties);
	      try{
		     String bodiesmsg = "";
		     String newline =System.getProperty("line.separator"); 

	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         System.out.println(uid);
	         message.addRecipients(Message.RecipientType.TO,uid);
	         // Set Subject: header field
	         message.setSubject(subjects);

	         // Now set the actual message\
	         bodiesmsg = "The total usage so far is" + newline+ newline; 
	         for (int i = 0; i<bodies.size();i++){
	        	 bodiesmsg = bodies.get(i) + newline;
	        	 System.out.println(bodiesmsg);
	        			 
	         }	
	         	System.out.println("bodiesmsg end");
	         
	         message.setText(bodiesmsg);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	
	      }
	   
	}
	public static void sendMailWithAttachments(String subjects,String bodies,ArrayList<String>to,String filename){

	      String from = "web@gmail.com";
	      String host = "localhost";
	      Properties properties = System.getProperties();
	      properties.setProperty("mail.smtp.host", host);
	      Session session = Session.getDefaultInstance(properties);
	    
	      
	      try{
	          // Create a default MimeMessage object.
	          MimeMessage message = new MimeMessage(session);

	          // Set From: header field of the header.
	          message.setFrom(new InternetAddress(from));

	          javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[to.size()];

	          for (int i = 0; i < to.size(); i++)
	          {
	              addressTo[i] = new javax.mail.internet.InternetAddress(to.get(i));
	          }
	          // Set To: header field of the header.
	          message.addRecipients(Message.RecipientType.BCC,addressTo);

	          // Set Subject: header field
	          message.setSubject(subjects);

	          // Create the message part 
	          BodyPart messageBodyPart = new MimeBodyPart();

	          // Fill the message
	          messageBodyPart.setText(bodies);
	          
	          // Create a multipar message
	          Multipart multipart = new MimeMultipart();

	          // Set text message part
	          multipart.addBodyPart(messageBodyPart);

	          // Part two is attachment
	          messageBodyPart = new MimeBodyPart();
	          DataSource source = new FileDataSource(filename);
	          messageBodyPart.setDataHandler(new DataHandler(source));
	          messageBodyPart.setFileName(filename);
	          multipart.addBodyPart(messageBodyPart);

	          // Send the complete message parts
	          message.setContent(multipart);

	          // Send message
	          Transport.send(message);
	          System.out.println("Sent message successfully....");
	       }catch (MessagingException e) {
	          e.printStackTrace();
	          
	          }
	      
	   }
}
