package com.group_3.kanbanboard.entity;

import com.group_3.kanbanboard.enums.ReleaseStatus;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "release")
public class ReleaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column
  private String version;

  private Date startDate;

  private Date endDate;

  @ManyToOne //......
  private ProjectEntity project;

  @OneToMany //......
  private List<TaskEntity> tasks;

  @Enumerated(EnumType.STRING)
  private ReleaseStatus status;

  public ReleaseEntity() {
  }

  public ReleaseEntity(UUID id, String version, Date startDate, Date endDate, ProjectEntity project,
      List<TaskEntity> tasks, ReleaseStatus status) {
    this.id = id;
    this.version = version;
    this.startDate = startDate;
    this.endDate = endDate;
    this.project = project;
    this.tasks = tasks;
    this.status = status;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public ProjectEntity getProject() {
    return project;
  }

  public void setProject(ProjectEntity project) {
    this.project = project;
  }

  public List<TaskEntity> getTasks() {
    return tasks;
  }

  public void setTasks(List<TaskEntity> tasks) {
    this.tasks = tasks;
  }

  public ReleaseStatus getStatus() {
    return status;
  }

  public void setStatus(ReleaseStatus status) {
    this.status = status;
  }
}
