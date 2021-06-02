package com.group_3.kanbanboard.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Проект")
public class ProjectRequestDto {

    @Schema(description = "Название проекта")
    private String title;

    @Schema(description = "Описание проекта")
    private String description;
    @Schema(description = "Создатель проекта")
    private UUID leadId;
    @Schema(description = "Старт проекта")
    private Boolean startProject;


    public ProjectRequestDto() {}

    public ProjectRequestDto(String title, String description, UUID leadId, Boolean startProject) {
        this.title = title;
        this.description = description;
        this.leadId = leadId;
        this.startProject = startProject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }

    public Boolean getStartProject() {
        return startProject;
    }

    public void setStartProject(Boolean startProject) {
        this.startProject = startProject;
    }
}
