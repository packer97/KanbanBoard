package com.group_3.kanbanboard.service.feign;


import com.group_3.kanbanboard.feign.WikiPediaTransformRequestDto;
import com.group_3.kanbanboard.feign.WikipediaPageRequestDto;
import com.group_3.kanbanboard.feign.WikipediaRestClient;
import com.group_3.kanbanboard.feign.WikipediaRestMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class WikipediaService {

    private static final Logger logger = Logger.getLogger(WikipediaService.class.getName());
    private final WikipediaRestClient wikipediaRestClient;

    @Autowired
    public WikipediaService(WikipediaRestClient wikipediaRestClient) {
        this.wikipediaRestClient = wikipediaRestClient;
    }

    public String getHtmlPageByTitle(String title) {
        ResponseEntity<String> htmlResponse = wikipediaRestClient.getHtmlPageByTitle(title);
        logger.info(htmlResponse.getStatusCode().toString());
        return htmlResponse.getBody();
    }

    public byte[] getPdfPageByTitle(String title) {
        ResponseEntity<byte[]> pdfResponse = wikipediaRestClient.getPdfPageByTitle(title);
        logger.info(pdfResponse.getStatusCode().toString());
        return pdfResponse.getBody();
    }

    public WikipediaRestMetaData getMetaDataByTitle(String title) {
        ResponseEntity<WikipediaRestMetaData> metaDataResponse = wikipediaRestClient.getMetaDataByTitle(title);
        logger.info(metaDataResponse.getStatusCode().toString());
        return metaDataResponse.getBody();
    }

    public void setHtmlPageByTitle(String title, WikipediaPageRequestDto request) {

        ResponseEntity<?> response = wikipediaRestClient.setHtmlPageByTitle(title, request);
        logger.info(response.getStatusCode().toString());
    }

    public String transformHtmlToWikitext(WikiPediaTransformRequestDto request){
        ResponseEntity<String> wikiTextResponse = wikipediaRestClient.transformToWikiText(request);
        logger.info(wikiTextResponse.getStatusCode().toString());
        return wikiTextResponse.getBody();
    }
}
