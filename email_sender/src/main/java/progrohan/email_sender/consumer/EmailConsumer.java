package progrohan.email_sender.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import progrohan.email_sender.model.EmailEvent;
import progrohan.email_sender.service.EmailSenderService;

@Component
public class EmailConsumer {

    EmailSenderService emailSenderService;

    @KafkaListener(topics = "email-notifications")
    public void consume(EmailEvent event) {

        emailSenderService.sendRegistrationEmail(event);

    }


}
