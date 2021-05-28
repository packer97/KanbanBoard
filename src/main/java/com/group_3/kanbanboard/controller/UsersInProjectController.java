package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.InProjectUserRole;
import com.group_3.kanbanboard.rest.dto.UserProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UserService;
import com.group_3.kanbanboard.service.entity.EntityServiceImpl;
import com.group_3.kanbanboard.service.impl.ModelViewProjectService;
import com.group_3.kanbanboard.service.impl.UserProjectServiceImpl;
import liquibase.pro.packaged.S;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Controller
@RequestMapping("/projects/{projectId}/users")
public class UsersInProjectController {
    private final ModelViewProjectService modelViewProjectService;
    private final UserProjectService userProjectService;
    private final UserService userService;
    private final UserProjectServiceImpl userProjectServiceImpl;
    private final EntityServiceImpl entityService;


    public UsersInProjectController(ModelViewProjectService modelViewProjectService,
                                    UserProjectService userProjectService,
                                    UserService userService,
                                    UserProjectServiceImpl userProjectServiceImpl,
                                    EntityServiceImpl entityService) {

        this.modelViewProjectService = modelViewProjectService;
        this.userProjectService = userProjectService;
        this.userService = userService;
        this.entityService = entityService;
        this.userProjectServiceImpl = userProjectServiceImpl;


    }

    @GetMapping
    public ModelAndView getUsers(@PathVariable UUID projectId) {
        List<UserProjectResponseDto> userProjectResponseDto = userProjectServiceImpl.getUserProjectsFromProject(projectId);
        ModelAndView modelAndView = new ModelAndView("userProject/usersInProjectList");
        modelAndView.addObject("userProjectResponseDto", userProjectResponseDto);
        return modelAndView;
    }


    @GetMapping("/{username}")
    public String UserDetail(@PathVariable UUID projectId, @PathVariable String username, Model model) {
        boolean isCreator = userProjectService.isUserProjectCreator(username, projectId);
        UserResponseDto user = userService.getUserByUsername(username);
        model.addAttribute("isCreator", isCreator);
        model.addAttribute("user", user);
        return "userProject/setUserRoleInProject";

    }

    @PatchMapping("/{username}")
    public String updateUserProject(@PathVariable UUID projectId, @PathVariable String username,
                                    UserProjectRequestDto userProjectRequestDto,
                                    String formStatus) {
        InProjectUserRole newRole = InProjectUserRole.valueOf(formStatus);
        userProjectServiceImpl.setUserProjectRole(entityService.getUserEntity(username).getId(), projectId, newRole);

        return "redirect:/projects/{projectId}/users";
    }

    @GetMapping("/addUser")
    public ModelAndView getAdd(@PathVariable UUID projectId) {
        ModelAndView modelAndView = new ModelAndView("userProject/addUser");
        modelAndView.addObject("projectId", projectId);
        return modelAndView;
    }

    @PostMapping
    public String addUser(@PathVariable UUID projectId, String userName, String formStatus, Model model) {
        if (!userName.isEmpty()) {
            userProjectServiceImpl.setUserInProject(entityService.getUserEntity(userName).getId(), projectId, formStatus);
            return "redirect:/projects/{projectId}/users";
        } return "redirect:/projects/{projectId}/users";
    }

    @DeleteMapping("/{userName}")
    public String deleteUser(@PathVariable UUID projectId, @PathVariable String userName) {
        userProjectServiceImpl.deleteUserInProject(projectId, userName);
        return "redirect:/projects/{projectId}/users";
    }
}
