package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    Optional<Task> add(Task task);

    List<Task> findFilter(boolean done);

    Optional<Task> findById(Integer id);

    boolean updateDone(Integer id);

    boolean updateNotDone(Integer id);

    boolean delete(Integer id);

    boolean update(Task task);
}
