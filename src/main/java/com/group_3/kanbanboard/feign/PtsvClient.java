package com.group_3.kanbanboard.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ptsv-service",
        url = "${ptsv2.api.url}"
)
public interface PtsvClient {

    @PostMapping("/post")
    ResponseEntity<String> sendText(@RequestBody String text);

}
