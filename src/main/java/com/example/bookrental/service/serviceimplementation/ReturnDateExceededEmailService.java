package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.repo.BookTransactionRepo;
import com.example.bookrental.utils.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ReturnDateExceededEmailService {
    private final JavaMailSender javaMailSender;
    private final BookTransactionRepo bookTransactionRepo;
    private final CustomMessageSource messageSource;

//    @Scheduled(fixedRate = 500)
    public String sendDueDateMail() {
        Date today = new Date();

        List<BookTransaction> returnDateExceeded = bookTransactionRepo.findByToDateBefore(today);
//        List<BookTransaction> returnDateExceeded = bookTransactionRepo.findByToDateAfter(today);

        for (BookTransaction transaction : returnDateExceeded) {
            String memberEmail = transaction.getMember().getEmail();
            String emailSubject = "Return date exceeded";
            String emailBody = MailUtils.setTemplet(memberEmail,emailSubject);
            sendMail(memberEmail, emailSubject, emailBody);
        }
//        System.out.println("mailsent");
        return messageSource.get(ExceptionMessages.RETURN_MAIL_SENT.getCode());
    }

    public String sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        return messageSource.get(ExceptionMessages.MAIL_SENT.getCode());
    }

}
