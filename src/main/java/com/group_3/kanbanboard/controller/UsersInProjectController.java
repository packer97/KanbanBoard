package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.InProjectUserRole;
import com.group_3.kanbanboard.rest.dto.UserProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UserService;
import com.group_3.kanbanboard.service.entity.UserEntityService;
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
    private final PrincipalService principalService;
    private final UserProjectService userProjectService;
    private final UserService userService;
    private final UserEntityService userEntityService;
    private final UserProjectServiceImpl userProjectServiceImpl;


    public UsersInProjectController(PrincipalService principalService,
                                    UserProjectService userProjectService,
                                    UserService userService,
                                    UserEntityService userEntityService, UserProjectServiceImpl userProjectServiceImpl) {

        this.principalService = principalService;
        this.userProjectService = userProjectService;
        this.userService = userService;
        this.userEntityService = userEntityService;
        this.userProjectServiceImpl = userProjectServiceImpl;


    }

    @GetMapping
    public ModelAndView getUsers(@PathVariable UUID projectId) {
        List<UserProjectResponseDto> userProjectResponseDto = userProjectServiceImpl.getUserProjectsFromProject(projectId);
        boolean isCreator = userProjectService.isUserProjectCreator(principalService.getPrincipal().getUsername(), projectId);
        ModelAndView modelAndView = new ModelAndView("userProject/usersInProjectList");
        modelAndView.addObject("isCreator", isCreator);
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
        userProjectServiceImpl.setUserProjectRole(userEntityService.getEntity(username).getId(), projectId, newRole);

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
            userProjectServiceImpl.setUserInProject(userEntityService.getEntity(userName).getId(), projectId, formStatus);
            return "redirect:/projects/{projectId}/users";
        }
        return "redirect:/projects/{projectId}/users";
    }
    @DeleteMapping("/{userName}")
    public String deleteUser(@PathVariable UUID projectId, @PathVariable String userName) {
        userProjectServiceImpl.deleteUserInProject(projectId, userName);
        return "redirect:/projects/{projectId}/users";
    }
}
