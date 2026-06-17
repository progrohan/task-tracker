package progrohan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import progrohan.backend.dto.TaskRequestDto;
import progrohan.backend.dto.TaskResponseDto;
import progrohan.backend.entity.Task;
import progrohan.backend.entity.TaskStatus;
import progrohan.backend.entity.UserEntity;
import progrohan.backend.exception.TaskNotFoundException;
import progrohan.backend.mapper.TaskMapper;
import progrohan.backend.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

    public List<TaskResponseDto> getTasksInProgress(String userName) {

        Long userId = userService.getUserId(userName);

        return taskRepository
                .findByStatus(TaskStatus.IN_PROGRESS, userId)
                .stream()
                .map(taskMapper::toDto)
                .toList();

    }

    public List<TaskResponseDto> getTasksCompleted(String userName) {

        Long userId = userService.getUserId(userName);

        return taskRepository
                .findByStatus(TaskStatus.COMPLETED, userId)
                .stream()
                .map(taskMapper::toDto)
                .toList();

    }

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto, String userName) {

        UserEntity user = userService.loadUserEntityByUsername(userName);

        Task task = taskMapper.toEntity(taskRequestDto);
        task.setUser(user);
        task.setStatus(TaskStatus.IN_PROGRESS);

        taskRepository.save(task);
        return taskMapper.toDto(task);

    }

    public TaskResponseDto updateTask(TaskRequestDto taskRequestDto, Long taskId) {
        Task task = getTaskByIdOrThrow(taskId);

        task.setTitle(taskRequestDto.title());
        task.setDescription(taskRequestDto.description());

        taskRepository.save(task);

        return taskMapper.toDto(task);

    }

    public void deleteTask(Long taskId) {

        Task task = getTaskByIdOrThrow(taskId);

        taskRepository.delete(task);

    }

    public TaskResponseDto makeComplete(Long taskId) {

        Task task = getTaskByIdOrThrow(taskId);

        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletedAt(LocalDateTime.now());

        taskRepository.save(task);

        return taskMapper.toDto(task);

    }


    public TaskResponseDto getTaskById(Long taskId) {

        return taskMapper.toDto(getTaskByIdOrThrow(taskId));

    }

    private Task getTaskByIdOrThrow(Long taskId) {

        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found"));

    }


}
