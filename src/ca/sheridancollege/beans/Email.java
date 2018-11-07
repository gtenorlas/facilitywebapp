package ca.sheridancollege.beans;

import java.awt.Color;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * @author MAGS
 *
 */
public class Email {

	/**
	 * work with emails
	 */
    private String receiver;
    private String subject;
    private String message;
    private final String host = "mags.website";
    private final String user = "support@mags.website";//change accordingly  
    private final String password = "Book2ball!";//change accordingly  

    public Email(String receiver, String subject, String message) {
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public static String addColor(String msg, Color color) {
        String hexColor = String.format("#%06X",  (0xFFFFFF & color.getRGB()));
        String colorMsg = "<FONT COLOR=\"#" + hexColor + "\">" + msg + "</FONT>";
        return colorMsg;
    }

    /**
     * send email message
     */
    public void send() {
        //Get the session object  
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.domain.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        //Compose the message  
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.receiver));
            message.setSubject(this.subject);
            
            MimeMultipart mp = new MimeMultipart();
            MimeBodyPart mbp1= new MimeBodyPart(); 
          //  String htmlText = "<b> This is formatted</b>"+
           // "<font size =\"5\" face=\"arial\" >This paragraph is in Arial, size 5</font>";
            mbp1.setContent(this.message,"text/html");
            mp.addBodyPart(mbp1);
            //msg.setContent(mp);
            
            
            
            
            
            
            //message.setText(this.message);
            message.setContent(mp);

            //send the message  
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
