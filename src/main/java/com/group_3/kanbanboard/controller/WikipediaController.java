package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.feign.WikipediaRestMetaData;
import com.group_3.kanbanboard.service.feign.WikipediaDownloadService;
import com.group_3.kanbanboard.service.feign.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping(params = {"title", "contentType"})
    public String getWikipediaHtmlByTitle(@RequestParam String title,
                                          @RequestParam String contentType,
                                          Model model) throws IOException {

        WikipediaRestMetaData metaData = wikipediaService.getMetaDataByTitle(title);

        System.out.println();


        switch (contentType) {
            case ("html"): {
                String htmlDocument = wikipediaService.getHtmlPageByTitle(title);
                String downloadedHtmlPath = wikipediaDownloadService.downloadHtml(title, htmlDocument);

                return "redirect:" + downloadedHtmlPath;
            }
            case ("pdf"): {
                byte[] pdfDocument = wikipediaService.getPdfPageByTitle(title);
                String downloadedPdfPath = wikipediaDownloadService.downloadPdf(title, pdfDocument);

                return "redirect:" + downloadedPdfPath;
            }
            default:
                throw new RuntimeException("Unknown request parameter \"contentType\"");
        }
    }
}