package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;

@Service
public class HbmCategoryService implements CategoryService {

    private final CategoryRepository repository;

    public HbmCategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean findByIds(List<Integer> ids, Task task) {
        for (Integer id : ids) {
            Optional<Category> category = repository.findById(id);
            if (category.isEmpty()) {
                return false;
            }
            task.getCategories().add(category.get());
        }
        return true;
    }
}
