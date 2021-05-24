package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.entity.*;

import java.util.UUID;

public interface EntityService {

    ReleaseEntity getReleaseEntity(UUID releaseId);

    ProjectEntity getProjectEntity(UUID projectId);

    UserEntity getUserEntity(String userName);

    UserEntity getUserEntity(UUID userId);

    UserProjectEntity getUserProjectEntity(UserProjectId userProjectId);

    TaskEntity getTaskEntity(UUID taskId);
}