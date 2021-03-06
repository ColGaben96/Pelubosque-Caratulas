package co.edu.unbosque.model;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *  * @author Gabriel Blanco
 * @version 1.0
 *
 */
public class Emailer {
	public static final String HELLO_MALE = "Bienvenido a Pelubosque";
	public static final String HELLO_FEMALE = "Bienvenida a Pelubosque";
	public static final String HELLO_TRANSFORMER = "Bienvenide a Pelubosque";
	
	/**
	 * Method to define the session details.<br>
	 * Preconditions: Javax Mail don't have any session defined. <br>
	 * Postconditions: Javax Mail have a session defined. <br>
	 * @author Gabriel Blanco
	 * @return
	 */
	public Session getSessionObject() {
		System.out.println("Getting Properties");
		Properties props=new Properties();  
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true"); 
		var client_id = "noresponder.pelubosque@gmail.com";
		var secret = "Pelubosque20202";
		Session session=Session.getDefaultInstance(props, new javax.mail.Authenticator() {  
		      protected PasswordAuthentication getPasswordAuthentication() {  
		    	    return new PasswordAuthentication(client_id,secret);  
		    	      }  
		    	    });  
		return session;
	}
	
	public void sendEmail(String emailTO, String subject, Object htmlContent) throws AddressException, MessagingException {
		MimeMessage message=new MimeMessage(getSessionObject());
		message.setFrom(new InternetAddress("Pelubosque"));
		System.out.println("Wait");
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTO));
		message.setSubject(subject);
		message.setContent(
	              htmlContent,
	             "text/html");
		Transport.send(message);
		System.out.println("Message sent");
	}
	
	public void sendEmailWithAttachments(String emailTO, String subject, Object htmlContent, File attachments) throws AddressException, MessagingException, IOException {
		MimeMessage message=new MimeMessage(getSessionObject());
		Multipart multipart = new MimeMultipart();
		BodyPart content = new MimeBodyPart();
		message.setFrom(new InternetAddress("Pelubosque"));
		System.out.println("Wait");
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTO));
		message.setSubject(subject);
		content.setContent(htmlContent, "text/html");
		multipart.addBodyPart(content);
		MimeBodyPart attachment = new MimeBodyPart();
		attachment.attachFile(attachments);
		multipart.addBodyPart(attachment);
		message.setContent(multipart);
		Transport.send(message);
		System.out.println("Message sent");
	}
}
