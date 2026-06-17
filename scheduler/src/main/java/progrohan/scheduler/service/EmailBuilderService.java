package progrohan.scheduler.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import progrohan.scheduler.dto.EmailEvent;
import progrohan.scheduler.entity.TaskStatus;
import progrohan.scheduler.entity.User;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class EmailBuilderService {

    private static final String SUBJECT = "Daily report";
    private static final String ADDRESSING = "Dear, %s \n";
    private static final String COMPLETED = "Today you have completed %d tasks! \n";
    private static final String IN_PROGRESS = "You have %d unfinished tasks! \n";

    public EmailEvent buildEmail(User user) {

        EmailEvent event;

        long inProgressQuantity = user.getTasks()
                .stream()
                .filter(task -> task.getStatus().equals(TaskStatus.IN_PROGRESS))
                .count();

        long completedQuantity = user.getTasks()
                .stream()
                .filter(task -> task.getStatus().equals(TaskStatus.COMPLETED))
                .filter(task -> task.getCompletedAt().toLocalDate().equals(LocalDate.now()))
                .count();

        if(inProgressQuantity > 0 && completedQuantity > 0) {
            event = buildEmailForBothPresent(user, inProgressQuantity, completedQuantity);
        }else if(inProgressQuantity == 0 && completedQuantity > 0){
            event = buildEmailForOnlyCompleted(user, completedQuantity);
        }else if(inProgressQuantity > 0){
            event = buildEmailForOnlyInProgress(user, inProgressQuantity);
        }else {
            event = buildEmailForNothingHappened(user);
        }

        return event;

    }

    public EmailEvent buildEmailForBothPresent(User user, Long inProgress, Long completed) {

        String message = String.format(ADDRESSING, user.getUsername())
                         + String.format(COMPLETED, completed)
                         + String.format(IN_PROGRESS, inProgress);

        return new EmailEvent(user.getEmail(), SUBJECT, message);


    }

    public EmailEvent buildEmailForOnlyCompleted(User user, Long completed) {

        String message = String.format(ADDRESSING, user.getUsername())
                         + String.format(COMPLETED, completed)
                         + "No tasks left :) /n";

        return new EmailEvent(user.getEmail(), SUBJECT, message);

    }

    public EmailEvent buildEmailForOnlyInProgress(User user, Long inProgress) {

        String message = String.format(ADDRESSING, user.getUsername())
                         + String.format(IN_PROGRESS, inProgress)
                         + "No tasks completed :( /n";

        return new EmailEvent(user.getEmail(), SUBJECT, message);

    }

    public EmailEvent buildEmailForNothingHappened(User user) {

        String message = String.format(ADDRESSING, user.getUsername())
                         + "Nothing done and nothing to do 0_o \n";

        return new EmailEvent(user.getEmail(), SUBJECT, message);

    }



}
