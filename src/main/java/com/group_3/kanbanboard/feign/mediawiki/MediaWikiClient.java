package com.group_3.kanbanboard.feign.mediawiki;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@FeignClient(
    name = "mediawiki-api", url = "${wikipedia.api.url}"
)
public interface MediaWikiClient {

  @GetMapping
  ResponseEntity<String> getTokenForCreatingAccounts(@RequestParam(defaultValue = "query") String action, @RequestParam(defaultValue = "tokens") String meta,
      @RequestParam(defaultValue = "createaccount") String type, @RequestParam(defaultValue = "json") String format);


  //action=createaccount - https://www.mediawiki.org/wiki/API:Account_creation#Example_1:_Process_on_a_wiki_without_special_authentication_extensions
  @PostMapping
  ResponseEntity<String> createAccount(@RequestPart String action, @RequestPart String username, @RequestPart String password,
      @RequestPart String retype, @RequestPart String createreturnurl, @RequestPart String createtoken, @RequestPart String format);

}
