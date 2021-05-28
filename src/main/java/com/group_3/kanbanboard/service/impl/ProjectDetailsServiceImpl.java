package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.UserService;
import com.group_3.kanbanboard.service.entity.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectDetailsServiceImpl {
    private final EntityService entityService;
    private final UserService userService;

    @Autowired
    public ProjectDetailsServiceImpl(EntityService entityService,
                                     UserService userService) {
        this.entityService = entityService;
        this.userService = userService;;
    }

    public String getLeadNameFromProject(UUID projectId) {
        ProjectEntity needProject = entityService.getProjectEntity(projectId);
        UserResponseDto userResponseDto = userService.getUserById(needProject.getLeadId());
        return String.format("%s %s", userResponseDto.getFirstName(), userResponseDto.getSecondName());
    }

}
