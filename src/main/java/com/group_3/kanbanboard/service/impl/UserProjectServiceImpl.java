package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.enums.InProjectUserRole;
import com.group_3.kanbanboard.exception.ReleaseNotFoundException;
import com.group_3.kanbanboard.exception.UserProjectNotFoundException;
import com.group_3.kanbanboard.mappers.UserProjectMapper;
import com.group_3.kanbanboard.repository.UserProjectRepository;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.service.EntityService;
import com.group_3.kanbanboard.service.UserProjectService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
      EntityService entityService, UserProjectMapper userProjectMapper) {
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
  public void setUserProjectRole(UUID userId, UUID projectId, InProjectUserRole role) {
    getUserProjectByUserAndProject(userId, projectId).setProjectUserRole(role);

  }

  @Transactional
  @Override
  public boolean isUserLeadInProject(UUID userId, UUID projectId) {
    InProjectUserRole userRole = getUserProjectByUserAndProject(userId, projectId)
        .getProjectUserRole();
    return userRole == InProjectUserRole.LEAD;
  }
}
