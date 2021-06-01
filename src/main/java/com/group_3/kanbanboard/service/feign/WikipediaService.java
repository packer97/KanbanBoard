package com.group_3.kanbanboard.service.feign;


import com.group_3.kanbanboard.feign.WikipediaRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WikipediaService {

    private final WikipediaRestClient wikipediaRestClient;

    @Autowired
    public WikipediaService(WikipediaRestClient wikipediaRestClient) {
        this.wikipediaRestClient = wikipediaRestClient;
    }

    public void method(String title) {
        wikipediaRestClient.getPdfPageByTitle(title);
    }

}
