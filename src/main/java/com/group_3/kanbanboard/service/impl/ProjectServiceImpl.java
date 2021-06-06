package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.exception.ProjectNotFoundException;
import com.group_3.kanbanboard.mappers.ProjectMapper;
import com.group_3.kanbanboard.repository.UserProjectRepository;
import com.group_3.kanbanboard.rest.dto.ProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.service.ProjectService;
import com.group_3.kanbanboard.service.entity.ProjectEntityServiceImpl;
import com.group_3.kanbanboard.service.entity.UserEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectEntityServiceImpl projectEntityService;
    private final ProjectMapper projectMapper;
    private final UserProjectRepository userProjectRepository;
    private final UserEntityServiceImpl userEntityService;

    @Autowired
    public ProjectServiceImpl(ProjectEntityServiceImpl projectEntityService, ProjectMapper projectMapper,
                              UserProjectRepository userProjectRepository, UserEntityServiceImpl userEntityService) {
        this.projectEntityService = projectEntityService;
        this.projectMapper = projectMapper;
        this.userProjectRepository = userProjectRepository;
        this.userEntityService = userEntityService;

    }

    @Transactional
    @Override
    public ProjectResponseDto getById(UUID id) {
        ProjectEntity project = projectEntityService.getEntity(id);
        return projectMapper.toResponseDto(project);
    }

    @Transactional
    @Override
    public List<ProjectResponseDto> getAllProjects() {
        List<ProjectEntity> projects = projectEntityService.getAllEntity();
        return projects.stream().map(projectMapper::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProjectResponseDto addProject(UUID userId, ProjectRequestDto projectRequestDto) {
        UserEntity userEntity = userEntityService.getEntity(userId);
        ProjectEntity project = projectMapper.toEntity(projectRequestDto);
        projectEntityService.saveEntity(project);
        UserProjectEntity userProjectEntity = new UserProjectEntity(userEntity, project);
        userProjectRepository.save(userProjectEntity);
        return projectMapper.toResponseDto(project);
    }

    @Transactional
    @Override
    public ProjectResponseDto updateProject(UUID id, ProjectRequestDto projectRequestDto) {
        ProjectEntity projectEntityFromDb = projectEntityService.getEntity(id);
        ProjectEntity projectEntityDto = projectMapper.toEntity(projectRequestDto);
        projectEntityDto.setTitle(projectEntityFromDb.getTitle());
        projectEntityDto.setDescription(projectEntityFromDb.getDescription());
        projectEntityService.saveEntity(projectEntityDto);
        return projectMapper.toResponseDto(projectEntityDto);
    }

    @Transactional
    @Override
    public void deleteProjectById(UUID id) {
        if (!projectEntityService.exists(id)) {
            throw new ProjectNotFoundException(String.format("Project with ID = %s was not found", id));
        }
        projectEntityService.deleteById(id);
    }
}
