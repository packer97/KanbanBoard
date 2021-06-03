package com.group_3.kanbanboard.feign.youtube;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "youtube-api", url = "${youtube.api.url}"
)
public interface YoutubeClient {

  @GetMapping("/search")
  String get25SearchResults(@RequestParam String part, @RequestParam String maxResults,
      @RequestParam String q, @RequestParam String key);


}
