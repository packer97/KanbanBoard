package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.exception.FormInputException;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/projects/{projectId}/releases")
public class ReleaseViewController {

  private final PrincipalService principalService;
  private final ReleaseService releaseService;
  private final ProjectService projectService;
  private final UserProjectService userProjectService;
  private final UtilService utilService;

  @Autowired
  public ReleaseViewController(PrincipalService principalService,
      ReleaseService releaseService, ProjectService projectService,
      UserProjectService userProjectService, UtilService utilService) {
    this.principalService = principalService;
    this.releaseService = releaseService;
    this.projectService = projectService;
    this.userProjectService = userProjectService;
    this.utilService = utilService;
  }

  @GetMapping
  public ModelAndView getProjectReleasesPage(@PathVariable UUID projectId) {

    boolean isLead = userProjectService
        .isUserLeadInProject(principalService.getPrincipalId(), projectId);

    ProjectResponseDto projectResponseDto = projectService.getById(projectId);
    projectResponseDto.getReleases().sort((o1, o2) -> o1.getStartDate().compareTo(o2.getEndDate()));

    ModelAndView modelAndView = new ModelAndView("releases/releaseListPage");
    modelAndView.addObject("projectDTO", projectResponseDto);
    modelAndView.addObject("projectId", projectId);
    modelAndView.addObject("isLead", isLead);

    return modelAndView;
  }

  @GetMapping("/{releaseId}")
  public ModelAndView getReleasePage(@PathVariable UUID releaseId, @PathVariable UUID projectId) {
    boolean isLead = userProjectService
        .isUserLeadInProject(principalService.getPrincipalId(), projectId);
    ReleaseResponseDto releaseResponseDto = releaseService.getById(releaseId);

    ModelAndView modelAndView = new ModelAndView("releases/releasePage");
    modelAndView.addObject("releaseDto", releaseResponseDto);
    modelAndView.addObject("isLead", isLead);
    return modelAndView;
  }

  @GetMapping("/new")
  public ModelAndView getAddReleasePage(@PathVariable UUID projectId) {
    utilService.checkLeadAccess(projectId);

    ModelAndView modelAndView = new ModelAndView("releases/newReleasePage");
    modelAndView.addObject("projectId", projectId);
    return modelAndView;
  }

  @PostMapping
  public String addReleaseToProject(String version, String formStartDate, String formEndDate,
      @PathVariable UUID projectId) throws ParseException {
    utilService.checkLeadAccess(projectId);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    ReleaseRequestDto releaseRequestDto = new ReleaseRequestDto();
    releaseRequestDto.setProjectId(projectId);
    releaseRequestDto.setVersion(version);

    Date startDate = simpleDateFormat.parse(formStartDate);
    Date endDate = simpleDateFormat.parse(formEndDate);

    if (endDate.compareTo(startDate) < 0) {
      throw new FormInputException("form.releaseEarlier");
    }

    releaseRequestDto.setStartDate(startDate);
    releaseRequestDto.setEndDate(endDate);

    releaseService.addRelease(releaseRequestDto);

    return "redirect:/projects/{projectId}/releases";
  }

  @DeleteMapping("/{releaseId}")
  public String deleteRelease(@PathVariable UUID releaseId, @PathVariable UUID projectId) {
    utilService.checkLeadAccess(projectId);

    releaseService.deleteReleaseById(releaseId);
    return "redirect:/projects/{projectId}/releases";
  }

  @GetMapping("/{releaseId}/edit")
  public ModelAndView getEditReleasePage(@PathVariable UUID releaseId,
      @PathVariable UUID projectId) {
    utilService.checkLeadAccess(projectId);

    ReleaseResponseDto releaseDto = releaseService.getById(releaseId);
    ModelAndView modelAndView = new ModelAndView("releases/editReleasePage");
    modelAndView.addObject("releaseDto", releaseDto);

    return modelAndView;
  }

  @PatchMapping("/{releaseId}")
  public String updateRelease(@PathVariable UUID projectId, @PathVariable UUID releaseId,
      ReleaseRequestDto releaseRequestDto, String formStartDate,
      String formEndDate)
      throws ParseException {
    utilService.checkLeadAccess(projectId);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Date startDate = simpleDateFormat.parse(formStartDate);
    Date endDate = simpleDateFormat.parse(formEndDate);

    if (endDate.compareTo(startDate) < 0) {
      throw new FormInputException("form.releaseEarlier");
    }

    releaseRequestDto.setStartDate(startDate);
    releaseRequestDto.setEndDate(endDate);

    releaseService.updateRelease(releaseId, releaseRequestDto);

    return "redirect:/projects/{projectId}/releases/{releaseId}";
  }
}
