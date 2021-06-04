package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.exception.TaskNotFoundException;
import com.group_3.kanbanboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class TaskEntityServiceImpl implements TaskEntityService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskEntityServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskEntity saveEntity(TaskEntity entity) {
        return taskRepository.save(entity);
    }

    @Override
    public TaskEntity getEntity(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id = %s not found", taskId));
    }

    @Override
    public List<TaskEntity> getAllEntity() {
        return taskRepository.findAll();
    }

    @Override
    public boolean exists(UUID taskId) {
        return taskRepository.existsById(taskId);
    }

    @Override
    public void deleteById(UUID taskId) {
        taskRepository.deleteById(taskId);
    }


    @Override
    public List<TaskEntity> getFromDependencies(ProjectEntity firstEntity, ReleaseEntity secondEntity) {
        return taskRepository.findByProjectAndRelease(firstEntity, secondEntity);
    }

    @Override
    public TaskEntity getByIdFromDependencies(UUID taskId, ProjectEntity firstEntity, ReleaseEntity secondEntity) {
        return taskRepository.findByIdAndProjectAndRelease(taskId, firstEntity, secondEntity)
                .orElseThrow(() -> new TaskNotFoundException(
                        "Task with id = %s not found from current project and release.", taskId));
    }
}

