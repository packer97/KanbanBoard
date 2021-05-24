package com.group_3.kanbanboard.rest.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.UUID;

@Schema(description = "Задача")
public class TaskResponseDto {
    @Schema(description = "Идентификатор задачи")
    private UUID id;

    @Schema(description = "Название задачи")
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Дата окончания")
    private Date endDate;

    @Schema(description = "Категория задачи")
    private String taskCategory;

    @Schema(description = "Статус задачи")
    private String taskStatus;

    @Schema(description = "Исполнитель задачи")
    private UserResponseDto performer;

    @Schema(description = "Проект")
    private ProjectResponseDto project;

    @Schema(description = "Версия релиза")
    private ReleaseResponseDto release;

    public TaskResponseDto(UUID id,
                           String title,
                           String description,
                           Date endDate,
                           String taskCategory,
                           String taskStatus,
                           UserResponseDto performer,
                           ProjectResponseDto project,
                           ReleaseResponseDto release) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.taskCategory = taskCategory;
        this.taskStatus = taskStatus;
        this.performer = performer;
        this.project = project;
        this.release = release;
    }

    public TaskResponseDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UserResponseDto getPerformer() {
        return performer;
    }

    public void setPerformer(UserResponseDto performer) {
        this.performer = performer;
    }

    public ProjectResponseDto getProject() {
        return project;
    }

    public void setProject(ProjectResponseDto project) {
        this.project = project;
    }

    public ReleaseResponseDto getRelease() {
        return release;
    }

    public void setRelease(ReleaseResponseDto release) {
        this.release = release;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}

