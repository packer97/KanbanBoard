package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.rest.dto.ProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.ProjectService;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UtilService;
import com.group_3.kanbanboard.service.impl.ProjectDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/projects")
public class ModelViewProjectController {
    private final PrincipalService principalService;
    private final ProjectService projectService;
    private final UtilService utilService;
    private final UserProjectService userProjectService;
    private final ProjectDetailsServiceImpl projectDetailsService;

    public ModelViewProjectController(PrincipalService principalService, ProjectService projectService,
                                      UtilService utilService, UserProjectService userProjectService,
                                      ProjectDetailsServiceImpl projectDetailsService) {
        this.principalService = principalService;
        this.projectService = projectService;
        this.utilService = utilService;
        this.userProjectService = userProjectService;
        this.projectDetailsService = projectDetailsService;
    }

    @GetMapping
    public String getUserProjectsPage(Model model) {
        List<UserProjectResponseDto> listProject =
                userProjectService.getUserProjectsFromUser(principalService.getPrincipalId());
        model.addAttribute("listProject", listProject);

        return "projects/projectListPage";
    }

    @GetMapping("/{projectId}")
    public String getProjectPage(@PathVariable UUID projectId,
                                 Model model) {
        boolean isLead = userProjectService.isUserLeadInProject(principalService.getPrincipalId(), projectId);
        model.addAttribute("projectDto", projectService.getById(projectId));
        model.addAttribute("leadName", projectDetailsService.getLeadNameFromProject(projectId));
        model.addAttribute("isLead", isLead);
        return "projects/projectPage";
    }

    @GetMapping("/new")
    public String newProject(@ModelAttribute("projectDto")
                                         ProjectResponseDto projectResponseDto) {
        return "projects/newProjectPage";
    }

    @PostMapping
    public String addProjectToUser(@ModelAttribute("projectDto")
                                               ProjectRequestDto projectRequestDto) {
        UUID principalId = principalService.getPrincipalId();
        projectRequestDto.setLeadId(principalId);
        projectService.addProject(principalId, projectRequestDto);

        return "redirect:/projects";
    }

    @DeleteMapping("/{projectId}")
    public String deleteProject(@PathVariable UUID projectId) {
        utilService.checkLeadAccess(projectId);

        projectService.deleteProjectById(projectId);
        return "redirect:/projects";
    }

    @GetMapping("/{projectId}/edit")
    public String getEditProjectPage(@PathVariable UUID projectId, Model model) {
        utilService.checkLeadAccess(projectId);

        ProjectResponseDto projectDto = projectService.getById(projectId);
        model.addAttribute("projectDto", projectDto);

        return "projects/editProjectPage";
    }

    @PatchMapping("/{projectId}")
    public String updateProject(@ModelAttribute("projectDto")
                                ProjectRequestDto projectRequestDto,
                                @PathVariable UUID projectId) {
        utilService.checkLeadAccess(projectId);
        projectService.updateProject(projectId, projectRequestDto);

        return "redirect:/projects/{projectId}";
    }
}
