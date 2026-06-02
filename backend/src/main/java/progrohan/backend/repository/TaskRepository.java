package progrohan.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import progrohan.backend.entity.Task;
import progrohan.backend.entity.TaskStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.status = :status")
    public List<Task> findByStatus(@Param("status") TaskStatus status);

}
