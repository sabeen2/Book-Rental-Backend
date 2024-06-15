package com.example.bookrental.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RequiredArgsConstructor
@Component
public class MailUtils {
    private final JavaMailSender javaMailSender;
    public static String setTemplet(String to,String subject){
        String template ="Subject-:"+subject+"\n\n"
                +"Hello, "+to+"\n\n"
                + "This is a message just for you to you about the expiry date of ypur renting service, "+to+". ";

        return template;
    }

    public static String resetTemplet(String to,String subject,String token){
        String template ="Subject-:"+subject+"\n\n"
                +"Hello, "+to+"\n\n"
                + "your reset token is, "+token+". ";

        return template;
    }

    public String sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        return "mail sent";
    }


    public void sendHtmlEmail() throws MessagingException, IOException {
        MimeMessage message=javaMailSender.createMimeMessage();
        message.setRecipients(MimeMessage.RecipientType.TO,"bibek@yopmail.com");

        String htmlTemplete=readFile("D:/hobs/templete/index.html");
        String htmlContent=htmlTemplete.replace("${name}","bibek");
        htmlContent.replace("${message}","this is message");
        message.setContent(htmlContent,"text/html;charset=utf-8");
        javaMailSender.send(message);
    }
    public String readFile(String filePath) throws IOException {
        Path path= Paths.get(filePath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}
