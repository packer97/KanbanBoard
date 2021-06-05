package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.rest.dto.TaskResponseDto;

import java.util.List;

public interface TaskFilterService {
    List<TaskResponseDto> getAllTasks(String keyword);
}
