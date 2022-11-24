package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.UserUtil;
import ru.job4j.todo.validation.ValidateTask;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String all(Model model, HttpSession httpSession) {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession httpSession) {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
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
    public String completed(Model model, HttpSession httpSession) {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("completedTasks", service.findFilter(true));
        return "task/completed";
    }

    @GetMapping("/new")
    public String taskNew(Model model, HttpSession httpSession) {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("newTasks", service.findFilter(false));
        return "task/new";
    }

    @GetMapping("/description/{id}")
    public String description(Model model, @PathVariable("id") int id,
                              HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        Optional<Task> result = service.findById(id);
        ValidateTask.checkOptional(result, response);
        Task task = result.get();
        model.addAttribute("task", task);
        return "task/description";
    }

    @GetMapping("/complete/{id}")
    public String complete(Model model, @PathVariable("id") int id,
                           HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        ValidateTask.updateDeleteComplete(service.updateDone(id), response);
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/notComplete/{id}")
    public String notComplete(Model model, @PathVariable("id") int id,
                           HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        ValidateTask.updateDeleteComplete(service.updateNotDone(id), response);
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id,
                         HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        ValidateTask.updateDeleteComplete(service.delete(id), response);
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(Model model, @PathVariable("id") int id,
                             HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        Optional<Task> result = service.findById(id);
        ValidateTask.checkOptional(result, response);
        model.addAttribute("task", result.get());
        return "task/update";
    }

    @PostMapping("/update")
    public String taskUpdate(@ModelAttribute Task task, HttpServletResponse response) throws IOException {
        ValidateTask.updateDeleteComplete(service.update(task), response);
        return "redirect:/tasks/";
    }

}
