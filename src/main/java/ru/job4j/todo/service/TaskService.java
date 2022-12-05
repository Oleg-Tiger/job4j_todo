package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAll();

    boolean add(Task task);

    List<Task> findFilter(boolean done);

    Optional<Task> findById(Integer id);

    boolean updateDone(Integer id);

    boolean updateNotDone(Integer id);

    boolean delete(Integer id);

    boolean update(Task task, Task inDb);
}
