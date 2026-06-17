package progrohan.scheduler.dto;

public record EmailEvent(
        String to,
        String subject,
        String body
) {
}

