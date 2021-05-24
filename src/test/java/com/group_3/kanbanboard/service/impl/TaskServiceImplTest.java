package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.enums.TaskCategory;
import com.group_3.kanbanboard.enums.TaskStatus;
import com.group_3.kanbanboard.exception.TaskNotFoundException;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.mappers.TaskMapperImpl;
import com.group_3.kanbanboard.repository.TaskRepository;
import com.group_3.kanbanboard.rest.dto.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {


    private static final UUID ID = UUID.randomUUID();
    private static final String TITLE = "test_task";
    private static final String DESCRIPTION = "description of test_task";
    private static final Date END_DATE = new Date(System.currentTimeMillis() + 86400000);
    private static final TaskCategory TASK_CATEGORY = TaskCategory.OTHER;
    private static final TaskStatus TASK_STATUS = TaskStatus.IN_PROGRESS;

    @InjectMocks
    private TaskServiceImpl taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    private TaskEntity expectedTask;
    private TaskRequestDto expectedTAskRequestDto;

    @Before
    public void setUp() {

        expectedTask = new TaskEntity(TITLE,
                DESCRIPTION,
                END_DATE,
                TASK_CATEGORY,
                TASK_STATUS,
                new UserEntity(),
                new ProjectEntity(),
                new ReleaseEntity());

        expectedTAskRequestDto = new TaskRequestDto(TITLE,
                DESCRIPTION,
                END_DATE,
                TASK_CATEGORY.name(),
                TASK_STATUS.name(),
                new UserResponseDto(),
                new ProjectResponseDto(),
                new ReleaseResponseDto());

    }

    @After
    public void tearDown() {
        expectedTask = null;
        expectedTAskRequestDto = null;
    }

    @Test
    public void getById() {
        Mockito.when(taskRepository.findById(Mockito.any())).thenReturn(Optional.of(expectedTask));
        reMapper();
        TaskResponseDto actualTaskResponseDto = taskService.getById(ID);

        Assert.assertEquals(expectedTask.getDescription(), actualTaskResponseDto.getDescription());
        Assert.assertEquals(expectedTask.getTitle(), actualTaskResponseDto.getTitle());
    }

    @Test(expected = TaskNotFoundException.class)
    public void getByIdExc() {
        TaskResponseDto actualTaskResponseDto = taskService.getById(ID);

    }

    @Test
    public void getAllTasks() {
        List<TaskEntity> expectedTasks = Collections.singletonList(expectedTask);
        Mockito.when(taskRepository.findAll()).thenReturn(expectedTasks);
        reMapper();
        List<TaskResponseDto> actualListTaskResponseDto = taskService.getAllTasks();

        Assert.assertEquals(expectedTasks.size(), actualListTaskResponseDto.size());
        Assert.assertEquals(expectedTasks.get(0).getTitle(), actualListTaskResponseDto.get(0).getTitle());
        Assert.assertEquals(expectedTasks.get(0).getDescription(), actualListTaskResponseDto.get(0).getDescription());


    }

    @Test
    public void addTask() {
        Mockito.when(taskRepository.save(expectedTask)).thenReturn(expectedTask);
        reMapper();
        TaskResponseDto actualTaskResponseDto = taskService.addTask(expectedTAskRequestDto);

        Assert.assertEquals(expectedTAskRequestDto.getTitle(), actualTaskResponseDto.getTitle());
        Assert.assertEquals(expectedTAskRequestDto.getDescription(), actualTaskResponseDto.getDescription());
    }

    @Test
    public void updateTask() {
        Mockito.when(taskRepository.findById(Mockito.any())).thenReturn(Optional.of(expectedTask));
        reMapper();
        TaskResponseDto actualTaskResponseDto = taskService.updateTask(ID, expectedTAskRequestDto);

        Assert.assertEquals(expectedTAskRequestDto.getDescription(), actualTaskResponseDto.getDescription());
        Assert.assertEquals(expectedTAskRequestDto.getTitle(), actualTaskResponseDto.getTitle());
    }

    @Test(expected = TaskNotFoundException.class)
    public void updateTaskExc() {
        TaskResponseDto actualTaskResponseDto = taskService.updateTask(ID, expectedTAskRequestDto);
    }

    @Test
    public void deleteTask() {
        UUID id = Mockito.any();
        Mockito.when(taskRepository.existsById(id)).thenReturn(Boolean.TRUE);
    }

    @Test(expected = TaskNotFoundException.class)
    public void deleteTaskExc() {
        taskService.deleteTask(ID);
    }

    private void reMapper() {
        Mockito.when(taskMapper.toResponseDto(Mockito.any(TaskEntity.class)))
                .thenAnswer(invocation -> new TaskMapperImpl().toResponseDto(invocation.<TaskEntity>getArgument(0)));

        Mockito.when(taskMapper.toEntity(Mockito.any(TaskRequestDto.class)))
                .thenAnswer(invocation -> new TaskMapperImpl().toEntity(invocation.<TaskRequestDto>getArgument(0)));
    }
}