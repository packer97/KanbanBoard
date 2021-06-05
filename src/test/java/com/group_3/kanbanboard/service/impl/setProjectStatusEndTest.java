package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.enums.ReleaseStatus;
import com.group_3.kanbanboard.repository.ProjectRepository;
import com.group_3.kanbanboard.rest.dto.ProjectResponseDto;
import com.group_3.kanbanboard.service.ProjectService;
import com.group_3.kanbanboard.service.entity.ProjectEntityService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class setProjectStatusEndTest {

    private static final Date DATE = new Date(1212121212121L);
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectEntityService projectEntityService;

    private UUID projectId;

    @BeforeEach
    void init() {

        ReleaseStatus status = ReleaseStatus.FINISHED;
        List<ReleaseEntity> releaseEntities = new ArrayList<ReleaseEntity>();
        ProjectEntity entity = new ProjectEntity();
        entity.setStartProject(true);
        entity.setTitle("Title");
        entity.setLeadId(UUID.randomUUID());
        entity.setDescription("qwertyu");
        ReleaseEntity releaseEntity1 = new ReleaseEntity("version", DATE, DATE, entity, status);
        ReleaseEntity releaseEntity2 = new ReleaseEntity("version", DATE, DATE, entity, status);
        releaseEntities.add(releaseEntity1);
        releaseEntities.add(releaseEntity2);
        entity.setReleases(releaseEntities);
        ProjectEntity saveEntity = projectEntityService.saveEntity(entity);
        projectId = saveEntity.getId();
    }

    @Test
    void findProjectByParameters() {
        Boolean projectStatus = false;
        projectService.setProjectStatusEnd(projectId);
        ProjectResponseDto actual = projectService.getById(projectId);
        Assert.assertEquals(projectStatus, actual.getStartProject());

    }

    @AfterEach
    void clean() {
        projectRepository.deleteAll();
    }

}

