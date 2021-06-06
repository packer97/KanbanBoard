package com.group_3.kanbanboard.mappers;

import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.rest.dto.UserRequestDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(uses = {ProjectMapper.class, TaskMapper.class}, componentModel = "spring")
public interface UserMapper {

    public UserRequestDto toRequestDto(UserEntity entity);
    @InheritInverseConfiguration
    public UserEntity toEntity(UserRequestDto requestDto);


    public UserResponseDto toResponseDto(UserEntity entity);
    @InheritInverseConfiguration
    public UserEntity toEntity(UserResponseDto responseDto);
}
