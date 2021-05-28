package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.enums.ReleaseStatus;
import com.group_3.kanbanboard.exception.ReleaseNotFoundException;
import com.group_3.kanbanboard.mappers.ProjectMapper;
import com.group_3.kanbanboard.mappers.ReleaseMapper;
import com.group_3.kanbanboard.repository.ReleaseRepository;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.service.ReleaseService;
import com.group_3.kanbanboard.service.entity.ReleaseEntityServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReleaseServiceImpl implements ReleaseService {

  private final ReleaseEntityServiceImpl releaseEntityService;
  private final ProjectServiceImpl projectService;
  private final ReleaseMapper releaseMapper;
  private final ProjectMapper projectMapper;

  @Autowired
  public ReleaseServiceImpl(
      ReleaseEntityServiceImpl releaseEntityService,
      ProjectServiceImpl projectService, ReleaseMapper releaseMapper,
      ProjectMapper projectMapper) {
    this.releaseEntityService = releaseEntityService;
    this.projectService = projectService;
    this.releaseMapper = releaseMapper;
    this.projectMapper = projectMapper;
  }

  @Transactional
  @Override
  public ReleaseResponseDto getById(UUID id) {
    ReleaseEntity release = releaseEntityService.getEntity(id);
    return releaseMapper.toResponseDto(release);
  }

  @Transactional
  @Override
  public List<ReleaseResponseDto> getAllReleases() {
    List<ReleaseEntity> releases = releaseEntityService.getAllEntity();
    return releases.stream().map(releaseMapper::toResponseDto).collect(Collectors.toList());
  }

  @Transactional
  @Override
  public ReleaseResponseDto addRelease(ReleaseRequestDto releaseRequestDto) {
    ReleaseEntity release = releaseMapper.toEntity(releaseRequestDto);
    release.setStatus(ReleaseStatus.BACKLOG);

    ProjectResponseDto projectResponseDto = projectService
        .getById(releaseRequestDto.getProjectId());
    ProjectEntity project = projectMapper.toEntity(projectResponseDto);
    project.setId(releaseRequestDto.getProjectId());

    if (project.getReleases() == null) {
      project.setReleases(new ArrayList<>());
    }

    release.setProject(project);
    project.getReleases().add(release);
    releaseEntityService.saveEntity(release);

    return releaseMapper.toResponseDto(release);
  }

  @Transactional
  @Override
  public ReleaseResponseDto updateRelease(UUID id, ReleaseRequestDto releaseRequestDto) {
    ReleaseEntity releaseEntityFromDb = releaseEntityService.getEntity(id);
    ReleaseEntity releaseFromDto = releaseMapper.toEntity(releaseRequestDto);
    releaseFromDto.setProject(releaseEntityFromDb.getProject());
    releaseFromDto.setId(releaseEntityFromDb.getId());

    releaseEntityService.saveEntity(releaseFromDto);

    return releaseMapper.toResponseDto(releaseFromDto);
  }

  @Transactional
  @Override
  public void deleteReleaseById(UUID id) {
    if (!releaseEntityService.exists(id)){
      throw new ReleaseNotFoundException(String.format("Release with ID = %s was not found", id));
    }
    releaseEntityService.deleteById(id);
  }
}
