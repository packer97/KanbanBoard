package com.group_3.kanbanboard.rest.dto;

import com.group_3.kanbanboard.enums.ReleaseStatus;
import java.util.Date;
import java.util.UUID;

public class ReleaseRequestDto {

  private UUID projectId;

  private String version;

  private Date startDate;
  private Date endDate;

  private ReleaseStatus status;

  public ReleaseRequestDto() {
  }

  public ReleaseRequestDto(UUID projectId, String version, Date startDate, Date endDate,
      ReleaseStatus status) {
    this.projectId = projectId;
    this.version = version;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  public UUID getProjectId() {
    return projectId;
  }

  public void setProjectId(UUID projectId) {
    this.projectId = projectId;
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

}
