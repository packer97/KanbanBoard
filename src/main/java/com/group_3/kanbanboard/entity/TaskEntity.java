package com.group_3.kanbanboard.entity;


import com.group_3.kanbanboard.enums.TaskCategory;
import com.group_3.kanbanboard.enums.TaskStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 64)
    private String title;

    @Column
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "task_category")
    @Enumerated(EnumType.STRING)
    private TaskCategory taskCategory;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @ManyToOne()
    private UserEntity performer;

    @ManyToOne()
    private ProjectEntity project;

    @ManyToOne()
    private ReleaseEntity release;

    public TaskEntity() {
    }

    public TaskEntity(String title,
                      String description,
                      Date endDate,
                      TaskCategory taskCategory,
                      TaskStatus taskStatus,
                      UserEntity performer,
                      ProjectEntity project,
                      ReleaseEntity release) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.taskCategory = taskCategory;
        this.taskStatus = taskStatus;
        this.performer = performer;
        this.project = project;
        this.release = release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public UserEntity getPerformer() {
        return performer;
    }

    public void setPerformer(UserEntity user) {
        this.performer = user;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public ReleaseEntity getRelease() {
        return release;
    }

    public void setRelease(ReleaseEntity release) {
        this.release = release;
    }

}
