package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import java.util.List;
import java.util.UUID;

public interface ReleaseService {

  ReleaseResponseDto getById(UUID id);

  List<ReleaseResponseDto> getAllReleases();

  ReleaseResponseDto addRelease(ReleaseRequestDto releaseRequestDto);

  ReleaseResponseDto updateRelease(UUID id, ReleaseRequestDto releaseRequestDto);

  void deleteReleaseById(UUID id);

  long countUnfinishedTasks(UUID id);

}
