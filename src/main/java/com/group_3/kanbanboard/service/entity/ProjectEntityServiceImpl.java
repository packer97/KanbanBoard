package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.exception.ProjectNotFoundException;
import com.group_3.kanbanboard.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectEntityServiceImpl implements ProjectEntityService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectEntityServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectEntity saveEntity(ProjectEntity entity) {
        return projectRepository.save(entity);
    }

    @Override
    public ProjectEntity getEntity(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project with id = %s not found", projectId)));
    }

    @Override
    public List<ProjectEntity> getAllEntity() {
        return projectRepository.findAll();
    }

    @Override
    public boolean exists(UUID taskId) {
        return projectRepository.existsById(taskId);
    }

    @Override
    public void deleteById(UUID taskId) {
        projectRepository.deleteById(taskId);
    }
}

