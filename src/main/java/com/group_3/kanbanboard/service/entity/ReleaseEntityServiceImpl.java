package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.ReleaseEntity;
import com.group_3.kanbanboard.exception.ReleaseNotFoundException;
import com.group_3.kanbanboard.repository.ReleaseRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ReleaseEntityServiceImpl implements EntityNewService<ReleaseEntity, UUID> {

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
  public ReleaseEntity getEntity(UUID uuid) {
    return releaseRepository.findById(uuid).orElseThrow(
        () -> new ReleaseNotFoundException(String.format("Release with ID = %s was not found", uuid)));
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
