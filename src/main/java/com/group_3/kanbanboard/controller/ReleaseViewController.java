package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.exception.FormInputException;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.rest.dto.UnfinishedTasksDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.ProjectService;
import com.group_3.kanbanboard.service.ReleaseService;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UtilService;
import com.group_3.kanbanboard.util.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    UnfinishedTasksDto unfinishedTasksDto = releaseService.countUnfinishedTasks(releaseId);

    ModelAndView modelAndView = new ModelAndView("releases/releasePage");
    modelAndView.addObject("releaseDto", releaseResponseDto);
    modelAndView.addObject("unfinishedTasksNumber", unfinishedTasksDto.getQuantity());
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
  public String addReleaseToProject(@RequestParam String version,
      @RequestParam String formStartDate, @RequestParam String formEndDate,
      @PathVariable UUID projectId) throws ParseException {
    utilService.checkLeadAccess(projectId);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    ReleaseRequestDto releaseRequestDto = new ReleaseRequestDto();
    releaseRequestDto.setProjectId(projectId);
    releaseRequestDto.setVersion(version);

    Date startDate = simpleDateFormat.parse(formStartDate);
    Date endDate = simpleDateFormat.parse(formEndDate);

    if (endDate.compareTo(startDate) < 0) {
      throw new FormInputException("Start date of release should be earlier than end date");
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
      @ModelAttribute ReleaseRequestDto releaseRequestDto, @RequestParam String formStartDate,
      @RequestParam String formEndDate)
      throws ParseException {
    utilService.checkLeadAccess(projectId);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Date startDate = simpleDateFormat.parse(formStartDate);
    Date endDate = simpleDateFormat.parse(formEndDate);

    DateUtil.checkIncorrectDates(startDate, endDate);

    releaseRequestDto.setStartDate(startDate);
    releaseRequestDto.setEndDate(endDate);

    releaseService.updateRelease(releaseId, releaseRequestDto);

    return "redirect:/projects/{projectId}/releases/{releaseId}";
  }
}
