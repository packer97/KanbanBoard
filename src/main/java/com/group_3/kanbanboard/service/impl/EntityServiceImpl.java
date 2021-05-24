package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.*;
import com.group_3.kanbanboard.exception.*;
import com.group_3.kanbanboard.repository.*;
import com.group_3.kanbanboard.service.EntityService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EntityServiceImpl implements EntityService {
    private final ReleaseRepository releaseRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;
    private final TaskRepository taskRepository;

    public EntityServiceImpl(ReleaseRepository releaseRepository,
                             UserRepository userRepository,
                             ProjectRepository projectRepository,
                             UserProjectRepository userProjectRepository,
                             TaskRepository taskRepository) {
        this.releaseRepository = releaseRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.userProjectRepository = userProjectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public ReleaseEntity getReleaseEntity(UUID releaseId) {
        return releaseRepository.findById(releaseId)
                .orElseThrow(() -> new ReleaseNotFoundException(String.format("Release with id = %s not found", releaseId)));
    }

    @Override
    public ProjectEntity getProjectEntity(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project with id = %s not found", projectId)));
    }

    @Override
    public UserEntity getUserEntity(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username = %s not found", userName)));
    }

    @Override
    public UserEntity getUserEntity(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(String.format("User with id = %s not found", userId)));
    }

    @Override
    public UserProjectEntity getUserProjectEntity(UserProjectId userProjectId) {
        return userProjectRepository.findById(userProjectId)
                .orElseThrow(() -> new UserProjectNotFoundException(String.format("UserProject with id = %s not found", userProjectId)));
    }
    @Override
    public TaskEntity getTaskEntity(UUID taskId){
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new  TaskNotFoundException(String.format("Task with id = %s not found", taskId)));
    }
}

