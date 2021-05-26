package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.enums.InProjectUserRole;
import com.group_3.kanbanboard.rest.dto.UserProjectRequestDto;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UserService;
import com.group_3.kanbanboard.service.impl.ModelViewProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects/{projectId}/users")
public class UsersInProjectController {
    private final ModelViewProjectService modelViewProjectService;
    private final UserProjectService userProjectService;
    private final PrincipalService principalService;
    private final UserService userService;



    public UsersInProjectController(ModelViewProjectService modelViewProjectService,
                                    UserProjectService userProjectService,
                                    PrincipalService principalService,
                                    UserService userService) {

        this.modelViewProjectService = modelViewProjectService;
        this.userProjectService = userProjectService;
        this.principalService = principalService;
        this.userService = userService;


    }


    @GetMapping
    public String getUsers(@PathVariable UUID projectId,
    Model model) {
        List<UserResponseDto> userResponseDtoList = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("usersInProjectList", userResponseDtoList);
        return "userProject/usersInProjectList";
    }

    @PostMapping("/{addUser}")
    public String addUser() {

        return toString();
    }
    @GetMapping("/{username}")
    public String UserDetail(@PathVariable String username, Model model) {
        UserResponseDto user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "userProject/setUserRoleInProject";

    }

    @PatchMapping("/{username}")
    public String updateUserProject(@PathVariable UUID projectId, @PathVariable String username, UserProjectRequestDto userProjectRequestDto,
                                    UserProjectResponseDto userProjectResponseDto, String formStatus,Model model) {
        UUID userId = userProjectResponseDto.getUser().getId();

        userProjectRequestDto.setProjectUserRole(InProjectUserRole.valueOf(formStatus));
        userProjectService.setUserProjectRole(userId, projectId, userProjectRequestDto);
        model.addAttribute("userProject", userProjectRequestDto);
        return "userProject/setUserRoleInProject";
    }



}
