package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.exception.TaskNotFoundException;
import com.group_3.kanbanboard.mappers.ReleaseMapper;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.mappers.UserMapper;
import com.group_3.kanbanboard.repository.TaskRepository;
import com.group_3.kanbanboard.rest.dto.TaskRequestDto;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.entity.EntityNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ModelViewTaskService {

    private final TaskRepository taskRepository;
    private final EntityNewService<ProjectEntity, UUID> entityProjectService;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @Autowired
    public ModelViewTaskService(TaskRepository taskRepository,
                                TaskMapper taskMapper,
                                UserMapper userMapper,
                                ReleaseMapper releaseMapper,
                                EntityNewService<ProjectEntity, UUID> entityProjectService) {
        this.taskRepository = taskRepository;
        this.entityProjectService = entityProjectService;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    public List<TaskResponseDto> getTasksFromProjectAndRelease(UUID projectId, UUID releaseId) {
        ProjectEntity project = .getProjectEntity(projectId);
        ReleaseEntity release = entityService.getReleaseEntity(releaseId);

        List<TaskResponseDto> taskResponseDtos = taskRepository.findByProjectAndRelease(project, release).stream()
                .map(taskMapper::toResponseDto)
                .collect(Collectors.toList());

        return taskResponseDtos;
    }

    public void setDependenciesAndSave(UUID taskId,
                                       String username,
                                       UUID projectId,
                                       UUID releaseId,
                                       TaskRequestDto taskRequestDto) {
        TaskEntity taskFromRequest = taskMapper.toEntity(taskRequestDto);
        UserEntity performer = entityService.getUserEntity(username);
        ProjectEntity project = entityService.getProjectEntity(projectId);
        ReleaseEntity release = entityService.getReleaseEntity(releaseId);
        taskFromRequest.setPerformer(performer);
        taskFromRequest.setProject(project);
        taskFromRequest.setRelease(release);


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
}
