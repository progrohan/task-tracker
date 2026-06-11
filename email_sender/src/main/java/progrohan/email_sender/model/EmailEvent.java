package progrohan.email_sender.model;

public record EmailEvent(
        String to,
        String subject,
        String body
) {
}
