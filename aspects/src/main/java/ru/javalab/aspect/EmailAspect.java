package ru.javalab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Aspect
@Component
@EnableAspectJAutoProxy
public class EmailAspect {
    @Autowired
    private Properties properties;

    @AfterReturning(pointcut = "execution(* ru.javalab.services.FileService.upload(..))", returning = "filename")
    public void sendEmail(JoinPoint joinPoint, String filename){
        System.out.println(joinPoint);
        System.out.println("email: " + joinPoint.getArgs()[2]);
        System.out.println("name: " + filename);

        MimeMessage message = new MimeMessage(createSession());
        try {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(joinPoint.getArgs()[2].toString()));
            message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));
            String html = "You upload new file " + filename +
                    "<br/> <a href=\"http://localhost:8080/file/"+ filename +"\">link</a>";
            message.setContent(html, "text/html");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public Session createSession() {
        return Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
                        properties.getProperty("mail.smtp.password"));
            }
        });
    }

}
