package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.feign.WikiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WikiService {

    private final WikiClient wikiClient;

    @Autowired
    public WikiService(WikiClient wikiClient) {
        this.wikiClient = wikiClient;
    }
}
