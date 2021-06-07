package com.group_3.kanbanboard.service.feign;


import com.group_3.kanbanboard.feign.WikiPediaTransformRequestDto;
import com.group_3.kanbanboard.feign.WikipediaPageRequestDto;
import com.group_3.kanbanboard.feign.WikipediaRestClient;
import com.group_3.kanbanboard.feign.WikipediaResponseMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class WikipediaServiceImpl implements WikipediaService {

    private static final Logger logger = Logger.getLogger(WikipediaServiceImpl.class.getName());
    private final WikipediaRestClient wikipediaRestClient;

    @Autowired
    public WikipediaServiceImpl(WikipediaRestClient wikipediaRestClient) {
        this.wikipediaRestClient = wikipediaRestClient;
    }

    @Override
    public String getHtmlPageByTitle(String title) {
        ResponseEntity<String> htmlResponse = wikipediaRestClient.getHtmlPageByTitle(title);
        logger.info(htmlResponse.getStatusCode().toString());
        return htmlResponse.getBody();
    }

    @Override
    public byte[] getPdfPageByTitle(String title) {
        ResponseEntity<byte[]> pdfResponse = wikipediaRestClient.getPdfPageByTitle(title);
        logger.info(pdfResponse.getStatusCode().toString());
        return pdfResponse.getBody();
    }

    @Override
    public WikipediaResponseMetaData getMetaDataByTitle(String title) {
        ResponseEntity<WikipediaResponseMetaData> metaDataResponse = wikipediaRestClient.getMetaDataByTitle(title);
        logger.info(metaDataResponse.getStatusCode().toString());
        return metaDataResponse.getBody();
    }

    @Override
    public void setHtmlPageByTitle(String title, WikipediaPageRequestDto request) {

        ResponseEntity<?> response = wikipediaRestClient.setHtmlPageByTitle(title, request);
        logger.info(response.getStatusCode().toString());
    }

    @Override
    public String transformHtmlToWikitext(WikiPediaTransformRequestDto request){
        ResponseEntity<String> wikiTextResponse = wikipediaRestClient.transformToWikiText(request);
        logger.info(wikiTextResponse.getStatusCode().toString());
        return wikiTextResponse.getBody();
    }
}
