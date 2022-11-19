package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.validation.ValidateTask;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequestMapping("/tasks")
@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String all(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("task", new Task(0, "Заполните поле", "Заполните поле"));
        return "task/add";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpServletResponse response) throws IOException {
        Optional<Task> result = service.add(task);
        ValidateTask.checkOptional(result, response);
        return "redirect:/tasks/";
    }

    @GetMapping("/completed")
    public String completed(Model model) {
        model.addAttribute("completedTasks", service.findFilter(true));
        return "task/completed";
    }

    @GetMapping("/new")
    public String taskNew(Model model) {
        model.addAttribute("newTasks", service.findFilter(false));
        return "task/new";
    }

    @GetMapping("/description/{id}")
    public String description(Model model, @PathVariable("id") int id, HttpServletResponse response) throws IOException {
        Optional<Task> result = service.findById(id);
        ValidateTask.checkOptional(result, response);
        Task task = result.get();
        model.addAttribute("task", task);
        return "task/description";
    }

    @GetMapping("/complete/{id}")
    public String complete(Model model, @PathVariable("id") int id, HttpServletResponse response) throws IOException {
        Optional<Task> result = service.findById(id);
        ValidateTask.checkOptional(result, response);
        Task task = result.get();
        boolean done = task.isDone();
        task.setDone(!done);
        ValidateTask.updateDeleteComplete(response, service.updateDone(task));
        model.addAttribute("task", task);
        return "task/description";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpServletResponse response) throws IOException {
        ValidateTask.updateDeleteComplete(response, service.delete(id));
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(Model model, @PathVariable("id") int id, HttpServletResponse response) throws IOException {
        Optional<Task> result = service.findById(id);
        ValidateTask.checkOptional(result, response);
        model.addAttribute("task", result.get());
        return "task/update";
    }

    @PostMapping("/update")
    public String taskUpdate(@ModelAttribute Task task, HttpServletResponse response) throws IOException {
        ValidateTask.updateDeleteComplete(response, service.update(task));
        return "redirect:/tasks/";
    }

}
