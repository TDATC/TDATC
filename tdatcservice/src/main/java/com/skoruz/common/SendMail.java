package com.skoruz.common;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    public static boolean sendingMail(String email,String subject,String body) {
        

        String host = "smtp.gmail.com";
        String from = "projectsrun@gmail.com";
       // String from = "hanumanthat@skoruz.com";
        String pass = "krishna0608";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true"); // added this line
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        try {
            // added this line
            String[] to = new String[1];
            to[0] = email;
          

            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) { // changed from a while loop
                toAddress[i] = new InternetAddress(to[i]);
            }
     

            for (InternetAddress toAddres : toAddress) {
                // changed from a while loop
         
                message.addRecipient(Message.RecipientType.TO, toAddres);
            }
            message.setSubject(subject);

            message.setContent(body, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
          

            return true;

        } catch (MessagingException e) {
            System.out.println("Error occur from send mail class : "+e.getMessage());
            return false;

        }
    }
}
