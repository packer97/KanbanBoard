package com.group_3.kanbanboard.rest;

import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
import com.group_3.kanbanboard.rest.dto.ReleaseResponseDto;
import com.group_3.kanbanboard.service.ReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Управление релизами")
@RestController
@RequestMapping("api/releases")
public class ReleaseController {

  private final ReleaseService releaseService;

  @Autowired
  public ReleaseController(ReleaseService releaseService) {
    this.releaseService = releaseService;
  }

  /**
   * Get a release by its id
   *
   * @param id Release id
   * @return Release with the given id
   */
  @Operation(summary = "Получение релиза по id")
  @GetMapping("/{id}")
  public ResponseEntity<ReleaseResponseDto> getReleaseById(@PathVariable UUID id) {
    return ResponseEntity.ok().body(releaseService.getById(id));
  }

  /**
   * Get all releases
   *
   * @return List of all releases
   */
  @Operation(summary = "Получить все релизы")
  @GetMapping
  public ResponseEntity<List<ReleaseResponseDto>> getAllReleases() {
    return ResponseEntity.ok().body(releaseService.getAllReleases());
  }

  /**
   * Create a release
   *
   * @param releaseRequestDto Release being added
   * @return Added release
   */
  @Operation(summary = "Добавление релиза")
  @PostMapping
  public ResponseEntity<ReleaseResponseDto> createRelease(
      @RequestBody ReleaseRequestDto releaseRequestDto) {

    return ResponseEntity.ok(releaseService.addRelease(releaseRequestDto));
  }

  /**
   * Update a release
   *
   * @param id                Release id
   * @param releaseRequestDto New version of release
   * @return Updated release
   */
  @Operation(summary = "Обновление релиза")
  @PutMapping("/{id}")
  public ResponseEntity<ReleaseResponseDto> updateRelease(@PathVariable UUID id,
      @RequestBody ReleaseRequestDto releaseRequestDto) {

    return ResponseEntity.ok().body(releaseService.updateRelease(id, releaseRequestDto));
  }

  /**
   * Delete a non-intentional created release
   *
   * @param id Release id
   * @return Deleted release
   */
  @Operation(summary = "Удаление релиза")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteRelease(@PathVariable UUID id) {
    releaseService.deleteReleaseById(id);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Просмотр количества задач, не завершившихся в заданный релиз")
  @GetMapping("/{id}/unfinished_tasks")
  public ResponseEntity<Long> unfinishedTasks(@PathVariable UUID id){
    return ResponseEntity.ok(releaseService.countUnfinishedTasks(id));
  }


}