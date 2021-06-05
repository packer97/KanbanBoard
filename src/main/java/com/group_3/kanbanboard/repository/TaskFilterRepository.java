package com.group_3.kanbanboard.repository;

import com.group_3.kanbanboard.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface TaskFilterRepository extends Repository<TaskEntity, UUID>, JpaSpecificationExecutor<TaskEntity> {

}
