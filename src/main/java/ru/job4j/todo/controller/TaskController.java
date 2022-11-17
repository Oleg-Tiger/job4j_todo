package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.AbstractTaskService;
import ru.job4j.todo.service.TaskService;

@Controller
public class TaskController {

    private final AbstractTaskService service;

    public TaskController(AbstractTaskService service) {
        this.service = service;
    }

    @GetMapping("/allTasks")
    public String allTasks(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "allTasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task(0, "Заполните поле", "Заполните поле"));
        return "addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        service.add(task);
        return "redirect:/allTasks";
    }

    @GetMapping("/completedTasks")
    public String completedTasks(Model model) {
        model.addAttribute("completedTasks", service.findFilter(true));
        return "completedTasks";
    }

    @GetMapping("/newTasks")
    public String newTasks(Model model) {
        model.addAttribute("newTasks", service.findFilter(false));
        return "newTasks";
    }

    @GetMapping("/taskDescription/{taskId}")
    public String taskDescription(Model model, @PathVariable("taskId") int id) {
        Task task = service.findById(id).get();
        model.addAttribute("task", task);
        return "taskDescription";
    }

    @GetMapping("/complete/{taskId}")
    public String taskComplete(Model model, @PathVariable("taskId") int id) {
        Task task = service.findById(id).get();
        task.setDone(true);
        service.updateDone(task);
        model.addAttribute("task", task);
        return "taskDescription";
    }

}
