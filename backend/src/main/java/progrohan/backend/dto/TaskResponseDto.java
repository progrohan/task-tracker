package progrohan.backend.dto;

import progrohan.backend.entity.TaskStatus;

public record TaskResponseDto(

        Long id,

        String title,

        String description,

        TaskStatus status

) {
}
