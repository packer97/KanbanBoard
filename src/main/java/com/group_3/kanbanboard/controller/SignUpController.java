package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.UserRole;
import com.group_3.kanbanboard.rest.dto.UserRequestDto;
import com.group_3.kanbanboard.rest.dto.UserSignUpRequest;
import com.group_3.kanbanboard.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/registration")
public class SignUpController {
    UserServiceImpl userService;

    @Autowired
    public SignUpController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignUpPage(@ModelAttribute("user") UserSignUpRequest userSignUpRequest) {
        return "signUp";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") @Valid UserSignUpRequest userSignUpRequest,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUp";
        }
        if (!userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword())) {
            return "signUp";
        }
        userService.addUser(new UserRequestDto(userSignUpRequest.getFirstName(), userSignUpRequest.getLastName(),
                userSignUpRequest.getPassword(), userSignUpRequest.getUserName(), userSignUpRequest.getEmail(), Collections.singleton(UserRole.USER)));
        return "login";
    }
}
