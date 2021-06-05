package com.group_3.kanbanboard.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "youTube-service",
        url = "${youtube.api.url}"
)
public interface YouTubeClient{

    @GetMapping("/channels")
    String getChannelInfo(@RequestParam String part, @RequestParam String id, @RequestParam String key);

}
