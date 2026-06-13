package progrohan.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import progrohan.backend.dto.EmailEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final KafkaTemplate<String, EmailEvent> kafkaTemplate;

    public void sendEmail(EmailEvent event) {

        log.info("SENDING: {}", event);

        kafkaTemplate.send("email-notifications", event).whenComplete((res, ex) -> {
            if (ex != null) {
                log.error("SEND ERROR: {}", ex.getMessage());
            } else {
                log.info("SENT OK offset = {}", res.getRecordMetadata().offset());
            }
        });
    }
}
