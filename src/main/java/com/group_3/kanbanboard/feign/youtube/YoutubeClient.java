package com.group_3.kanbanboard.feign.youtube;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "youtube-api", url = "${youtube.api.url}"
)
public interface YoutubeClient {

  @GetMapping("/search")
  ResponseEntity<String> get25SearchResults(@RequestParam String part, @RequestParam(defaultValue = "25") String maxResults,
      @RequestParam String q, @RequestParam String key);


}
