package nus.iss.Neko.Server.services;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Value("${email.from:}")
    private String fromEmail;

    @Value("${email.password:}")
    private String password;

    public void sendEmailUponSignUp(String toEmail) {
        Properties props =  new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMPT HOST
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("Content-Transfer-Encoding", "8-bit");
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setSubject("Welcome to Neko Kona!");
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSentDate(new Date());
            msg.setRecipients
                (Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            
            // create the body part
            BodyPart messageBodyPart = new MimeBodyPart();
            
            String body = getEmailMessage();

            messageBodyPart.setContent(body, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    private String getEmailMessage() {

        String message = """
            <head>
                <style type=\"text/css\">
                    .body {
                        display:-webkit-flex;
                        flex-direction: column;
                        text-align: center;
                    }

                    img {
                        width: 300px;
                    }
                </style>
            </head>
            <div class=\"body\">
                <h1> Welcome to Neko Kona </h1>
                <img src=\"https://images.unsplash.com/photo-1682213916353-279defcd3003?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1932&q=80\"/>
                <p> Lorem Ipsum </p>
            </div> 
        """;

        return message;
    }

}
