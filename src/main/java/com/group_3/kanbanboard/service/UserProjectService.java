package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.entity.UserProjectId;
import com.group_3.kanbanboard.enums.InProjectUserRole;
import com.group_3.kanbanboard.rest.dto.UserProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserProjectService {

    List<UserProjectResponseDto> getUserProjectsFromUser(UUID userId);

    List<UserProjectResponseDto>  getUserProjectsFromProject(UUID projectId);

    UserProjectResponseDto getUserProjectByUserAndProject(UUID userId, UUID projectId);

    UserProjectResponseDto setUserProjectRole( UUID userId, UUID projectId,InProjectUserRole inProjectUserRole );

    UserProjectResponseDto setUserInProject(UUID userId, UUID projectId,String projectRole);

    UserProjectResponseDto deleteUserInProject(UUID projectId, String userName);

    boolean isUserLeadInProject(UUID userId, UUID projectId);
}
