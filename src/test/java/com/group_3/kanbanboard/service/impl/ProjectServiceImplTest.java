package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.enums.UserRole;
import com.group_3.kanbanboard.exception.ProjectNotFoundException;
import com.group_3.kanbanboard.mappers.ProjectMapper;
import com.group_3.kanbanboard.mappers.ProjectMapperImpl;
import com.group_3.kanbanboard.mappers.UserMapper;
import com.group_3.kanbanboard.mappers.UserMapperImpl;
import com.group_3.kanbanboard.repository.ProjectRepository;
import com.group_3.kanbanboard.repository.UserRepository;
import com.group_3.kanbanboard.rest.dto.ProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {
    private static final UUID ID_1 = UUID.randomUUID();
    private static final UUID ID_2 = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String PROJECT_TITLE_1 = "project_1";
    private static final String PROJECT_TITLE_2 = "project_2";
    private static final String DESCRIPTION_1 = "description_1";
    private static final String DESCRIPTION_2 = "description_2";
    private static final UUID LEAD_ID = UUID.randomUUID();
    private static final boolean START_PROJECT = false;

    private static final String firstName = "Firstname";
    private static final String secondName = "Secondnameov";
    private static final String userName = "FirstSecondnameov";
    private static final String password = "pass";
    private static final String mail = "fs@ya.com";
    private Set<UserRole> role = new HashSet<>(Arrays.asList(UserRole.USER,UserRole.ADMIN));


    private static ProjectEntity project1;
    private static ProjectRequestDto projectRequestDto;
    private static UserEntity expectedUser = new UserEntity();
    private static UserProjectEntity userProjectEntity = new UserProjectEntity();
    private static List<ReleaseEntity> releases;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8) {};

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Before
    public void setUp() {
        project1 = new ProjectEntity(ID_1, PROJECT_TITLE_1, DESCRIPTION_1);
        projectRequestDto = new ProjectRequestDto(PROJECT_TITLE_1, DESCRIPTION_1, LEAD_ID);
        expectedUser = new UserEntity(USER_ID,firstName,secondName,userName,password,mail,role);
    }

    @After
    public void tearDown() {
        project1 = null;
        projectRequestDto = null;
        expectedUser = null;
    }

    @Test
    public void getById() {
        when(projectRepository.findById(Mockito.any())).thenReturn(Optional.of(project1));
        reMapper();
        ProjectResponseDto actual = projectService.getById(ID_1);

        Assert.assertEquals(project1.getDescription(), actual.getDescription());
        Assert.assertEquals(project1.getTitle(), actual.getTitle());
    }

    @Test(expected = ProjectNotFoundException.class)
    public void getByIdExc() {
        ProjectResponseDto actualProjectResponseDto = projectService.getById(ID_1);
    }

    @Test
    public void getAllProjects() {
        List<ProjectEntity> expectedListProject = Collections.singletonList(project1);
        reMapper();
        when(projectRepository.findAll()).thenReturn(expectedListProject);
        List<ProjectResponseDto> actualListProject = projectService.getAllProjects();
        Assert.assertEquals(expectedListProject.get(0).getTitle(), actualListProject.get(0).getTitle());
        Assert.assertEquals(expectedListProject.get(0).getDescription(), actualListProject.get(0).getDescription());
    }

    @Test
    public void addProject() {
        when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(expectedUser));
        when(userMapper.toResponseDto(Mockito.any(UserEntity.class)))
                .thenAnswer(invocation -> new UserMapperImpl().toResponseDto(invocation.<UserEntity>getArgument(0)));
        when(projectRepository.findById(Mockito.any())).thenReturn(Optional.of(project1));
        when(projectRepository.save(project1)).thenReturn(project1);
        reMapper();
        ProjectResponseDto actualProject = projectService.addProject(USER_ID, projectRequestDto);
        Assert.assertEquals(project1.getTitle(), actualProject.getTitle());
        Assert.assertEquals(project1.getDescription(), actualProject.getDescription());
    }

    @Test
    public void updateProject() {
        when(projectRepository.findById(Mockito.any())).thenReturn(Optional.of(project1));
        reMapper();
        ProjectResponseDto actualProjectResponseDto = projectService.updateProject(ID_2, projectRequestDto);

        Assert.assertEquals(projectRequestDto.getDescription(), actualProjectResponseDto.getDescription());
        Assert.assertEquals(projectRequestDto.getTitle(), actualProjectResponseDto.getTitle());
    }

    @Test(expected = ProjectNotFoundException.class)
    public void updateProjectExc() {
        ProjectResponseDto actualProjectResponseDto = projectService.updateProject(ID_1, projectRequestDto);
    }

    @Test
    public void deleteProjectById() {
        UUID id = Mockito.any();
        Mockito.when(projectRepository.existsById(id)).thenReturn(Boolean.TRUE);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void deleteProjectExc(){
        projectService.deleteProjectById(ID_1);
    }

    private void reMapper() {
        when(projectMapper.toResponseDto(Mockito.any(ProjectEntity.class)))
                .thenAnswer(invocation -> new ProjectMapperImpl().toResponseDto(invocation.<ProjectEntity>getArgument(0)));
        when(projectMapper.toRequestDto(Mockito.any(ProjectEntity.class)))
                .thenAnswer(invocation -> new ProjectMapperImpl().toRequestDto(invocation.<ProjectEntity>getArgument(0)));
        when(projectMapper.toEntity(Mockito.any(ProjectRequestDto.class)))
            .thenAnswer(invocation -> new ProjectMapperImpl()
                .toEntity(invocation.<ProjectRequestDto>getArgument(0)));
    }

    @Test(expected = ProjectNotFoundException.class)
    public void endProject() {
        projectService.setProjectStatusEnd(ID_1);
    }


}