package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.TaskStatus;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.rest.dto.TaskRequestDto;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.TaskService;
import com.group_3.kanbanboard.service.impl.ModelViewProjectService;
import com.group_3.kanbanboard.service.impl.ModelViewTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/projects/{projectId}/releases/{releaseId}/tasks")
public class ModelViewTaskController {

    private final ModelViewTaskService modelViewTaskService;
    private final ModelViewProjectService modelViewProjectService;
    private final PrincipalService principalService;
    private final TaskService taskService;


    @Autowired
    public ModelViewTaskController(ModelViewTaskService modelViewTaskService,
                                   ModelViewProjectService modelViewProjectService, PrincipalService principalService,
                                   TaskService taskService) {
        this.modelViewTaskService = modelViewTaskService;
        this.modelViewProjectService = modelViewProjectService;
        this.principalService = principalService;
        this.taskService = taskService;
    }


    @GetMapping
    public String getTasksFromProjectAndRelease(@PathVariable UUID projectId,
                                                @PathVariable UUID releaseId,
                                                Model model) {
        List<TaskResponseDto> taskResponseDtoList =
                modelViewTaskService.getTasksFromProjectAndRelease(projectId, releaseId);
        model.addAttribute("tasksList", taskResponseDtoList);

        ReleaseResponseDto releaseResponseDto = modelViewTaskService.getReleaseById(releaseId);
        model.addAttribute("release", releaseResponseDto);

        model.addAttribute("statuses", TaskStatus.values());

        return "taskList";
    }

    @GetMapping("/{taskId}")
    public String getDistinctTaskById(@PathVariable UUID projectId,
                                      @PathVariable UUID releaseId,
                                      @PathVariable UUID taskId,
                                      Model model) {
        TaskResponseDto distinctTask = modelViewTaskService
                .getTaskByIdFromProjectAndRelease(taskId, projectId, releaseId);

        model.addAttribute("distinctTask", distinctTask);

        List<UserResponseDto> projectUsers = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("projectUsers", projectUsers);

        return "taskDetail";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/projects/{projectId}/releases/{releaseId}/tasks";
    }

    @PutMapping("/{taskId}")
    public String updateTask(@PathVariable UUID taskId,
                             Model model) {
        TaskResponseDto previousTask = taskService.getById(taskId);
        model.addAttribute("distinctTask", previousTask);
        return "taskDetail";
    }

    @ModelAttribute("userAsPrincipal")
    public UserResponseDto getUserAsPrincipal() {
        return principalService.getPrincipal();
    }
}

