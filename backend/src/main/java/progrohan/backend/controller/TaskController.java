package progrohan.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import progrohan.backend.dto.TaskRequestDto;
import progrohan.backend.dto.TaskResponseDto;
import progrohan.backend.service.TaskService;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/in-progress")
    public ResponseEntity<List<TaskResponseDto>> getTasksInProgress(@AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok(taskService.getTasksInProgress(userDetails.getUsername()));

    }

    @GetMapping("/completed")
    public ResponseEntity<List<TaskResponseDto>> getTasksCompleted(@AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok(taskService.getTasksCompleted(userDetails.getUsername()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){

        return ResponseEntity.ok(taskService.getTaskById(id));

    }

    @PostMapping()
    public ResponseEntity<TaskResponseDto> createTask(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TaskRequestDto taskRequestDto) throws URISyntaxException {

        TaskResponseDto task= taskService.createTask(taskRequestDto, userDetails.getUsername());

        return ResponseEntity
                .created(new URI("/api/task/" + task.id()))
                .body(task);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){

        taskService.deleteTask(id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto){

        TaskResponseDto task = taskService.updateTask(taskRequestDto, id);

        return ResponseEntity.ok(task);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> makeCompleted(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){

        TaskResponseDto taskResponseDto = taskService.makeComplete(id);

        return ResponseEntity.ok(taskResponseDto);

    }


}
