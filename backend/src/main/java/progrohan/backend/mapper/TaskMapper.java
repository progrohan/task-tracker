package progrohan.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import progrohan.backend.dto.TaskRequestDto;
import progrohan.backend.dto.TaskResponseDto;
import progrohan.backend.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {


    Task toEntity(TaskRequestDto dto);

    TaskResponseDto toDto(Task task);


}
