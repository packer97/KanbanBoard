package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.enums.TaskCategory;
import com.group_3.kanbanboard.enums.TaskStatus;
import com.group_3.kanbanboard.mappers.ProjectMapper;
import com.group_3.kanbanboard.mappers.ReleaseMapper;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.mappers.UserMapper;
import com.group_3.kanbanboard.repository.TaskRepository;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TaskFilterServiceTest {
    private static final String TITLE = "test_task";
    private static final String DESCRIPTION = "description of test_task";
    private static final Date END_DATE = new Date(System.currentTimeMillis() + 86400000);
    private static final TaskCategory TASK_CATEGORY = TaskCategory.FRONTEND;
    private static final TaskCategory TASK_CATEGORY_1 = TaskCategory.BACKEND;
    private static final TaskStatus TASK_STATUS = TaskStatus.IN_PROGRESS;

    @InjectMocks
    private TaskFilterServiceImpl taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private ReleaseMapper releaseMapper;
    @InjectMocks
    private final TaskMapper taskMapper= Mappers.getMapper(TaskMapper.class);

    @Test
    public void testGetFilterTasks() {
        TaskEntity expectedTask = new TaskEntity(TITLE, DESCRIPTION, END_DATE, TASK_CATEGORY, TASK_STATUS,
                new UserEntity(), new ProjectEntity(), new ReleaseEntity());
        expectedTask.setId(UUID.randomUUID());
        TaskEntity expectedTask1 = new TaskEntity(TITLE, DESCRIPTION, END_DATE, TASK_CATEGORY_1, TASK_STATUS,
                new UserEntity(), new ProjectEntity(), new ReleaseEntity());
        expectedTask1.setId(UUID.randomUUID());

        taskService = new TaskFilterServiceImpl(taskRepository, taskMapper);

        when(taskRepository.findAll(TASK_CATEGORY.name())).thenReturn(Collections.singletonList(expectedTask));
        when(taskRepository.findAll(TASK_CATEGORY_1.name())).thenReturn(Collections.singletonList(expectedTask1));
        when(taskRepository.findAll(TITLE)).thenReturn(Arrays.asList(expectedTask, expectedTask1));


        List<TaskResponseDto> actualTask = Stream.of(expectedTask, expectedTask1)
                .map(taskMapper::toResponseDto).collect(
                        Collectors.toList());

        Assertions.assertNotEquals(taskService.getAllTasks(TASK_CATEGORY.name()).get(0).getTaskCategory().name(),
                actualTask.get(1).getTaskCategory().name());
    }
}