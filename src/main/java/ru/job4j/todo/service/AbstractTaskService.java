package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface AbstractTaskService {

    List<Task> findAll();

    Task add(Task task);

    List<Task> findFilter(boolean done);

    Optional<Task> findById(Integer id);

    void updateDone(Task task);

    boolean delete(Integer id);

    boolean update(Task task);
}
