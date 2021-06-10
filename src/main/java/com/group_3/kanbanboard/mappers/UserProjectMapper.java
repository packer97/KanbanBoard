package com.group_3.kanbanboard.mappers;

import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.rest.dto.UserProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


@Mapper(uses = {UserProjectMapper.class, UserMapper.class, ProjectMapper.class}, componentModel = "spring")
public interface UserProjectMapper {

    public UserProjectRequestDto toRequestDto(UserProjectEntity entity);
    @InheritInverseConfiguration
    public UserProjectEntity toEntity(UserProjectRequestDto requestDto);


    public UserProjectResponseDto toResponseDto(UserProjectEntity entity);
    @InheritInverseConfiguration
    public UserProjectEntity toEntity(UserProjectResponseDto responseDto);

}
