package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.rest.dto.UserRequestDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {

  private final PrincipalService principalService;
  private final UserService userService;

  @Autowired
  public ProfileController(PrincipalService principalService, UserService userService) {
    this.principalService = principalService;
    this.userService = userService;
  }

  @GetMapping
  public ModelAndView getPrincipalProfile(){
    ModelAndView modelAndView = new ModelAndView("profile/profilePage");
    modelAndView.addObject("principalDto", principalService.getPrincipal());
    return modelAndView;
  }

  @GetMapping("/edit")
  public ModelAndView getProfileEditPage(){
    ModelAndView modelAndView = new ModelAndView("profile/profileEditPage");
    modelAndView.addObject("principalDto", principalService.getPrincipal());
    return modelAndView;
  }

  @PostMapping("/edit")
  public String editProfile(UserRequestDto requestDto){
    if(requestDto.getFirstName().equals("") || requestDto.getSecondName().equals("") || requestDto.getMail().equals("")){
      return "redirect:/profile/edit";
    }
    userService.updateUser(principalService.getPrincipalId(), requestDto);
    return "redirect:/profile";
  }

  @GetMapping("/changePassword")
  public String getChangePasswordChange(){
    return "profile/changePasswordPage";
  }

  @PostMapping("/changePassword")
  public String changePassword(@RequestParam String oldPassword,
      @RequestParam String newPassword, @RequestParam String confirmPassword){

    if(!principalService.isPrincipalPassword(oldPassword)){
      return "profile/changePasswordPage";
    }

    if(newPassword == null || newPassword.equals("")){
      return "profile/changePasswordPage";
    }

    if(!newPassword.equals(confirmPassword)){
      return "profile/changePasswordPage";
    }

    principalService.updatePrincipalPassword(newPassword);
    return "redirect:/profile";
  }


}
