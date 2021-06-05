package com.group_3.kanbanboard.controller;

import com.group_3.kanbanboard.rest.dto.TaskResponseDto;
import com.group_3.kanbanboard.service.TaskFilterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/filter")
public class TaskFilterController {
    private final TaskFilterService taskFilterService;

    public TaskFilterController(TaskFilterService taskFilterService) {
        this.taskFilterService = taskFilterService;
    }


    @GetMapping
    public String searchTasks(@RequestParam String search,
                              Model model){

        List<TaskResponseDto> filterList = taskFilterService.findBySearchTerm(search);
        model.addAttribute("filterList", filterList);

        return "";
    }
}
