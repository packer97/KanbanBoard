package com.group_3.kanbanboard.controller;


import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.UserProjectService;
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



    public UsersInProjectController(ModelViewProjectService modelViewProjectService,
                                    UserProjectService userProjectService,
                                    PrincipalService principalService) {

        this.modelViewProjectService = modelViewProjectService;
        this.userProjectService = userProjectService;
        this.principalService = principalService;

    }


    @GetMapping
    public String getUsers(@PathVariable UUID projectId,
    Model model) {

        UserResponseDto userAsPrincipal = principalService.getPrincipal();
        List<UserResponseDto> userResponseDtoList = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("usersInProjectList", userResponseDtoList);
        return "userProject/usersInProjectList";
    }

    @PostMapping("/{addUser}")
    public String addUser() {

        return toString();
    }


}
