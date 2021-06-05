package com.group_3.kanbanboard.controller.feign;

import com.group_3.kanbanboard.service.feign.YouTubeService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/youtube")
public class YouTubeController {

        private final YouTubeService youTubeService;

        @Autowired
        public YouTubeController(
                YouTubeService youTubeService) {
            this.youTubeService = youTubeService;
        }

        @GetMapping("/searchPage")
        public String getSearchPage() {
            return "youTube/youTubeRequestPage";
        }

        @GetMapping("/channels")
        public ModelAndView getChannelInfo(@RequestParam String id) //channel id f.e. UC6uFoHcr_EEK6DgCS-LeTNA
                throws ParseException {

            ModelAndView modelAndView = new ModelAndView("youTube/youTubeResponsePage");
            String json = youTubeService.getResult(id);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);
            JSONArray resultList = (JSONArray) jsonObject.get("items");
            modelAndView.addObject("id", id);
            modelAndView.addObject("resultList", resultList);
            return modelAndView;
        }

}
