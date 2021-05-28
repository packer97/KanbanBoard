package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.mappers.UserMapper;
import com.group_3.kanbanboard.rest.dto.TaskRequestDto;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.entity.ProjectEntityService;
import com.group_3.kanbanboard.service.entity.ReleaseEntityService;
import com.group_3.kanbanboard.service.entity.TaskEntityService;
import com.group_3.kanbanboard.service.entity.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ModelViewTaskService {

    private final TaskEntityService taskEntityService;
    private final UserEntityService userEntityService;
    private final ProjectEntityService projectEntityService;
    private final ReleaseEntityService releaseEntityService;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @Autowired
    public ModelViewTaskService(TaskEntityService taskEntityService,
                                UserEntityService userEntityService,
                                ProjectEntityService projectEntityService,
                                ReleaseEntityService releaseEntityService,
                                TaskMapper taskMapper,
                                UserMapper userMapper) {
        this.taskEntityService = taskEntityService;
        this.userEntityService = userEntityService;
        this.projectEntityService = projectEntityService;
        this.releaseEntityService = releaseEntityService;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    public List<TaskResponseDto> getTasksFromProjectAndRelease(UUID projectId, UUID releaseId) {
        ProjectEntity project = projectEntityService.getEntity(projectId);
        ReleaseEntity release = releaseEntityService.getEntity(releaseId);

        List<TaskResponseDto> taskResponseDtos = taskEntityService.getFromDependencies(project, release).stream()
                .map(taskMapper::toResponseDto)
                .collect(Collectors.toList());

        return taskResponseDtos;
    }

    public TaskResponseDto setDependenciesAndGet(String username,
                                                 UUID projectId,
                                                 UUID releaseId,
                                                 TaskRequestDto taskRequestDto) {
        TaskEntity taskFromRequest = setDependencies(username, projectId, releaseId, taskRequestDto);
        return taskMapper.toResponseDto(taskFromRequest);

    }

    @Transactional
    public TaskResponseDto setDependenciesAndSave(UUID taskId,
                                                  String username,
                                                  UUID projectId,
                                                  UUID releaseId,
                                                  TaskRequestDto taskRequestDto) {
        TaskEntity taskFromRequest = setDependencies(username, projectId, releaseId, taskRequestDto);
        taskFromRequest.setId(taskId);

        TaskEntity savedTask = taskEntityService.saveEntity(taskFromRequest);
        return taskMapper.toResponseDto(savedTask);
    }

    @Transactional
    public TaskResponseDto setDependenciesAndSave(String username,
                                                  UUID projectId,
                                                  UUID releaseId,
                                                  TaskRequestDto taskRequestDto) {
        TaskEntity taskFromRequest = setDependencies(username, projectId, releaseId, taskRequestDto);

        TaskEntity savedTask = taskEntityService.saveEntity(taskFromRequest);
        return taskMapper.toResponseDto(savedTask);
    }

    private TaskEntity setDependencies(String username, UUID projectId, UUID releaseId, TaskRequestDto taskRequestDto) {
        TaskEntity taskFromRequest = taskMapper.toEntity(taskRequestDto);

        taskFromRequest.setPerformer(userEntityService.getEntity(username));
        taskFromRequest.setProject(projectEntityService.getEntity(projectId));
        taskFromRequest.setRelease(releaseEntityService.getEntity(releaseId));
        return taskFromRequest;
    }


    @Transactional
    public TaskResponseDto getTaskByIdFromProjectAndRelease(UUID taskId, UUID projectId, UUID releaseId) {
        ProjectEntity project = projectEntityService.getEntity(projectId);
        ReleaseEntity release = releaseEntityService.getEntity(releaseId);
        TaskEntity taskEntity = taskEntityService.getByIdFromDependencies(taskId, project, release);
        return taskMapper.toResponseDto(taskEntity);
    }

    @Transactional
    public UserResponseDto getUserByUserName(String username) {
        return userMapper.toResponseDto(userEntityService.getEntity(username));
    }
}
