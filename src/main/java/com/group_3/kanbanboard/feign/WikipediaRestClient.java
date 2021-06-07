package com.group_3.kanbanboard.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
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

    /**
     * Get html page from Wikimedia RestAPI use title of for {@link PathVariable} in request;
     *
     * @param title - current title wikipedia url path;
     * @return {@link ResponseEntity} with {@link String} html content in body;
     */
    @RequestMapping(method = RequestMethod.GET, value = "/page/html/{title}")
    ResponseEntity<String> getHtmlPageByTitle(@PathVariable String title);

    /**
     * Get pdf page from Wikimedia RestAPI use title of for {@link PathVariable} in request;
     *
     * @param title - current title of wikipedia url path
     * @@return {@link ResponseEntity} with byteArray of pfd content in body;
     */
    @RequestMapping(method = RequestMethod.GET, value = "/page/pdf/{title}")
    ResponseEntity<byte[]> getPdfPageByTitle(@PathVariable String title);

    /**
     * Get metadata from Wikimedia RestAPI use title of for {@link PathVariable} in request;     *
     *
     * @param title - current title of wikipedia url path
     * @return {@link ResponseEntity} with {@link WikipediaResponseMetaData} json encoded metadata in body of request.     *
     */
    @RequestMapping(method = RequestMethod.GET, value = "/page/title/{title}")
    ResponseEntity<WikipediaResponseMetaData> getMetaDataByTitle(@PathVariable String title);

    /**
     * Set new revision of html page contains in request body.
     *
     * @param title - current title of wikipedia url path
     * @param body  - body {@link WikipediaPageRequestDto} contain new version html of requested url;
     * @return {@link ResponseEntity} with empty body. Only for get {@link HttpStatus} code;
     */
    @RequestMapping(method = RequestMethod.POST, value = "/page//html/{title}")
    ResponseEntity<?> setHtmlPageByTitle(@PathVariable String title, @RequestBody WikipediaPageRequestDto body);

    /**
     * Transform html content to wikitext by post request in Wikimedia RestAPI. Required in body html content;
     *
     * @param body - html page {@link String} content for transform;     *
     * @return {@link ResponseEntity} with {@link String} wikitext accepted by html page
     *
     */
    @RequestMapping(method = RequestMethod.POST, value = "/transform/html/to/wikitext")
    ResponseEntity<String> transformToWikiText(@RequestBody WikiPediaTransformRequestDto body);


}