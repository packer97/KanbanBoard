package com.group_3.kanbanboard.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(
        name = "www.wikipedia.org",
        url = "${wikipedia.api-url}/${wikipedia.api-version}/"
)
public interface WikipediaRestClient {

    //Ограничение на передаваемый контент в ResponseBody - 2GB

    @RequestMapping(method = RequestMethod.GET, value = "/page/html/{title}")
    ResponseEntity<String> getHtmlPageByTitle(@PathVariable String title);

    @RequestMapping(method = RequestMethod.GET, value = "/page/pdf/{title}")
    ResponseEntity<byte[]> getPdfPageByTitle(@PathVariable String title);

    @RequestMapping(method = RequestMethod.POST, value = "/page//html/{title}")
    ResponseEntity<?> setHtmlPageByTitle(@PathVariable String title, @RequestBody WikipediaRequestDto body);

    @RequestMapping(method = RequestMethod.GET, value ="/page/title/{title}")
    ResponseEntity<WikipediaRestMetaData> getMetaDataByTitle(@PathVariable String title);
}