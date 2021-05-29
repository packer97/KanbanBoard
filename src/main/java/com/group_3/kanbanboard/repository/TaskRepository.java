package com.group_3.kanbanboard.repository;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findByProjectAndRelease(ProjectEntity project, ReleaseEntity release);

    Optional<TaskEntity> findByIdAndProjectAndRelease(UUID id,
                                                      ProjectEntity project,
                                                      ReleaseEntity release);


}
