package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.*;
import ru.job4j.todo.service.*;
import ru.job4j.todo.util.UserUtil;
import ru.job4j.todo.validation.Validate;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/tasks")
@Controller
public class TaskController {

    private final TaskService service;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    public TaskController(TaskService service, PriorityService priorityService, CategoryService categoryService) {
        this.service = service;
        this.priorityService = priorityService;
        this.categoryService = categoryService;
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
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("task", new Task(0, "Заполните поле", "Заполните поле"));
        return "task/add";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpServletResponse response,
                         @RequestParam("category.id") List<Integer> categoryId,
                         HttpSession session) throws IOException {
        int id = task.getPriority().getId();
        Optional<Priority> priority = priorityService.findById(id);
        Validate.checkOptional(priority, response);
        task.setPriority(priority.get());
        task.setUser(UserUtil.getUserFromSession(session));
        for (Integer ids : categoryId) {
            Optional<Category> optionalCategory = categoryService.findById(ids);
            Validate.checkOptional(optionalCategory, response);
            task.getCategories().add(optionalCategory.get());
        }
        Optional<Task> result = service.add(task);
        Validate.checkOptional(result, response);
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
        Validate.checkOptional(result, response);
        Task task = result.get();
        model.addAttribute("task", task);
        return "task/description";
    }

    @GetMapping("/complete/{id}")
    public String complete(Model model, @PathVariable("id") int id,
                           HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        Validate.updateDeleteComplete(service.updateDone(id), response);
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/notComplete/{id}")
    public String notComplete(Model model, @PathVariable("id") int id,
                           HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        Validate.updateDeleteComplete(service.updateNotDone(id), response);
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id,
                         HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        Validate.updateDeleteComplete(service.delete(id), response);
        model.addAttribute("tasks", service.findAll());
        return "task/all";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(Model model, @PathVariable("id") int id,
                             HttpServletResponse response, HttpSession httpSession) throws IOException {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        Optional<Task> result = service.findById(id);
        Validate.checkOptional(result, response);
        model.addAttribute("task", result.get());
        return "task/update";
    }

    @PostMapping("/update")
    public String taskUpdate(@ModelAttribute Task task, HttpServletResponse response) throws IOException {
        int id = task.getPriority().getId();
        Optional<Priority> priority = priorityService.findById(id);
        Validate.checkOptional(priority, response);
        task.setPriority(priority.get());
        Validate.updateDeleteComplete(service.update(task), response);
        return "redirect:/tasks/";
    }

}
