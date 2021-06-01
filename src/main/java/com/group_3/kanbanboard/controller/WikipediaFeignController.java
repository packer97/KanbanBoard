package com.group_3.kanbanboard.controller;


import com.group_3.kanbanboard.service.feign.WikipediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wikipedia")
public class WikipediaFeignController {

    private final WikipediaService wikipediaService;

    @Autowired
    public WikipediaFeignController(WikipediaService wikipediaService) {
        this.wikipediaService = wikipediaService;
    }

    @GetMapping(params = "title")
    public void getWikipediaHtmlPage(@RequestParam String title, Model model) {
        model.addAttribute("title", title);
        wikipediaService.method(title);
    }
}
