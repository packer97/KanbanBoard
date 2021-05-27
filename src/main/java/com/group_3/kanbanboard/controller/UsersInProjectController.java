package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.InProjectUserRole;
import com.group_3.kanbanboard.rest.dto.UserProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UserService;
import com.group_3.kanbanboard.service.impl.ModelViewProjectService;
import com.group_3.kanbanboard.service.impl.UserProjectServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/projects/{projectId}/users")
public class UsersInProjectController {
    private final ModelViewProjectService modelViewProjectService;
    private final UserProjectService userProjectService;
    private final UserService userService;
    private final UserProjectServiceImpl userProjectServiceImpl;



    public UsersInProjectController(ModelViewProjectService modelViewProjectService,
                                    UserProjectService userProjectService,
                                    UserService userService,
                                    UserProjectServiceImpl userProjectServiceImpl) {

        this.modelViewProjectService = modelViewProjectService;
        this.userProjectService = userProjectService;
        this.userService = userService;
        this.userProjectServiceImpl = userProjectServiceImpl;


    }

    @GetMapping
    public ModelAndView getUsers(@PathVariable UUID projectId) {
        List<UserProjectResponseDto> userProjectResponseDto = userProjectServiceImpl.getUserProjectsFromProject(projectId);
        ModelAndView modelAndView = new ModelAndView("userProject/usersInProjectList");
        modelAndView.addObject("userProjectResponseDto", userProjectResponseDto);
        return modelAndView;
    }
//    public ModelAndView getUsersRoleByProject(@PathVariable UUID projectId,
//                                              UserProjectResponseDto userProjectResponseDto){
//
//    }
    @PostMapping("/{addUser}")
    public String addUser() {

        return toString();
    }

    @GetMapping("/{username}")
    public String UserDetail(@PathVariable UUID projectId,@PathVariable String username, Model model,
                                UserProjectResponseDto userProjectResponseDto) {
        UserResponseDto user = userService.getUserByUsername(username);
//        UUID userId = userProjectResponseDto.getUser().getId();
        model.addAttribute("user", user);
//        List<UserProjectResponseDto> userProjectResponseDto = userProjectServiceImpl.getUserProjectsFromProject(projectId);

//        model.addAttribute("role", userProjectService.getUserProjectByUserAndProject(userId,projectId));
        return "userProject/setUserRoleInProject";

    }

    @PostMapping("/{username}")
    public ModelAndView updateUserProject(@PathVariable UUID projectId, @PathVariable String username,
                                          UserProjectServiceImpl userProjectServiceImpl,
                                          UserProjectResponseDto userProjectResponseDto,
                                          UserProjectRequestDto userProjectRequestDto,
                                          String formStatus) {
        userProjectRequestDto.setProjectUserRole(InProjectUserRole.valueOf(formStatus));
        userProjectServiceImpl.setUserProjectRole(userProjectResponseDto.getUser().getId(),projectId,userProjectRequestDto);
        ModelAndView modelAndView = new ModelAndView("userProject/setUserRoleInProject");
        modelAndView.addObject("userProjectResponseDto", userProjectRequestDto);
        return modelAndView;
    }

}
