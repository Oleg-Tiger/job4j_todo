package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.todo.service.TaskService;

@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/allTasks")
    public String allTasks(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "allTasks";
    }

}
