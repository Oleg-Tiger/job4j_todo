package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.*;
import ru.job4j.todo.service.*;
import ru.job4j.todo.util.UserUtil;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RequestMapping("/tasks")
@Controller
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String all(Model model, HttpSession httpSession) {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.findAll(user));
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
    public String create(@ModelAttribute Task task,
                         @RequestParam("category.id") List<Integer> categoryId,
                         HttpSession session) {
        task.setUser(UserUtil.getUserFromSession(session));
        priorityService.findById(task);
        categoryService.findByIds(categoryId, task);
        if (!taskService.add(task)) {
            return "error/404";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/completed")
    public String completed(Model model, HttpSession httpSession) {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("completedTasks", taskService.findFilter(true, user));
        model.addAttribute("user", user);
        return "task/completed";
    }

    @GetMapping("/new")
    public String taskNew(Model model, HttpSession httpSession) {
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("newTasks", taskService.findFilter(false, user));
        model.addAttribute("user", user);
        return "task/new";
    }

    @GetMapping("/description/{id}")
    public String description(Model model, @PathVariable("id") int id, HttpSession httpSession) {
        Optional<Task> task =  taskService.findById(id);
        if (task.isEmpty()) {
            return "error/404";
        }
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("task", task.get());
        return "task/description";
    }

    @GetMapping("/complete/{id}")
    public String complete(Model model, @PathVariable("id") int id, HttpSession httpSession) {
        if (!taskService.updateDone(id)) {
            return "error/404";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/notComplete/{id}")
    public String notComplete(Model model, @PathVariable("id") int id, HttpSession httpSession) {
        if (!taskService.updateNotDone(id)) {
            return "error/404";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession httpSession) {
        if (!taskService.delete(id)) {
            return "error/404";
        }
        return "redirect:/tasks/";
    }

    @GetMapping("/update/{id}")
    public String formUpdate(Model model, @PathVariable("id") int id, HttpSession httpSession) {
        Optional<Task> task =  taskService.findById(id);
        if (task.isEmpty()) {
            return "error/404";
        }
        User user = UserUtil.getUserFromSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("task", task.get());
        return "task/update";
    }

    @PostMapping("/update")
    public String taskUpdate(@ModelAttribute Task task, @RequestParam("category.id") List<Integer> categoryId) {
        Optional<Task> inDb =  taskService.findById(task.getId());
        if (inDb.isEmpty()) {
            return "error/404";
        }
        priorityService.findById(task);
        categoryService.findByIds(categoryId, task);
        if (!taskService.update(task, inDb.get())) {
            return "error/404";
        }
        return "redirect:/tasks/";
    }

}
