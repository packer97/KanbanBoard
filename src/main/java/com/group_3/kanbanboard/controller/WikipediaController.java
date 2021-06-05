package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.feign.WikipediaRestMetaData;
import com.group_3.kanbanboard.service.feign.WikipediaLoadService;
import com.group_3.kanbanboard.service.feign.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping("/wikipedia")
public class WikipediaController {

    private final WikipediaService wikipediaService;
    private final WikipediaLoadService wikipediaDownloadService;

    @Autowired()
    public WikipediaController(WikipediaService wikipediaService, WikipediaLoadService wikipediaDownloadService) {
        this.wikipediaService = wikipediaService;
        this.wikipediaDownloadService = wikipediaDownloadService;
    }

    @GetMapping()
    public String openWikipediaPage() {
        return "wikipedia/wikipedia";
    }

    @GetMapping(params = {"title", "contentType"})
    public String getWikipediaContentByTitle(@RequestParam String title,
                                             @RequestParam String contentType,
                                             Model model) throws IOException {
        WikipediaRestMetaData metaData = wikipediaService.getMetaDataByTitle(title);

        switch (contentType) {
            case ("html"): {
                String html = wikipediaService.getHtmlPageByTitle(title);
                String downloadedHtmlPath = wikipediaDownloadService.downloadHtml(title, html);

                return "redirect:" + "/wikipedia/downloaded/html/" + title + ".html";
            }
            case ("pdf"): {
                byte[] pdf = wikipediaService.getPdfPageByTitle(title);
                String downloadedPdfPath = wikipediaDownloadService.downloadPdf(title, pdf);

                return "redirect:" + "/wikipedia/downloaded/pdf/" + title + ".pdf";
            }
            default:
                throw new RuntimeException("Unknown value of request parameter \"contentType\"");
        }
    }
    @PostMapping()
    public @ResponseBody String setNewRevisionHtmlByTitle (@RequestParam String title) {
       return null;
    }
}
