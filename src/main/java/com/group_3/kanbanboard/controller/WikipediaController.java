package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.service.feign.WikipediaDownloadService;
import com.group_3.kanbanboard.service.feign.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@Controller
@RequestMapping("/wikipedia")
public class WikipediaController {

    private final WikipediaService wikipediaService;
    private final WikipediaDownloadService wikipediaDownloadService;

    @Autowired()
    public WikipediaController(WikipediaService wikipediaService, WikipediaDownloadService wikipediaDownloadService) {
        this.wikipediaService = wikipediaService;
        this.wikipediaDownloadService = wikipediaDownloadService;
    }

    @GetMapping()
    public String openWikipediaPage() {
        return "wikipedia/wikipedia";
    }

    @GetMapping(params = "title")
    public String getWikipediaHtmlByTitle(@RequestParam String title) throws IOException {
        String document = wikipediaService.getHtmlPageByTitle(title);

        if (!title.isEmpty()) {
            String downloadedHtmlPath = wikipediaDownloadService.downloadHtml(title, document);
        }
        return "wikipedia/wikipedia";

    }
}