package com.group_3.kanbanboard.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "WikiClient",
        url = "${wiki.url}"
    )
public interface WikiClient {

    @GetMapping
    ResponseEntity<String> getPage(@RequestParam("query") String action,
                                   @RequestParam("search") String list,
                                   @RequestParam("Craig%20Noone") String srsearch,
                                   @RequestParam("json") String format);

    @PostMapping
    ResponseEntity<String> login(@RequestPart String action,
                                 @RequestPart String userName,
                                 @RequestPart String password,
                                 @RequestPart String loginreturnurl,
                                 @RequestPart String token,
                                 @RequestPart String format);
    @GetMapping
    ResponseEntity<String> getToken(@RequestParam("query") String action, @RequestParam("meta") String meta,
                                    @RequestParam("type") String type, @RequestParam("format") String format);

}

