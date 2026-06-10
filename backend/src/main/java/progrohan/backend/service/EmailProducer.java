package progrohan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import progrohan.backend.dto.EmailEvent;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final KafkaTemplate<String, EmailEvent> kafkaTemplate;

    public void sendEmail(EmailEvent event) {

        kafkaTemplate.send("email-notifications", event);
    }
}
