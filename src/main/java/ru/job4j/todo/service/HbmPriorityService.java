package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.PriorityRepository;
import java.util.List;
import java.util.Optional;

@Service
public class HbmPriorityService implements PriorityService {

    private final PriorityRepository repository;

    public HbmPriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    public List<Priority> findAll() {
        return repository.findAll();
    }

    public boolean findById(Task task) {
        int id = task.getPriority().getId();
        Optional<Priority> priority = repository.findById(id);
        if (priority.isEmpty()) {
            return false;
        }
        task.setPriority(priority.get());
        return true;
    }
}
