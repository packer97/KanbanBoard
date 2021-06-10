package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.exception.ProjectNotFoundException;
import com.group_3.kanbanboard.exception.UserNotFoundException;
import com.group_3.kanbanboard.mappers.ProjectMapper;
import com.group_3.kanbanboard.repository.ProjectRepository;
import com.group_3.kanbanboard.repository.UserProjectRepository;
import com.group_3.kanbanboard.repository.UserRepository;
import com.group_3.kanbanboard.rest.dto.ProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserProjectRepository userProjectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper,
                              UserProjectRepository userProjectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userProjectRepository = userProjectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public ProjectResponseDto getById(UUID id) {
        ProjectEntity project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("project.notFound", id));
        return projectMapper.toResponseDto(project);
    }

    @Transactional
    @Override
    public List<ProjectResponseDto> getAllProjects() {
        List<ProjectEntity> projects = projectRepository.findAll();
        return projects.stream().map(projectMapper::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProjectResponseDto addProject(UUID userId, ProjectRequestDto projectRequestDto) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user.notFound.id", userId));
        ProjectEntity project = projectMapper.toEntity(projectRequestDto);
        projectRepository.save(project);
        UserProjectEntity userProjectEntity = new UserProjectEntity(userEntity, project);
        userProjectRepository.save(userProjectEntity);
        return projectMapper.toResponseDto(project);
    }

    @Transactional
    @Override
    public ProjectResponseDto updateProject(UUID id, ProjectRequestDto projectRequestDto) {
        ProjectEntity projectEntityFromDb = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("project.notFound", id));
        projectEntityFromDb.setTitle(projectRequestDto.getTitle());
        projectEntityFromDb.setDescription(projectRequestDto.getDescription());
        projectRepository.save(projectEntityFromDb);
        return projectMapper.toResponseDto(projectEntityFromDb);
    }

    @Transactional
    @Override
    public void deleteProjectById(UUID id) {
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException("project.notFound", id);
        }
        projectRepository.deleteById(id);
    }
}
