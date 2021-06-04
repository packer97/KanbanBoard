package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.enums.InProjectUserRole;
import com.group_3.kanbanboard.mappers.UserProjectMapper;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.entity.ProjectEntityServiceImpl;
import com.group_3.kanbanboard.service.entity.UserEntityServiceImpl;
import com.group_3.kanbanboard.service.entity.UserProjectEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserProjectServiceImpl implements UserProjectService {

    
    private final ProjectEntityServiceImpl projectEntityService;
    private final UserEntityServiceImpl userEntityService;
    private final UserProjectEntityServiceImpl userProjectEntityService;
    private final UserProjectMapper userProjectMapper;

    @Autowired
    public UserProjectServiceImpl(ProjectEntityServiceImpl projectEntityService,
                                  UserEntityServiceImpl userEntityService,
                                  UserProjectEntityServiceImpl userProjectEntityService,
                                  UserProjectMapper userProjectMapper) {
        this.projectEntityService = projectEntityService;
        this.userEntityService = userEntityService;
        this.userProjectEntityService = userProjectEntityService;

        this.userProjectMapper = userProjectMapper;
    }


    @Transactional
    @Override
    public List<UserProjectResponseDto> getUserProjectsFromUser(UUID userId) {
        UserEntity user = userEntityService.getEntity(userId);
        List<UserProjectEntity> usersAndProjects = userProjectEntityService.getFromFirst(user);

        return usersAndProjects.stream().map(userProjectMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<UserProjectResponseDto> getUserProjectsFromProject(UUID projectId) {
        ProjectEntity project = projectEntityService.getEntity(projectId);
        List<UserProjectEntity> projectsAndUsers = userProjectEntityService.getFromSecond(project);

        return projectsAndUsers.stream().map(userProjectMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserProjectResponseDto getUserProjectByUserAndProject(UUID userId, UUID projectId) {
        UserProjectEntity userWithProject = userProjectEntityService.getFromDependencies(
                userEntityService.getEntity(userId), projectEntityService.getEntity(projectId));

        return userProjectMapper.toResponseDto(userWithProject);
    }

    @Transactional
    @Override
    public UserProjectResponseDto setUserProjectRole(UUID userId, UUID projectId, InProjectUserRole inProjectUserRole) {
        UserProjectEntity userProjectFromDb = userProjectEntityService.getFromDependencies(
                userEntityService.getEntity(userId), projectEntityService.getEntity(projectId));
        userProjectFromDb.setProjectUserRole(inProjectUserRole);
        userProjectEntityService.saveEntity(userProjectFromDb);
        return userProjectMapper.toResponseDto(userProjectFromDb);

    }

    @Transactional
    @Override
    public UserProjectResponseDto setUserInProject(UUID userId, UUID projectId, String projectRole) {
        UserEntity userEntity = userEntityService.getEntity(userId);
        ProjectEntity projectEntity = projectEntityService.getEntity(projectId);
        UserProjectEntity userProjectEntity = new UserProjectEntity(userEntity, projectEntity);

        userProjectEntity.setProjectUserRole(InProjectUserRole.valueOf(projectRole));
        userProjectEntityService.saveEntity(userProjectEntity);
        return userProjectMapper.toResponseDto(userProjectEntity);
    }

    @Transactional
    @Override
    public UserProjectResponseDto deleteUserInProject(UUID projectId, String userName) {
        UserEntity userEntity = userEntityService.getEntity(userName);
        ProjectEntity projectEntity = projectEntityService.getEntity(projectId);
        UserProjectEntity userProjectEntity = new UserProjectEntity(userEntity, projectEntity);
        userProjectEntityService.deleteEntity(userProjectEntity);
        return userProjectMapper.toResponseDto(userProjectEntity);
    }

    @Transactional
    @Override
    public boolean isUserLeadInProject(UUID userId, UUID projectId) {
        InProjectUserRole userRole = getUserProjectByUserAndProject(userId, projectId)
                .getProjectUserRole();
        return userRole == InProjectUserRole.LEAD;
    }

    @Transactional
    @Override
    public boolean isUserProjectCreator(String username, UUID projectId) {
        UUID userId = userEntityService.getEntity(username).getId();
        UUID creator = getUserProjectByUserAndProject(userId, projectId)
                .getProject().getLeadId();

        return userId.equals(creator);
    }
}
