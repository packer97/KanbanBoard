package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.exception.ReleaseNotFoundException;
import com.group_3.kanbanboard.repository.ReleaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReleaseEntityServiceImpl implements ReleaseEntityService {

  private final ReleaseRepository releaseRepository;

  public ReleaseEntityServiceImpl(
      ReleaseRepository releaseRepository) {
    this.releaseRepository = releaseRepository;
  }

  @Override
  public ReleaseEntity saveEntity(ReleaseEntity entity) {
    return releaseRepository.save(entity);
  }

  @Override
  public ReleaseEntity getEntity(UUID releaseId) {
    return releaseRepository.findById(releaseId).orElseThrow(
        () -> new ReleaseNotFoundException("release.notFound", releaseId));
  }

  @Override
  public List<ReleaseEntity> getAllEntity() {
    return releaseRepository.findAll();
  }

  @Override
  public boolean exists(UUID uuid) {
    return releaseRepository.existsById(uuid);
  }

  @Override
  public void deleteById(UUID uuid) {
    releaseRepository.deleteById(uuid);
  }
}
