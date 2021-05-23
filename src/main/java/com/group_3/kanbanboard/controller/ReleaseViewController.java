package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.exception.ForbiddenException;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.ProjectService;
import com.group_3.kanbanboard.service.ReleaseService;
import com.group_3.kanbanboard.service.UserProjectService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects/{projectId}/releases")
public class ReleaseViewController {

  private final PrincipalService principalService;
  private final ReleaseService releaseService;
  private final ProjectService projectService;
  private final UserProjectService userProjectService;

  @Autowired
  public ReleaseViewController(PrincipalService principalService,
      ReleaseService releaseService, ProjectService projectService,
      UserProjectService userProjectService) {
    this.principalService = principalService;
    this.releaseService = releaseService;
    this.projectService = projectService;
    this.userProjectService = userProjectService;
  }

  @GetMapping
  public ModelAndView getProjectReleasesPage(@PathVariable UUID projectId) {

    boolean isLead = checkAccess(projectId);

    ProjectResponseDto projectResponseDto = projectService.getById(projectId);
    projectResponseDto.getReleases().sort(new Comparator<ReleaseEntity>() {
      @Override
      public int compare(ReleaseEntity o1, ReleaseEntity o2) {
        return o1.getStartDate().compareTo(o2.getStartDate());
      }
    });

    ModelAndView modelAndView = new ModelAndView("releases/releaseListPage");
    modelAndView.addObject("projectDTO", projectResponseDto);
    modelAndView.addObject("projectId", projectId);
    modelAndView.addObject("isLead", isLead);

    return modelAndView;
  }

  @GetMapping("/{releaseId}")
  public ModelAndView getReleasePage(@PathVariable UUID releaseId, @PathVariable UUID projectId) {
    ReleaseResponseDto releaseResponseDto = releaseService.getById(releaseId);

    ModelAndView modelAndView = new ModelAndView("releases/releasePage");
    modelAndView.addObject("releaseDto", releaseResponseDto);
    return modelAndView;
  }

  @GetMapping("/new")
  public ModelAndView getAddReleasePage(@PathVariable UUID projectId) {
    checkAccess(projectId);

    ModelAndView modelAndView = new ModelAndView("releases/newReleasePage");
    modelAndView.addObject("projectId", projectId);
    return modelAndView;
  }

  @PostMapping
  public String addReleaseToProject(String version, String formStartDate, String formEndDate,
      @PathVariable UUID projectId) throws ParseException {
    checkAccess(projectId);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    ReleaseRequestDto releaseRequestDto = new ReleaseRequestDto();
    releaseRequestDto.setProjectId(projectId);
    releaseRequestDto.setVersion(version);

    releaseRequestDto.setStartDate(simpleDateFormat.parse(formStartDate));
    releaseRequestDto.setEndDate(simpleDateFormat.parse(formEndDate));

    releaseService.addRelease(releaseRequestDto);

    return "redirect:/projects/{projectId}/releases";
  }

  @DeleteMapping("/{releaseId}")
  public String deleteRelease(@PathVariable UUID releaseId, @PathVariable UUID projectId) {
    checkAccess(projectId);

    releaseService.deleteReleaseById(releaseId);
    return "redirect:/projects/{projectId}/releases";
  }

  @GetMapping("/{releaseId}/edit")
  public ModelAndView getEditReleasePage(@PathVariable UUID releaseId,
      @PathVariable UUID projectId) {
    checkAccess(projectId);

    ReleaseResponseDto releaseDto = releaseService.getById(releaseId);
    ModelAndView modelAndView = new ModelAndView("releases/editReleasePage");
    modelAndView.addObject("releaseDto", releaseDto);

    return modelAndView;
  }

  @PatchMapping("/{releaseId}")
  public String updateRelease(@PathVariable UUID projectId, @PathVariable UUID releaseId,
      @RequestBody ReleaseRequestDto releaseRequestDto) {
    checkAccess(projectId);

    releaseService.updateRelease(releaseId, releaseRequestDto);

    return "redirect:/projects/{projectId}/releases";
  }

  private boolean checkAccess(UUID projectId) {
    boolean isLead = userProjectService
        .isUserLeadInProject(principalService.getPrincipalId(), projectId);
    if (!isLead) {
      throw new ForbiddenException("Error! You are not a lead to continue your actions!");
    }
    return true;
  }


}
