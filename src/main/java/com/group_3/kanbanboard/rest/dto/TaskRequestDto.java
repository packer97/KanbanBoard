package com.group_3.kanbanboard.rest.dto;

import com.group_3.kanbanboard.enums.TaskCategory;
import com.group_3.kanbanboard.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Schema(description = "Задача")
public class TaskRequestDto {
    @Schema(description = "Название задачи")
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Дата окончания")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Schema(description = "Категория задачи")
    private TaskCategory taskCategory;

    @Schema(description = "Статус задачи")
    private TaskStatus taskStatus;

    @Schema(description = "Исполнитель задачи")
    private UserResponseDto performer;

    @Schema(description = "Проект")
    private ProjectResponseDto project;

    @Schema(description = "Версия релиза")
    private ReleaseResponseDto release;

    public TaskRequestDto() {
    }

    public TaskRequestDto(String title,
                          String description,
                          Date endDate,
                          TaskCategory taskCategory,
                          TaskStatus taskStatus,
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}


