package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.mappers.TaskMapper;
import com.group_3.kanbanboard.repository.TaskFilterRepository;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.service.TaskFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.group_3.kanbanboard.specification.TaskSpecification.categoryOrStatusContainsIgnoreCase;

@Service
public class TaskFilterServiceImpl implements TaskFilterService {
    private final TaskFilterRepository taskFilterRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskFilterServiceImpl(TaskFilterRepository taskFilterRepository, TaskMapper taskMapper) {
        this.taskFilterRepository = taskFilterRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskResponseDto> findBySearchTerm(String searchTerm) {
        Specification<TaskEntity> searchSpec = categoryOrStatusContainsIgnoreCase(searchTerm);
        List<TaskEntity> searchResults = taskFilterRepository.findAll(searchSpec);
        return searchResults.stream().map(taskMapper::toResponseDto).collect(Collectors.toList());
    }
}
