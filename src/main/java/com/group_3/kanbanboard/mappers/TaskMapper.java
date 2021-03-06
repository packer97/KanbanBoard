package com.group_3.kanbanboard.mappers;

import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.rest.dto.TaskRequestDto;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProjectMapper.class, ReleaseMapper.class}, componentModel = "spring")
public interface TaskMapper {

    public TaskRequestDto toRequestDto(TaskEntity entity);
    @InheritInverseConfiguration
    public TaskEntity toEntity(TaskRequestDto requestDto);


    public TaskResponseDto toResponseDto(TaskEntity entity);
    @InheritInverseConfiguration
    public TaskEntity toEntity(TaskResponseDto responseDto);
}
