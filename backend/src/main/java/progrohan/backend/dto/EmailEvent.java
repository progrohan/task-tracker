package progrohan.backend.dto;

public record EmailEvent(
        String to,
        String subject,
        String body
) {
}
