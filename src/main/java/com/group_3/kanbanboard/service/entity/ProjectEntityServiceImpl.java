package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.exception.ProjectNotFoundException;
import com.group_3.kanbanboard.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class ProjectEntityServiceImpl implements ProjectEntityService {

    private final ProjectRepository projectRepository;
    private ResourceBundle res = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());

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
                .orElseThrow(() -> new ProjectNotFoundException(String.format(res.getString("project.notFound"), projectId)));
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

