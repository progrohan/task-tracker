package progrohan.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import progrohan.backend.dto.UserCreateRequestDto;
import progrohan.backend.dto.UserResponseDto;
import progrohan.backend.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "username")
    UserEntity toEntity(UserCreateRequestDto dto);

    @Mapping(source = "username", target = "name")
    UserResponseDto toDto(UserEntity entity);

    UserResponseDto toResponseDto(UserCreateRequestDto requestDTO);

}