package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.TaskCategory;
import com.group_3.kanbanboard.enums.TaskStatus;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.rest.dto.TaskRequestDto;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.ProjectService;
import com.group_3.kanbanboard.service.ReleaseService;
import com.group_3.kanbanboard.service.TaskService;
import com.group_3.kanbanboard.service.impl.ModelViewProjectService;
import com.group_3.kanbanboard.service.impl.ModelViewTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/projects/{projectId}/releases/{releaseId}/tasks")
public class ModelViewTaskController {

    private final ModelViewTaskService modelViewTaskService;
    private final ModelViewProjectService modelViewProjectService;
    private final PrincipalService principalService;
    private final TaskService taskService;
    private final ReleaseService releaseService;


    @Autowired
    public ModelViewTaskController(ModelViewTaskService modelViewTaskService,
                                   ModelViewProjectService modelViewProjectService, PrincipalService principalService,
                                   TaskService taskService, ProjectService projectService, ReleaseService releaseService) {
        this.modelViewTaskService = modelViewTaskService;
        this.modelViewProjectService = modelViewProjectService;
        this.principalService = principalService;
        this.taskService = taskService;
        this.releaseService = releaseService;
    }


    @GetMapping
    public String getTasksFromProjectAndRelease(@PathVariable UUID projectId,
                                                @PathVariable UUID releaseId,
                                                Model model) {
        List<TaskResponseDto> taskResponseDtoList =
                modelViewTaskService.getTasksFromProjectAndRelease(projectId, releaseId);
        model.addAttribute("tasksList", taskResponseDtoList);

        ReleaseResponseDto releaseResponseDto = releaseService.getById(releaseId);
        model.addAttribute("release", releaseResponseDto);

        return "taskList";
    }

    @GetMapping("/{taskId}")
    public String getDistinctTaskById(@PathVariable UUID projectId,
                                      @PathVariable UUID releaseId,
                                      @PathVariable UUID taskId,
                                      Model model) {
        TaskResponseDto distinctTask = modelViewTaskService
                .getTaskByIdFromProjectAndRelease(taskId, projectId, releaseId);

        Date date = distinctTask.getEndDate();


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
                             @PathVariable UUID projectId,
                             @PathVariable UUID releaseId,
                             @RequestParam String projectUserSelect,
                             @ModelAttribute TaskRequestDto taskRequestDto,
                             Model model) {

        modelViewTaskService.setDependenciesAndSave(taskId, projectUserSelect, projectId, releaseId, taskRequestDto);

        TaskResponseDto distinctTask = modelViewTaskService
                .getTaskByIdFromProjectAndRelease(taskId, projectId, releaseId);
        model.addAttribute("distinctTask", distinctTask);

        List<UserResponseDto> projectUsers = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("projectUsers", projectUsers);

        return "taskDetail";
    }

    @ModelAttribute("userAsPrincipal")
    public UserResponseDto getUserAsPrincipal() {
        return principalService.getPrincipal();
    }


    @ModelAttribute("categories")
    public TaskCategory[] getTaskCategories() {
        return TaskCategory.values();
    }

    @ModelAttribute("statuses")
    public TaskStatus[] getTaskStatuses() {
        return TaskStatus.values();
    }

    @InitBinder
    public void bindingDate(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }
}