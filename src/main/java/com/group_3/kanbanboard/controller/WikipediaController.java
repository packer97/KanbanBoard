package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.feign.WikiPediaTransformRequestDto;
import com.group_3.kanbanboard.feign.WikipediaPageRequestDto;
import com.group_3.kanbanboard.feign.WikipediaRestMetaData;
import com.group_3.kanbanboard.service.feign.WikipediaLoadService;
import com.group_3.kanbanboard.service.feign.WikipediaService;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

        switch (contentType) {
            case ("html"): {
                String html = wikipediaService.getHtmlPageByTitle(title);
                wikipediaLoadService.downloadHtml(title, html);

                String url = "/wikipedia/downloaded/html/" + title + ".html";

                return "redirect:" + url;
            }
            case ("pdf"): {
                byte[] pdf = wikipediaService.getPdfPageByTitle(title);
                wikipediaLoadService.downloadPdf(title, pdf);

                String url = "/wikipedia/downloaded/pdf/" + title + ".pdf";

                return "redirect:" + url;
            }
            default:
                throw new RuntimeException("Unknown value of request parameter \"contentType\"");
        }
    }

    @GetMapping("/save")
    public String setNewRevisionHtmlByTitle(@RequestParam String title) throws IOException {

        WikipediaRestMetaData metaData = wikipediaService.getMetaDataByTitle(title);

        List<Map<String, Object>> items = metaData.getItems();
        Map<String, Object> properties = items.get(0);

        int rev = (Integer) properties.get("rev");
        String tid = (String) properties.get("tid");

        String etag = rev + "/" + tid;

        WikipediaPageRequestDto wikipediaRequestDto = new WikipediaPageRequestDto();
        wikipediaRequestDto.setBase_etag(etag);
        wikipediaRequestDto.setHtml(
                wikipediaLoadService.uploadHtml(Paths.get(WikipediaLoadService.ROOT_DOWNLOAD_PATH,
                        WikipediaLoadService.HTML_FILE_TYPE,
                        title + "." + WikipediaLoadService.HTML_FILE_TYPE).toString()));
        wikipediaRequestDto.setComment("testing wikipedia rest API post mapping");

        wikipediaService.setHtmlPageByTitle(title, wikipediaRequestDto);
        return "wikipedia/wikipedia";
    }

    @GetMapping("/transform")
    public String transFormToWikitext(@RequestParam String title) throws IOException {

//        System.out.println(Paths.get(WikipediaLoadService.ROOT_DOWNLOAD_PATH,
//                WikipediaLoadService.HTML_FILE_TYPE,
//                title + "." + WikipediaLoadService.HTML_FILE_TYPE).toString());

        String html = wikipediaLoadService.uploadHtml(Paths.get(WikipediaLoadService.ROOT_DOWNLOAD_PATH,
                WikipediaLoadService.HTML_FILE_TYPE,

                title + "." + WikipediaLoadService.HTML_FILE_TYPE).toString());
        WikiPediaTransformRequestDto transformRequestDto = new WikiPediaTransformRequestDto(html);

        String wikiText = wikipediaService.transformHtmlToWikitext(transformRequestDto);
        wikipediaLoadService.downloadWikiText(title, wikiText);

        String url = "/wikipedia/downloaded/wikitext/" + title + ".wiki";

        return "redirect:" + url;
    }
}

