package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.ReleaseStatus;
import com.group_3.kanbanboard.exception.FormInputException;
import com.group_3.kanbanboard.rest.dto.*;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.ProjectService;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UtilService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/projects")
public class ModelViewProjectController {

    private final PrincipalService principalService;
    private final UserProjectService userProjectService;
    private final ProjectService projectService;
    private final UtilService utilService;

    public ModelViewProjectController(PrincipalService principalService, UserProjectService userProjectService,
                                      ProjectService projectService, UtilService utilService) {
        this.principalService = principalService;
        this.userProjectService = userProjectService;
        this.projectService = projectService;
        this.utilService = utilService;
    }

    @GetMapping
    public ModelAndView getUserProjectsPage() {

        List<UserProjectResponseDto> userProjectResponseDtos = userProjectService
                .getUserProjectsFromUser(principalService.getPrincipalId());

        ModelAndView modelAndView = new ModelAndView("projects/projectListPage");
        modelAndView.addObject("projectList", userProjectResponseDtos);

        return modelAndView;
    }

    @GetMapping("/{projectId}")
    public ModelAndView getProjectPage(@PathVariable UUID projectId) {
        ProjectResponseDto projectResponseDto = projectService.getById(projectId);

        ModelAndView modelAndView = new ModelAndView("projects/projectPage");
        modelAndView.addObject("projectDto", projectResponseDto);
        return modelAndView;
    }

    @PostMapping
    public String addProjectToUser(String title, String description) throws ParseException {

        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setLeadId(principalService.getPrincipalId());
        projectRequestDto.setTitle(title);
        projectRequestDto.setDescription(description);

        projectService.addProject(principalService.getPrincipalId(), projectRequestDto);

        return "redirect:/projects";
    }

    @DeleteMapping("/{projectId}")
    public String deleteProject(@PathVariable UUID projectId) {
        utilService.checkLeadAccess(projectId);

        projectService.deleteProjectById(projectId);
        return "redirect:/projects";
    }

    @GetMapping("/{projectId}/edit")
    public ModelAndView getEditProjectPage(@PathVariable UUID projectId) {
        utilService.checkLeadAccess(projectId);

        ProjectResponseDto projectDto = projectService.getById(projectId);
        ModelAndView modelAndView = new ModelAndView("projects/editProjectPage");
        modelAndView.addObject("projectDto", projectDto);

        return modelAndView;
    }
    @PatchMapping("/{projectId}")
    public String updateRelease(@PathVariable UUID projectId, ProjectRequestDto projectRequestDto,
                                String title, String description)
            throws ParseException {
        utilService.checkLeadAccess(projectId);

        projectRequestDto.setTitle(title);
        projectRequestDto.setDescription(description);

        projectService.updateProject(projectId, projectRequestDto);

        return "redirect:/projects/{projectId}";
    }

}
