package edu.sustech.oj_server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServer {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("LanranForces<${spring.mail.username}>")
    private String emailUser;

    public void sendEmail(String target,String subject,String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        System.out.println("sending email to: "+target);
        msg.setFrom(emailUser);
        msg.setTo(target);
        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);
    }
}
