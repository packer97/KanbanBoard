package com.group_3.kanbanboard.rest.dto;

public class UnfinishedTasksDto {

  private Long quantity;

  public UnfinishedTasksDto() {
  }

  public UnfinishedTasksDto(Long quantity) {
    this.quantity = quantity;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

}
