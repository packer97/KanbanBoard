package com.group_3.kanbanboard.rest;

import com.group_3.kanbanboard.rest.dto.ProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.service.impl.ProjectServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Проекты")
@RequestMapping("/api/projects")
@RestController
public class ProjectController {

    private final ProjectServiceImpl projectService;

    @Autowired
    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Добавить проект")
    @PostMapping
    public ResponseEntity<ProjectResponseDto> addProject(@PathVariable UUID userId, @RequestBody ProjectRequestDto projectRequestDto) {
        return ResponseEntity.ok(projectService.addProject(userId, projectRequestDto));
    }

    @Operation(summary = "Получить список всех проектов")
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        return ResponseEntity.ok().body(projectService.getAllProjects());
    }

    @Operation(summary = "Получение проекта по id")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(projectService.getById(id));
    }

    @Operation(summary = "Обновление проекта")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable UUID id,
                                                            @RequestBody ProjectRequestDto projectRequestDto) {
        return ResponseEntity.ok().body(projectService.updateProject(id, projectRequestDto));
    }

    @Operation(summary = "Удаление проекта")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable UUID id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Завершение проекта")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> endProject(@PathVariable UUID id) {
        return ResponseEntity.ok().body(projectService.setProjectStatusEnd(id));
    }

}
