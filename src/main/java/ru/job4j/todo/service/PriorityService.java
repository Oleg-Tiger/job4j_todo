package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import java.util.List;

public interface PriorityService {

    public List<Priority> findAll();

    public boolean findById(Task task);
}
