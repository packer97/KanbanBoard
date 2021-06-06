package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.feign.WikipediaRequestDto;
import com.group_3.kanbanboard.feign.WikipediaRestMetaData;
import com.group_3.kanbanboard.service.feign.WikipediaLoadService;
import com.group_3.kanbanboard.service.feign.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/wikipedia")
public class WikipediaController {

    private final WikipediaService wikipediaService;
    private final WikipediaLoadService wikipediaLoadService;

    @Autowired()
    public WikipediaController(WikipediaService wikipediaService, WikipediaLoadService wikipediaLoadService) {
        this.wikipediaService = wikipediaService;
        this.wikipediaLoadService = wikipediaLoadService;
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
                String downloadedHtmlPath = wikipediaLoadService.downloadHtml(title, html);

                return "redirect:" + "/wikipedia/downloaded/html/" + title + ".html";
            }
            case ("pdf"): {
                byte[] pdf = wikipediaService.getPdfPageByTitle(title);
                String downloadedPdfPath = wikipediaLoadService.downloadPdf(title, pdf);

                return "redirect:" + "/wikipedia/downloaded/pdf/" + title + ".pdf";
            }
            default:
                throw new RuntimeException("Unknown value of request parameter \"contentType\"");
        }
    }

    @PostMapping()
    public String setNewRevisionHtmlByTitle(@RequestParam String title) throws IOException {

        WikipediaRestMetaData metaData = wikipediaService.getMetaDataByTitle(title);

        List<Map<String, Object>> items = metaData.getItems();
        Map<String, Object> properties = items.get(0);

        String rev = (String) properties.get("rev");
        String tid = (String) properties.get("tid");

        String etag = rev + "/" + tid;

        WikipediaRequestDto wikipediaRequestDto = new WikipediaRequestDto();
        wikipediaRequestDto.setBase_etag(etag);
        wikipediaRequestDto.setHtml(
                wikipediaLoadService.uploadPdf(Paths.get(".", "downloaded", "html", title + ".html").toString()));
        wikipediaRequestDto.setComment("testing wikipedia rest API post mapping");

        wikipediaRequestDto.setMinor(true);
        wikipediaRequestDto.setBot(true);
        wikipediaService.setHtmlPageByTitle(title, wikipediaRequestDto);

        return "wikipedia/wikipedia";
    }
}
