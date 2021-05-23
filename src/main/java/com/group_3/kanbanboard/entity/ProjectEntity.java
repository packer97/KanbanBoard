package com.group_3.kanbanboard.entity;

import java.util.List;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String title;
    @Column
    private String description;
    @Column(name = "lead_id")
    private UUID leadId;
    @Column(name = "start_project")
    private Boolean startProject = false;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserProjectEntity> users = new ArrayList<>();

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ReleaseEntity> releases;

    public ProjectEntity() {
    }

    public ProjectEntity(String title, String description, UUID leadId) {
        this.title = title;
        this.description = description;
        this.leadId = leadId;
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

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }

    public boolean isStartProject() {
        return startProject;
    }

    public void setStartProject(boolean startProject) {
        this.startProject = startProject;
    }

    public List<UserProjectEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserProjectEntity> users) {
        this.users = users;
    }

    public List<ReleaseEntity> getReleases() {
        return releases;
    }

    public void setReleases(List<ReleaseEntity> releases) {
        this.releases = releases;
    }

}
