package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.exception.TaskNotFoundException;
import com.group_3.kanbanboard.mappers.ReleaseMapper;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.mappers.UserMapper;
import com.group_3.kanbanboard.repository.TaskRepository;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ModelViewTaskService {

    private final TaskRepository taskRepository;
    private final EntityService entityService;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;
    private final ReleaseMapper releaseMapper;

    @Autowired
    public ModelViewTaskService(TaskRepository taskRepository, EntityService entityService, TaskMapper taskMapper, UserMapper userMapper, ReleaseMapper releaseMapper) {
        this.taskRepository = taskRepository;
        this.entityService = entityService;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
        this.releaseMapper = releaseMapper;
    }

    @Transactional
    public List<TaskResponseDto> getTasksFromProjectAndRelease(UUID projectId, UUID releaseId) {
        ProjectEntity project = entityService.getProjectEntity(projectId);
        ReleaseEntity release = entityService.getReleaseEntity(releaseId);

        List<TaskResponseDto> taskResponseDtos = taskRepository.findByProjectAndRelease(project, release).stream()
                .map(taskMapper::toResponseDto)
                .collect(Collectors.toList());

        return taskResponseDtos;
    }

    @Transactional
    public TaskResponseDto getTaskByIdFromProjectAndRelease(UUID taskId, UUID projectId, UUID releaseId) {

        TaskEntity taskEntity = taskRepository.findByIdAndProjectAndRelease(
                taskId, entityService.getProjectEntity(projectId), entityService.getReleaseEntity(releaseId))
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format("Task with id = %s in release with id = %s and project with id = %s, not found in principal",
                                taskId, releaseId, projectId)));
        return taskMapper.toResponseDto(taskEntity);
    }

    @Transactional
    public UserResponseDto getUserByUserName(String userName) {
        return userMapper.toResponseDto(entityService.getUserEntity(userName));
    }

    @Transactional
    public ReleaseResponseDto getReleaseById(UUID id) {
        return releaseMapper.toResponseDto(entityService.getReleaseEntity(id));
    }
}
