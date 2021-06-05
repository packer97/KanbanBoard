package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.enums.TaskCategory;
import com.group_3.kanbanboard.enums.TaskStatus;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.repository.TaskFilterRepository;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.group_3.kanbanboard.specification.TaskSpecification.categoryOrStatusContainsIgnoreCase;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
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
    private TaskFilterRepository taskRepository;
    @InjectMocks
    private TaskMapper taskMapper= Mappers.getMapper(TaskMapper.class);

    private TaskEntity expectedTask;
    private TaskEntity expectedTask1;

    @Test
    public void testGetFilterTasks() {
        expectedTask = new TaskEntity(TITLE, DESCRIPTION, END_DATE, TASK_CATEGORY, TASK_STATUS,
                new UserEntity(), new ProjectEntity(), new ReleaseEntity());
        expectedTask1 = new TaskEntity(TITLE, DESCRIPTION, END_DATE, TASK_CATEGORY_1, TASK_STATUS,
                new UserEntity(), new ProjectEntity(), new ReleaseEntity());
        Specification<TaskEntity> searchSpec = categoryOrStatusContainsIgnoreCase(TASK_CATEGORY.name());
        when(taskRepository.findAll(searchSpec)).thenReturn(Arrays.asList(expectedTask, expectedTask1));

        List<TaskResponseDto> actualTask = Stream.of(expectedTask, expectedTask1)
                .map(taskMapper::toResponseDto).collect(
                        Collectors.toList());

        Assertions.assertEquals(taskService.findBySearchTerm(TASK_CATEGORY.name()).get(0).getTaskCategory().name(),
                actualTask.get(0).getTaskCategory().name());


    }
}