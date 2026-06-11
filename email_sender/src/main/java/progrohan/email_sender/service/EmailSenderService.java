package progrohan.email_sender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import progrohan.email_sender.model.EmailEvent;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public void sendRegistrationEmail(EmailEvent mail) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(mail.to());
        message.setSubject(mail.subject());

        message.setText(mail.body());

        mailSender.send(message);
    }
}