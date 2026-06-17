package progrohan.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import progrohan.scheduler.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u JOIN FETCH u.tasks")
    List<User> findAllWithTasks();


}
