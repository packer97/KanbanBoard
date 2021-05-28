package com.group_3.kanbanboard.rest.dto;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.enums.InProjectUserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Пользователь и Проект")
public class UserProjectRequestDto {
    @Schema(description = "Пользователь")
    private UserEntity user;
    @Schema(description = "Проект")
    private ProjectEntity project;
    @Schema(description = "Роль")
    private InProjectUserRole projectUserRole;

    public UserProjectRequestDto() {
    }

    public UserProjectRequestDto(UserEntity user, ProjectEntity project, InProjectUserRole projectUserRole) {
        this.user = user;
        this.project = project;
        this.projectUserRole = projectUserRole;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public InProjectUserRole getProjectUserRole() {
        return projectUserRole;
    }

    public void setProjectUserRole(InProjectUserRole projectUserRole) {
        this.projectUserRole = projectUserRole;
    }
}
