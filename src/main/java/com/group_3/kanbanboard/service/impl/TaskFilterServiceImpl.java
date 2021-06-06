package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.repository.TaskRepository;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.service.TaskFilterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TaskFilterServiceImpl implements TaskFilterService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    @Autowired
    public TaskFilterServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    @Override
    public List<TaskResponseDto> getAllTasks(String keyword) {
        List<TaskEntity> tasks;
        if (StringUtils.isEmpty(keyword)) {
            tasks = taskRepository.findAll();
        } else {
            tasks = taskRepository.findAll(keyword.toUpperCase(Locale.ROOT));
        }
        return tasks.stream().map(taskMapper::toResponseDto).collect(Collectors.toList());
    }
}
