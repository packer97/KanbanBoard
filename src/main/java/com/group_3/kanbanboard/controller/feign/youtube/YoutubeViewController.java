package com.group_3.kanbanboard.controller.feign.youtube;

import com.group_3.kanbanboard.service.feign.YoutubeClientService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/youtube")
public class YoutubeViewController {

  private final YoutubeClientService youtubeClientService;

  @Autowired
  public YoutubeViewController(
      YoutubeClientService youtubeClientService) {
    this.youtubeClientService = youtubeClientService;
  }

  @GetMapping("/searchPage")
  public String getSearchPage() {
    return "youtube/searchPage";
  }

  @GetMapping("/search")
  public ModelAndView getSearchResults(@RequestParam String q, @RequestParam String maxResults)
      throws ParseException {
    ModelAndView modelAndView = new ModelAndView("youtube/resultsPage");
    ResponseEntity<String> responseString = youtubeClientService.getSearchResults(q, maxResults);
    JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseString.getBody());

    JSONArray resultList = (JSONArray) jsonObject.get("items");

    modelAndView.addObject("query", q);
    modelAndView.addObject("resultList", resultList);
    return modelAndView;
  }


}
