package com.group_3.kanbanboard.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.enums.ReleaseStatus;
import com.group_3.kanbanboard.exception.ProjectNotFoundException;
import com.group_3.kanbanboard.exception.ReleaseNotFoundException;
import com.group_3.kanbanboard.mappers.ProjectMapper;
import com.group_3.kanbanboard.mappers.ProjectMapperImpl;
import com.group_3.kanbanboard.mappers.ReleaseMapper;
import com.group_3.kanbanboard.mappers.ReleaseMapperImpl;
import com.group_3.kanbanboard.repository.ProjectRepository;
import com.group_3.kanbanboard.repository.ReleaseRepository;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.service.entity.ReleaseEntityServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseServiceImplTest {

  private static final UUID releaseId1 = UUID.randomUUID();
  private static final UUID releaseId2 = UUID.randomUUID();
  private static final UUID releaseId3 = UUID.randomUUID();

  private static final UUID projectId = UUID.randomUUID();


  private static final String releaseName1 = "RELEASE_NAME_1";
  private static final String releaseName2 = "RELEASE_NAME_2"; //usage?!
  private static final String releaseName3 = "RELEASE_NAME_3";
  private static final ReleaseStatus releaseStatus2and3 = ReleaseStatus.BACKLOG;
  private static final ReleaseStatus releaseStatus1 = ReleaseStatus.IN_PROGRESS;
  private static final String releaseVersion1 = "V1.0";
  private static final String releaseVersion2 = "V2.0";
  private static final String releaseVersion3 = "V3.0";
  private static final Date startDate = new Date(System.currentTimeMillis());
  private static final Date endDate = new Date(System.currentTimeMillis() + 100000000);

  private static ProjectEntity project = new ProjectEntity();


  private static ReleaseEntity release1;
  private static ReleaseEntity release2;
  private static ReleaseEntity release3;

  private static ReleaseRequestDto requestDto;

  @Mock
  private ReleaseMapper releaseMapper;

  @InjectMocks
  private ProjectServiceImpl projectService;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private ProjectMapper projectMapper;

  @Mock
  private ReleaseRepository releaseRepository;

  @InjectMocks
  private ReleaseServiceImpl releaseService;

  private ReleaseEntityServiceImpl releaseEntityService;

  @Before
  public void setUp() {
    release1 = new ReleaseEntity(releaseVersion1, startDate, endDate, project, releaseStatus1);
    release2 = new ReleaseEntity(releaseVersion2, startDate, endDate, project, releaseStatus2and3);
    release3 = new ReleaseEntity(releaseVersion3, startDate, endDate, project, releaseStatus2and3);

    releaseEntityService = new ReleaseEntityServiceImpl(releaseRepository);
    releaseService = new ReleaseServiceImpl(releaseEntityService, projectService, releaseMapper, projectMapper);

  }

//  @After
//  void tearDown() {
//  }

  @Test
  public void getById_EXISTING_RELEASES() {
    setUpReleaseMappers();

    when(releaseRepository.findById(releaseId1)).thenReturn(Optional.of(release1));
    when(releaseRepository.findById(releaseId2)).thenReturn(Optional.of(release2));
    when(releaseRepository.findById(releaseId3)).thenReturn(Optional.of(release3));

    Assertions.assertAll(
        () -> assertEquals(releaseService.getById(releaseId1).getVersion(),
            releaseMapper.toResponseDto(release1).getVersion()),
        () -> assertEquals(releaseService.getById(releaseId2).getVersion(),
            releaseMapper.toResponseDto(release2).getVersion()),
        () -> assertEquals(releaseService.getById(releaseId3).getVersion(),
            releaseMapper.toResponseDto(release3).getVersion())
    );
  }

  @Test
  public void getById_UNEXISTING_ID() {
    UUID unexistingId = UUID.randomUUID();
    when(releaseRepository.findById(unexistingId)).thenReturn(Optional.empty());

    assertThrows(ReleaseNotFoundException.class, () -> releaseService.getById(unexistingId));
  }

  @Test
  public void getById_NULL() {
    assertThrows(ReleaseNotFoundException.class, () -> releaseService.getById(null));
  }


  @Test
  public void getAllReleases() {
    setUpReleaseMappers();

    when(releaseRepository.findAll()).thenReturn(Arrays.asList(release1, release2));

    List<ReleaseResponseDto> actualReleases = Stream.of(release1, release2)
        .map(releaseMapper::toResponseDto).collect(
            Collectors.toList());

    Assertions.assertEquals(releaseService.getAllReleases().get(0).getVersion(),
        actualReleases.get(0).getVersion());

  }


  @Test
  public void addRelease() {
    when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));


    setUpReleaseMappers();
    setUpProjectMappers();


    project.setReleases(new ArrayList<>());

    ProjectEntity actualProject = new ProjectEntity();

    release1.setProject(actualProject);

    actualProject.setReleases(Collections.singletonList(release1));

    requestDto = new ReleaseRequestDto(projectId, releaseVersion1, startDate, endDate,
        releaseStatus1);
    ReleaseResponseDto releaseResponseDto = releaseService.addRelease(requestDto);

    assertEquals(releaseResponseDto.getProject().getReleases().get(0).getVersion(),
        actualProject.getReleases().get(0).getVersion());
  }

  @Test
  public void addRelease_NULL_PROJECT_ID() {
    setUpReleaseMappers();
    setUpProjectMappers();

    requestDto = new ReleaseRequestDto(null, releaseVersion1, startDate, endDate, releaseStatus1);
    assertThrows(ProjectNotFoundException.class, () -> releaseService.addRelease(requestDto));

  }

  //  @Test
//  public void updateRelease() {
//  }
//
//  @Test
//  public void deleteReleaseById() {
//  }
//

  private void setUpReleaseMappers() {
    when(releaseMapper.toResponseDto(Mockito.any(ReleaseEntity.class)))
        .thenAnswer(invocation -> new ReleaseMapperImpl()
            .toResponseDto(invocation.<ReleaseEntity>getArgument(0)));

    when(releaseMapper.toEntity(Mockito.any(ReleaseRequestDto.class)))
        .thenAnswer(invocation -> new ReleaseMapperImpl()
            .toEntity(invocation.<ReleaseRequestDto>getArgument(0)));


  }

  private void setUpProjectMappers() {
    when(projectMapper.toResponseDto(Mockito.any(ProjectEntity.class)))
        .thenAnswer(invocation -> new ProjectMapperImpl()
            .toResponseDto(invocation.<ProjectEntity>getArgument(0)));

    when(projectMapper.toEntity(Mockito.any(ProjectResponseDto.class)))
        .thenAnswer(invocation -> new ProjectMapperImpl()
            .toEntity(invocation.<ProjectResponseDto>getArgument(0)));
  }

}