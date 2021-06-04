package com.group_3.kanbanboard.service.entity;


import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.exception.UserProjectNotFoundException;
import com.group_3.kanbanboard.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectEntityServiceImpl implements UserProjectEntityService<UserProjectEntity, UserEntity, ProjectEntity> {

    private final UserProjectRepository userProjectRepository;

    @Autowired
    public UserProjectEntityServiceImpl(UserProjectRepository userProjectRepository) {
        this.userProjectRepository = userProjectRepository;
    }


    @Override
    public List<UserProjectEntity> getFromFirst(UserEntity firstEntity) {
        return userProjectRepository.findByUser(firstEntity);
    }

    @Override
    public List<UserProjectEntity> getFromSecond(ProjectEntity secondEntity) {
        return userProjectRepository.findByProject(secondEntity);
    }

    @Override
    public UserProjectEntity getFromDependencies(UserEntity firstEntity, ProjectEntity secondEntity) {
        return userProjectRepository.findByUserAndProject(firstEntity, secondEntity)
                .orElseThrow(() -> new UserProjectNotFoundException(
                        String.format(
                                "Relation entity UserProject with User (id = %s) and Project(id = %s) not found",
                                firstEntity.getId(), secondEntity.getId())));
    }
}
