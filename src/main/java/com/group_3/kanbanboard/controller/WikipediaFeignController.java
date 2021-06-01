package com.group_3.kanbanboard.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wikipedia")
public class WikipediaFeignController {

//    private final WikipediaService wikipediaService;
//
//    @Autowired
//    public WikipediaFeignController(WikipediaService wikipediaService) {
//        this.wikipediaService = wikipediaService;
//    }
//
//    @GetMapping(params = "title")
//    public void getWikipediaHtmlPage(@RequestParam String title, Model model) {
//        model.addAttribute("title", title);
//        wikipediaService.method(title);
//    }
}
