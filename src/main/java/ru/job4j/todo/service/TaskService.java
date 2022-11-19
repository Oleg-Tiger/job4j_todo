package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAll();

    Optional<Task> add(Task task);

    List<Task> findFilter(boolean done);

    Optional<Task> findById(Integer id);

    boolean updateDone(Task task);

    boolean delete(Integer id);

    boolean update(Task task);
}
