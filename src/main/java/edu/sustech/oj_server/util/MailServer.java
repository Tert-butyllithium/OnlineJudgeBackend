package edu.sustech.oj_server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServer {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("SUSTechCPC<${spring.mail.username}>")
    private String emailUser;

    public void sendEmail(String target,String subject,String text) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper msgHelper=new MimeMessageHelper(msg,true);
        System.out.println("sending email to: "+target);
        msgHelper.setFrom(emailUser);
        msgHelper.setTo(target);
        msgHelper.setSubject(subject);
        msgHelper.setText(text);
        javaMailSender.send(msg);
    }

    public void sendEmailWithAttachedFile(String target,String subject,String text,String filename,String filepath) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper msgHelper=new MimeMessageHelper(msg,true);
        System.out.println("sending email to: "+target);
        msgHelper.setFrom(emailUser);
        msgHelper.setTo(target);
        msgHelper.setSubject(subject);
        msgHelper.setText(text,true);

        FileSystemResource file = new FileSystemResource(new File(filepath));
        msgHelper.addAttachment(filename,file);

        javaMailSender.send(msg);
    }
}
