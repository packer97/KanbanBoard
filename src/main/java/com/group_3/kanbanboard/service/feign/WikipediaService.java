package com.group_3.kanbanboard.service.feign;


import com.group_3.kanbanboard.feign.WikipediaRestClient;
import com.group_3.kanbanboard.feign.WikipediaRestMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WikipediaService {

    private final WikipediaRestClient wikipediaRestClient;

    @Autowired
    public WikipediaService(WikipediaRestClient wikipediaRestClient) {
        this.wikipediaRestClient = wikipediaRestClient;
    }

    public String getHtmlPageByTitle(String title) {
       ResponseEntity<String> htmlResponse = wikipediaRestClient.getHtmlPageByTitle(title);
        return htmlResponse.getBody();
    }

    public byte[] getPdfPageByTitle(String title){
        ResponseEntity<byte[]> pdfResponse = wikipediaRestClient.getPdfPageByTitle(title);
        return  pdfResponse.getBody();
    }

    public WikipediaRestMetaData  getMetaDataByTitle(String title){
        ResponseEntity<WikipediaRestMetaData> metaDataResponse = wikipediaRestClient.getMetaDataByTitle(title);
        return metaDataResponse.getBody();
    }
}
