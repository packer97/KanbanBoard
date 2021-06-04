package com.group_3.kanbanboard.repository;

import com.group_3.kanbanboard.entity.ReleaseEntity;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseEntity, UUID> {



}
