package com.group_3.kanbanboard.controller.feign.mediawiki;

import com.group_3.kanbanboard.rest.dto.UserSignUpRequest;
import com.group_3.kanbanboard.service.feign.MediaWikiClientService;
import feign.Headers;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping("/mediawiki")
public class MediaWikiViewController {

  private final MediaWikiClientService clientService;

  @Autowired
  public MediaWikiViewController(
      MediaWikiClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping("/register")
  public String getRegistrationPage(){
    return "mediawiki/register";
  }

  @PostMapping("/register")
  public String createAccount(String username, String password, String retype) throws ParseException {
    if(!clientService.createNewAccount(username, password, retype)){
      return "redirect:/mediawiki/register";
    }
    return "mediawiki/successReg";
  }


}
