package com.group_3.kanbanboard.rest.dto;

import com.group_3.kanbanboard.entity.ReleaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
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
    @Schema(description = "Релизы")
    private List<ReleaseEntity> releases;

    public ProjectRequestDto(String title, String description, UUID leadId,
        Boolean startProject, List<ReleaseEntity> releases) {
        this.title = title;
        this.description = description;
        this.leadId = leadId;
        this.startProject = startProject;
        this.releases = releases;
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

    public List<ReleaseEntity> getReleases() {
        return releases;
    }

    public void setReleases(List<ReleaseEntity> releases) {
        this.releases = releases;
    }
}
