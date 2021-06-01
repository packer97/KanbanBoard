package com.group_3.kanbanboard.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(
        name = "www.wikipedia.org",
        url = "${wikipedia.api-url} + ${wikipedia.api-version}"
)
public interface WikipediaRestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/page/html/{title}")
    String getHtmlPageByTitle(@PathVariable String title);

    @RequestMapping(method = RequestMethod.GET, value = "/page/pdf/{title}")
    String getPdfPageByTitle(@PathVariable String title);

    @RequestMapping(method = RequestMethod.POST, value = "/page//html/{title}")
    String setHtmlPageByTitle(@PathVariable String title, @RequestBody String body);

}