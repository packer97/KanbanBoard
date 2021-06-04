package com.group_3.kanbanboard.service.entity;


import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.entity.UserProjectId;
import com.group_3.kanbanboard.exception.TaskNotFoundException;
import com.group_3.kanbanboard.exception.UserProjectNotFoundException;
import com.group_3.kanbanboard.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectEntityServiceImpl implements UserProjectEntityService {

    private final UserProjectRepository userProjectRepository;

    @Autowired
    public UserProjectEntityServiceImpl(UserProjectRepository userProjectRepository) {
        this.userProjectRepository = userProjectRepository;
    }


    @Override
    public void deleteEntity(UserProjectEntity entity) {
        userProjectRepository.delete(entity);
    }

    @Override
    public List<UserProjectEntity> getFromFirst(UserEntity userEntity) {
        return userProjectRepository.findByUser(userEntity);
    }

    @Override
    public List<UserProjectEntity> getFromSecond(ProjectEntity projectEntity) {
        return userProjectRepository.findByProject(projectEntity);
    }

    @Override
    public UserProjectEntity getFromDependencies(UserEntity userEntity, ProjectEntity projectEntity) {
        return userProjectRepository.findByUserAndProject(userEntity, projectEntity)
                .orElseThrow(() -> new UserProjectNotFoundException(
                                "Relation entity UserProject with User (id = %s) and Project(id = %s) not found",
                                userEntity.getId(), projectEntity.getId()));
    }

    @Override
    public UserProjectEntity saveEntity(UserProjectEntity entity) {
        return userProjectRepository.save(entity);
    }

    @Override
    public UserProjectEntity getEntity(UserProjectId userProjectId) {
        return userProjectRepository.findById(userProjectId)
                .orElseThrow(() -> new TaskNotFoundException("Relation entity with id = %s not found", userProjectId));
    }

    @Override
    public List<UserProjectEntity> getAllEntity() {
        return userProjectRepository.findAll();
    }

    @Override
    public boolean exists(UserProjectId userProjectId) {
        return userProjectRepository.existsById(userProjectId);
    }

    @Override
    public void deleteById(UserProjectId userProjectId) {
        userProjectRepository.deleteById(userProjectId);

    }
}
