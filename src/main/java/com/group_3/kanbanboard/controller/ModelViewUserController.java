package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class ModelViewUserController {


    private final UserService userService;

    public ModelViewUserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String usersList(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        return "usersList";
    }

    @GetMapping("/addAdmin")
    public void addAdmin() {
        userService.addAdmin();
    }


}
