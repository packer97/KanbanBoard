package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.entity.ProjectEntity;
import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.entity.UserProjectEntity;
import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.rest.dto.UserProjectResponseDto;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UserService;
import com.group_3.kanbanboard.service.impl.ModelViewProjectService;
import com.group_3.kanbanboard.service.impl.UserProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects/{projectId}/releases/{releaseId}/users")
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

//        List<UserProjectResponseDto> userProjectResponseDtoList = userProjectService.getUserProjectsFromProject(projectId);
//        model.addAttribute("UsersList", userProjectResponseDtoList);
        List<UserResponseDto> userResponseDtoList = modelViewProjectService.getUsersForProject(projectId);
        model.addAttribute("usersInProjectList", userResponseDtoList);
        return "userProject/usersInProjectList";
    }



}
