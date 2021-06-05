package com.group_3.kanbanboard.controller.feign;

import com.group_3.kanbanboard.feign.PtsvClient;
//import com.group_3.kanbanboard.service.feign.PtsvService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ptsv")
public class PtsvController {

    private final PtsvClient ptsvClient;

    @Autowired
    public PtsvController(
            PtsvClient ptsvClient) {
        this.ptsvClient = ptsvClient;
    }

    @GetMapping("/sendTextPage")
    public String getSendTextPage() {
        return "POST/post";
    }

    @PostMapping("sendTextPage") //check here http://ptsv2.com/t/postrequest
    public String sendText(String text) throws ParseException {
        ptsvClient.sendText(text);
        return "POST/executedPost";
    }
}
