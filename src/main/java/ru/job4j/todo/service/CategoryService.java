package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    boolean findByIds(List<Integer> ids, Task task);
}
