package progrohan.email_sender.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import progrohan.email_sender.model.EmailEvent;
import progrohan.email_sender.service.EmailSenderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "email-notifications",groupId = "email-service")
    public void consume(EmailEvent emailEvent) {

        log.info("Listened event: {}", emailEvent);

        try{
            emailSenderService.sendRegistrationEmail(emailEvent);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("Email event received: {}", emailEvent);
    }


}
