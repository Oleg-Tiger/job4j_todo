package ru.job4j.todo.repository;

import ru.job4j.todo.model.Priority;
import java.util.List;
import java.util.Optional;

public interface PriorityRepository {

    public List<Priority> findAll();

    public Optional<Priority> findById(int id);
}
