package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.UserService;
import com.group_3.kanbanboard.service.entity.ProjectEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectDetailsServiceImpl {
    private final ProjectEntityService projectEntityService;
    private final UserService userService;

    @Autowired
    public ProjectDetailsServiceImpl(ProjectEntityService projectEntityService,
                                     UserService userService) {
        this.projectEntityService = projectEntityService;
        this.userService = userService;

    }

    public String getLeadNameFromProject(UUID projectId) {
        ProjectEntity needProject = projectEntityService.getEntity(projectId);
        UserResponseDto userResponseDto = userService.getUserById(needProject.getLeadId());
        return String.format("%s %s", userResponseDto.getFirstName(), userResponseDto.getSecondName());
    }

}
