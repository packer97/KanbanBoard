package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.exception.TaskNotFoundException;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.rest.dto.TaskRequestDto;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.service.TaskService;
import com.group_3.kanbanboard.service.entity.EntityNewService;
import com.group_3.kanbanboard.service.entity.TaskEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskEntityServiceImpl taskEntityService;
    private final TaskMapper taskMapper;
    private final ResourceBundle res = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());


    @Autowired
    public TaskServiceImpl(TaskEntityServiceImpl taskEntityService, TaskMapper taskMapper) {
        this.taskEntityService = taskEntityService;
        this.taskMapper = taskMapper;
    }

    @Transactional
    @Override
    public TaskResponseDto getById(UUID id) {
        return taskMapper.toResponseDto(taskEntityService.getEntity(id));
    }

    @Transactional
    @Override
    public List<TaskResponseDto> getAllTasks() {
        List<TaskEntity> tasks = taskEntityService.getAllEntity();
        return tasks.stream().map(taskMapper::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TaskResponseDto addTask(TaskRequestDto taskRequestDto) {
        TaskEntity task = taskMapper.toEntity(taskRequestDto);
        TaskEntity savedTask = taskEntityService.saveEntity(task);
        return taskMapper.toResponseDto(savedTask);


    }

    @Transactional
    @Override
    public TaskResponseDto updateTask(UUID id, TaskRequestDto taskRequestDto) {
        TaskEntity taskFromDb = taskEntityService.getEntity(id);
        TaskEntity taskFromDto = taskMapper.toEntity(taskRequestDto);
        taskFromDto.setId(taskFromDb.getId());
        TaskEntity savedTask = taskEntityService.saveEntity(taskFromDto);

        return taskMapper.toResponseDto(taskFromDto);

    }

    @Transactional
    @Override
    public void deleteTask(UUID id) {
        if (!taskEntityService.exists(id))
            throw new TaskNotFoundException(String.format(res.getString("task.notFound"), id));
        taskEntityService.deleteById(id);
    }
}
