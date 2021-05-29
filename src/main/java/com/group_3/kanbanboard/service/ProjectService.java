package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.rest.dto.ProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectResponseDto getById(UUID id);

    List<ProjectResponseDto> getAllProjects();

    ProjectResponseDto addProject(UUID userId, ProjectRequestDto projectRequestDto);

    ProjectResponseDto updateProject(UUID id, ProjectRequestDto ProjectRequestDto);

    void deleteProjectById(UUID id);

}
