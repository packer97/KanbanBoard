//package com.group_3.kanbanboard.controller;
//
//import com.group_3.kanbanboard.entity.ReleaseEntity;
//import com.group_3.kanbanboard.entity.UserEntity;
//import com.group_3.kanbanboard.entity.UserProjectEntity;
//import com.group_3.kanbanboard.entity.UserProjectId;
//import com.group_3.kanbanboard.enums.InProjectUserRole;
//import com.group_3.kanbanboard.enums.ReleaseStatus;
//import com.group_3.kanbanboard.exception.FormInputException;
//import com.group_3.kanbanboard.exception.ReleaseNotFoundException;
//import com.group_3.kanbanboard.mappers.UserProjectMapper;
//import com.group_3.kanbanboard.repository.UserProjectRepository;
//import com.group_3.kanbanboard.rest.dto.ReleaseRequestDto;
//import com.group_3.kanbanboard.rest.dto.UserProjectRequestDto;
//import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
//import com.group_3.kanbanboard.rest.dto.UserResponseDto;
//import com.group_3.kanbanboard.service.PrincipalService;
//import com.group_3.kanbanboard.service.UserProjectService;
//import com.group_3.kanbanboard.service.UserService;
//import liquibase.pro.packaged.S;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
//@Controller
//@RequestMapping("/projects/{projectId}/users/")
//public class UserInProjectController {
//
//    private final PrincipalService principalService;
//    private final UserDetailsService userDetailsService;
//    private final UserService userService;
//    private final UserProjectService userProjectService;
//    private final UserProjectMapper userProjectMapper;
//
//    public UserInProjectController(UserDetailsService userDetailsService, UserProjectService userProjectService,
//                                   UserProjectMapper userProjectMapper, UserService userService, PrincipalService principalService) {
//        this.userDetailsService = userDetailsService;
//        this.userProjectService = userProjectService;
//        this.userProjectMapper = userProjectMapper;
//        ;
//        this.userService = userService;
//        this.principalService = principalService;
//    }
//
//    @GetMapping
//    public ModelAndView getPrincipalProfile() {
//        ModelAndView modelAndView = new ModelAndView("userProject/setUserRoleInProject");
//        modelAndView.addObject("principalDto", principalService.getPrincipal());
//        return modelAndView;
//    }
//
//
//    @GetMapping("/{username}")
//    public String UserDetail(@PathVariable String username, Model model) {
//        UserResponseDto user = userService.getUserByUsername(username);
//        model.addAttribute("user", user);
//        return "userProject/setUserRoleInProject";
//
//    }
//
//    @PatchMapping("/{username}")
//    public String updateUserProject(@PathVariable UUID projectId, @PathVariable String username, UserProjectRequestDto userProjectRequestDto,
//                                    UserProjectResponseDto userProjectResponseDto, String formStatus) {
//        UUID userId = userProjectResponseDto.getUser().getId();
//
//        userProjectRequestDto.setProjectUserRole(InProjectUserRole.valueOf(formStatus));
//        userProjectService.setUserProjectRole(userId, projectId, userProjectRequestDto);
//
//        return "userProject/setUserRoleInProject";
//    }
//
//
//}
