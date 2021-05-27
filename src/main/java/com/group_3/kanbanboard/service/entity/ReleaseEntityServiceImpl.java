package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.entity.TaskEntity;
import com.group_3.kanbanboard.exception.ReleaseNotFoundException;
import com.group_3.kanbanboard.exception.TaskNotFoundException;
import com.group_3.kanbanboard.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReleaseEntityServiceImpl implements EntityNewService<ReleaseEntity, UUID> {
    private final ReleaseRepository releaseRepository;

    @Autowired
    public ReleaseEntityServiceImpl(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @Override
    public ReleaseEntity saveEntity(ReleaseEntity entity) {
        return releaseRepository.save(entity);
    }

    @Override
    public ReleaseEntity getEntity(UUID releaseId) {
        return releaseRepository.findById(releaseId)
                .orElseThrow(() -> new ReleaseNotFoundException(String.format("Release with id = %s not found", releaseId)));
    }

    @Override
    public List<ReleaseEntity> getAllEntity() {
        return releaseRepository.findAll();
    }

    @Override
    public boolean exists(UUID releaseId) {
        return releaseRepository.existsById(releaseId);
    }

    @Override
    public void deleteById(UUID releaseId) {
        releaseRepository.deleteById(releaseId);
    }
}

