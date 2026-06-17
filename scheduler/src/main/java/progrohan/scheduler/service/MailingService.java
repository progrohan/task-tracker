package progrohan.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import progrohan.scheduler.dto.EmailEvent;
import progrohan.scheduler.entity.User;
import progrohan.scheduler.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailingService {

    private final UserRepository userRepository;
    private final EmailProducer emailProducer;
    private final EmailBuilderService emailBuilderService;

    @Scheduled(cron = "0 55 23 * * *", zone = "Europe/Moscow")
    public void ProcessUsers(){

        List<User> users = userRepository.findAllWithTasks();

        for (User user : users) {

            EmailEvent email = emailBuilderService.buildEmail(user);
            emailProducer.sendEmail(email);

        }

    }

}
