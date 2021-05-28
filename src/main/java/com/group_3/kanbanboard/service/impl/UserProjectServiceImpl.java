package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.*;
import com.group_3.kanbanboard.enums.InProjectUserRole;
import com.group_3.kanbanboard.exception.ReleaseNotFoundException;
import com.group_3.kanbanboard.exception.UserProjectNotFoundException;
import com.group_3.kanbanboard.mappers.UserProjectMapper;
import com.group_3.kanbanboard.repository.UserProjectRepository;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.service.entity.EntityService;
import com.group_3.kanbanboard.service.UserProjectService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserProjectServiceImpl implements UserProjectService {

  private final UserProjectRepository userProjectRepository;
  private final EntityService entityService;
  private final UserProjectMapper userProjectMapper;



  @Autowired
  public UserProjectServiceImpl(UserProjectRepository userProjectRepository,
       UserProjectMapper userProjectMapper,EntityService entityService) {
    this.userProjectRepository = userProjectRepository;
    this.entityService = entityService;
    this.userProjectMapper = userProjectMapper;
  }


  @Transactional
  @Override
  public List<UserProjectResponseDto> getUserProjectsFromUser(UUID userId) {
    UserEntity user = entityService.getUserEntity(userId);
    List<UserProjectEntity> usersAndProjects = userProjectRepository.findByUser(user);

    return usersAndProjects.stream().map(userProjectMapper::toResponseDto)
        .collect(Collectors.toList());

  }

  @Transactional
  @Override
  public List<UserProjectResponseDto> getUserProjectsFromProject(UUID projectId) {
    ProjectEntity project = entityService.getProjectEntity(projectId);
    List<UserProjectEntity> projectsAndUsers = userProjectRepository.findByProject(project);

    return projectsAndUsers.stream().map(userProjectMapper::toResponseDto)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public UserProjectResponseDto getUserProjectByUserAndProject(UUID userId, UUID projectId) {
    UserProjectEntity userWithProject = userProjectRepository.findByUserAndProject(
        entityService.getUserEntity(userId), entityService.getProjectEntity(projectId))
        .orElseThrow(() -> new UserProjectNotFoundException(
            String.format(
                "Relation entity UserProject with User (id = %s) and Project(id = %s) not found",
                userId, projectId)));
    return userProjectMapper.toResponseDto(userWithProject);
  }

  @Transactional
  @Override
  public UserProjectResponseDto setUserProjectRole(UUID userId, UUID projectId, InProjectUserRole inProjectUserRole) {
    UserProjectEntity userProjectFromDb = userProjectRepository.findByUserAndProject(entityService.getUserEntity(userId), entityService.getProjectEntity(projectId))
            .orElseThrow(() -> new UserProjectNotFoundException(
                    String.format(
                            "Relation entity UserProject with User (id = %s) and Project(id = %s) not found",
                            userId, projectId)));
    userProjectFromDb.setProjectUserRole(inProjectUserRole);
    userProjectRepository.save(userProjectFromDb);
    return userProjectMapper.toResponseDto(userProjectFromDb);

  }
  @Transactional
  @Override
  public UserProjectResponseDto setUserInProject(UUID userId, UUID projectId,String projectRole) {
    UserEntity userEntity = entityService.getUserEntity(userId);
    ProjectEntity projectEntity = entityService.getProjectEntity(projectId);
    UserProjectEntity userProjectEntity = new UserProjectEntity (userEntity, projectEntity);

    userProjectEntity.setProjectUserRole( InProjectUserRole.valueOf(projectRole));
    userProjectRepository.save(userProjectEntity);
    return userProjectMapper.toResponseDto(userProjectEntity);
  }
  @Transactional
  @Override
  public UserProjectResponseDto deleteUserInProject(UUID projectId, String userName) {
    UserEntity userEntity = entityService.getUserEntity(userName);
    ProjectEntity projectEntity = entityService.getProjectEntity(projectId);
    UserProjectEntity userProjectEntity = new UserProjectEntity (userEntity, projectEntity);
    userProjectRepository.delete(userProjectEntity);
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
    UUID userId = entityService.getUserEntity(username).getId() ;
    UUID creator = getUserProjectByUserAndProject(userId, projectId)
            .getProject().getLeadId();

    return userId.equals(creator);
  }
}
