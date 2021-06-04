package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.mappers.UserMapper;
import com.group_3.kanbanboard.repository.UserProjectRepository;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.entity.ProjectEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ModelViewProjectService {

    private final PrincipalService principalService;
    private final UserProjectRepository userProjectRepository;
    private final ProjectEntityService projectEntityService;
    private final UserMapper userMapper;

    @Autowired
    public ModelViewProjectService(PrincipalService principalService,
                                   UserProjectRepository userProjectRepository,
                                   ProjectEntityService projectEntityService,
                                   UserMapper userMapper) {
        this.principalService = principalService;
        this.userProjectRepository = userProjectRepository;
        this.projectEntityService = projectEntityService;
        this.userMapper = userMapper;
    }

    public List<UserResponseDto> getUsersForProject(UUID projectId) {
        UserEntity projectOwner = principalService.getPrincipalEntity();
        ProjectEntity needProject = projectEntityService.getEntity(projectId);

        List<UserProjectEntity> UserWithProjects = userProjectRepository.findByUser(projectOwner);

        List<ProjectEntity> availableProjects = UserWithProjects.stream()
                .map(UserProjectEntity::getProject)
                .collect(Collectors.toList());

        if (availableProjects.contains(needProject)) {
            List<UserProjectEntity> needProjectWithUsers = userProjectRepository.findByProject(needProject);
            List<UserEntity> users = needProjectWithUsers.stream()
                    .map(UserProjectEntity::getUser)
                    .collect(Collectors.toList());
            return users.stream().map(userMapper::toResponseDto).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
