package com.group_3.kanbanboard.rest.dto;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.enums.ReleaseStatus;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class ReleaseResponseDto {

  private UUID id;

  private String version;

  private Date startDate;

  private Date endDate;

  private ReleaseStatus status;

  private ProjectEntity project;

  private List<TaskEntity> tasks;

  public ReleaseResponseDto(){}

  public ReleaseResponseDto(UUID id, String version, Date startDate, Date endDate,
      ReleaseStatus status, ProjectEntity project,
      List<TaskEntity> tasks) {
    this.id = id;
    this.version = version;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.project = project;
    this.tasks = tasks;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ProjectEntity getProject() {
    return project;
  }

  public void setProject(ProjectEntity project) {
    this.project = project;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public ReleaseStatus getStatus() {
    return status;
  }

  public void setStatus(ReleaseStatus status) {
    this.status = status;
  }

  public List<TaskEntity> getTasks() {
    return tasks;
  }

  public void setTasks(List<TaskEntity> tasks) {
    this.tasks = tasks;
  }
}
