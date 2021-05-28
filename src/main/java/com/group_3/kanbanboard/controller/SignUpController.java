package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.UserRole;
import com.group_3.kanbanboard.rest.dto.UserRequestDto;
import com.group_3.kanbanboard.rest.dto.UserSignUpRequest;
import com.group_3.kanbanboard.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
@RequestMapping("/registration")
public class SignUpController {
    UserServiceImpl userService;

    @Autowired
    public SignUpController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getSignUpPage() {
        ModelAndView model = new ModelAndView("profile/signUp");
        model.addObject("userSignUpRequest", new UserSignUpRequest());
        return model;
    }

    @PostMapping
    public ModelAndView saveUser(@Valid @ModelAttribute UserSignUpRequest userSignUpRequest,
                                 BindingResult bindingResult, ModelAndView model) {
        if (bindingResult.hasErrors()) {
            model.setViewName("profile/signUp");
            bindingResult.addError(Objects.requireNonNull(validatePassword(userSignUpRequest)));
            return model;
        }
        userService.addUser(new UserRequestDto(userSignUpRequest.getFirstName(), userSignUpRequest.getLastName(),
                userSignUpRequest.getPassword(), userSignUpRequest.getUserName(), userSignUpRequest.getEmail(), Collections.singleton(UserRole.USER)));
        model.setViewName("profile/login");
        return model;
    }

    private ObjectError validatePassword(UserSignUpRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return new ObjectError("UserSignUpRequest", "Confirm password doesn't match");
        }
        return null;
    }
}
