package com.group_3.kanbanboard.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "mediawiki-api",
        url = "${wikipedia.api.url}"
    )
public interface WikiClient {

    @RequestMapping(method = RequestMethod.GET, value = "")
    ResponseEntity<String> get();

    @RequestMapping(method = RequestMethod.POST, value = "")
    String set();
}

