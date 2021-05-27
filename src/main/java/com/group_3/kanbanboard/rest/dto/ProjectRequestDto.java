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

    public ProjectRequestDto(String title, String description, UUID leadId) {
        this.title = title;
        this.description = description;
        this.leadId = leadId;
    }

    public ProjectRequestDto() {}

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
}
