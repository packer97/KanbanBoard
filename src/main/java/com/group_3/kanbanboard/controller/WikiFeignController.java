package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.service.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wiki")
public class WikiFeignController {

    private final WikiService wikiService;

    @Autowired
    public WikiFeignController(WikiService wikiService) {
        this.wikiService = wikiService;
    }
    @GetMapping
    public String getPage(@RequestParam String action,
                          @RequestParam String list,
                          @RequestParam String srsearch, Model model) {
        model.addAttribute("wiki", wikiService.getPage(action, list, srsearch));
        return "wiki";
    }

    @PostMapping
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        model.addAttribute("login", wikiService.login(username, password));
        return "loginWiki";
    }
}
