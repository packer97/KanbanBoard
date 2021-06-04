package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.service.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wiki")
public class WikiFeignController {

    private final WikiService wikipediaService;

    @Autowired
    public WikiFeignController(WikiService wikipediaService) {
        this.wikipediaService = wikipediaService;
    }
}
