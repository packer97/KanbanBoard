package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.UserService;
import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/projects/{projectId}/users/")
public class UserInProjectController {

    private final PrincipalService principalService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public UserInProjectController(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, UserService userService, PrincipalService principalService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.principalService = principalService;
    }
    @GetMapping
    public ModelAndView getPrincipalProfile(){
        ModelAndView modelAndView = new ModelAndView("userProject/setUserRoleInProject");
        modelAndView.addObject("principalDto", principalService.getPrincipal());
        return modelAndView;
    }


    @GetMapping("/{username}")
    public String UserDetail(@PathVariable String username, Model model) {
        UserResponseDto user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "userProject/setUserRoleInProject";

    }


}
