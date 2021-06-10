package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.entity.UserProjectId;

import java.util.List;

public interface UserProjectEntityService extends EntityNewService<UserProjectEntity, UserProjectId> {

    void deleteEntity(UserProjectEntity entity);

    List<UserProjectEntity> getFromFirst(UserEntity firstEntity);

    List<UserProjectEntity> getFromSecond(ProjectEntity secondEntity);

    UserProjectEntity getFromDependencies(UserEntity firstEntity, ProjectEntity secondEntity);

}
