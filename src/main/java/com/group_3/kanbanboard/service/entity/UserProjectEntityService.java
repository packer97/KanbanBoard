package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;

import java.util.List;
import java.util.UUID;

public interface UserProjectEntityService extends EntityNewService<TaskEntity, UUID> {

    List<TaskEntity> getFromDependencies(ProjectEntity firstEntity, ReleaseEntity secondEntity);

    TaskEntity getByIdFromDependencies(UUID id, ProjectEntity  firstEntity, ReleaseEntity secondEntity);

}
