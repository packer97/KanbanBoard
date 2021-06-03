package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.TaskCategory;
import com.group_3.kanbanboard.enums.TaskStatus;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.rest.dto.TaskRequestDto;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.*;
import com.group_3.kanbanboard.service.impl.ModelViewProjectService;
import com.group_3.kanbanboard.service.impl.ModelViewTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects/{projectId}/releases/{releaseId}/tasks")
public class ModelViewTaskController {

    private final ModelViewTaskService modelViewTaskService;
    private final ModelViewProjectService modelViewProjectService;
    private final PrincipalService principalService;
    private final TaskService taskService;
    private final ReleaseService releaseService;
    private final UtilService utilService;


    @Autowired
    public ModelViewTaskController(ModelViewTaskService modelViewTaskService,
                                   ModelViewProjectService modelViewProjectService,
                                   PrincipalService principalService,
                                   TaskService taskService,
                                   ProjectService projectService,
                                   ReleaseService releaseService,
                                   UtilService utilService) {
        this.modelViewTaskService = modelViewTaskService;
        this.modelViewProjectService = modelViewProjectService;
        this.principalService = principalService;
        this.taskService = taskService;
        this.releaseService = releaseService;
        this.utilService = utilService;
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

        return "tasks/taskList";
    }

    @GetMapping(params = "search")
    public String searchTasks(@PathVariable UUID projectId,
                              @PathVariable UUID releaseId,
                              @RequestParam String search,
                              Model model){

        List<TaskResponseDto> taskResponseDtoList =
                modelViewTaskService.getTasksFromProjectAndRelease(projectId, releaseId);

      List<TaskResponseDto> filterList = taskResponseDtoList.stream().filter( task -> task.getTitle().contains(search)).collect(Collectors.toList());
        model.addAttribute("tasksList", filterList);

        ReleaseResponseDto releaseResponseDto = releaseService.getById(releaseId);
        model.addAttribute("release", releaseResponseDto);

        return "tasks/taskList";
    }


    @GetMapping("/{taskId}")
    public String getDistinctTaskById(@PathVariable UUID projectId,
                                      @PathVariable UUID releaseId,
                                      @PathVariable UUID taskId,
                                      HttpServletRequest request,
                                      Model model) {

        boolean isAddTask = request.getRequestURI().contains("addTask");
        model.addAttribute("isAddTask", isAddTask);

        TaskResponseDto distinctTask = modelViewTaskService
                .getTaskByIdFromProjectAndRelease(taskId, projectId, releaseId);
        model.addAttribute("distinctTask", distinctTask);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("formattedEndDate", dateFormat.format(distinctTask.getEndDate()));

        List<UserResponseDto> projectUsers = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("projectUsers", projectUsers);

        return "tasks/taskDetail";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable UUID projectId,@PathVariable UUID taskId) {

        utilService.checkLeadAccess(projectId);
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

        utilService.checkLeadAccess(projectId);

        TaskResponseDto distinctTask =
                modelViewTaskService.setDependenciesAndSave(taskId, projectUserSelect, projectId, releaseId, taskRequestDto);
        model.addAttribute("distinctTask", distinctTask);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("formattedEndDate", dateFormat.format(distinctTask.getEndDate()));

        List<UserResponseDto> projectUsers = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("projectUsers", projectUsers);

        return "tasks/taskDetail";
    }

    @GetMapping("/addTask")
    public String openAddTask(@PathVariable UUID projectId,
                              @PathVariable UUID releaseId,
                              @ModelAttribute TaskRequestDto taskRequestDto,
                              HttpServletRequest request,
                              Model model) {

        boolean isAddTask = request.getRequestURI().contains("addTask");
        model.addAttribute("isAddTask", isAddTask);

        TaskResponseDto distinctTask = modelViewTaskService
                .setDependenciesAndGet(principalService.getPrincipal().getUsername(), projectId, releaseId, taskRequestDto);

        model.addAttribute("distinctTask", distinctTask);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("formattedEndDate", dateFormat.format(new Date()));

        List<UserResponseDto> projectUsers = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("projectUsers", projectUsers);

        return "tasks/taskDetail";
    }

    @PostMapping
    public String addTask(@PathVariable UUID projectId,
                          @PathVariable UUID releaseId,
                          @RequestParam String projectUserSelect,
                          @ModelAttribute TaskRequestDto taskRequestDto,
                          Model model){

        utilService.checkLeadAccess(projectId);

        TaskResponseDto distinctTask = modelViewTaskService
                .setDependenciesAndSave(projectUserSelect, projectId, releaseId, taskRequestDto);

        model.addAttribute("distinctTask", distinctTask);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("formattedEndDate", dateFormat.format(new Date()));

        List<UserResponseDto> projectUsers = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("projectUsers", projectUsers);

        return "redirect:/projects/{projectId}/releases/{releaseId}/tasks";

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