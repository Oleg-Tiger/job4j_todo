package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements AbstractTaskService {

    private final TaskStore store;

    public TaskService(TaskStore store) {
        this.store = store;
    }

    @Override
    public List<Task> findAll() {
        return store.findAll();
    }

    @Override
    public Task add(Task task) {
        return store.add(task);
    }

    @Override
    public  List<Task> findFilter(boolean done) {
        return store.findFilter(done);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return store.findById(id);
    }

    @Override
    public void updateDone(Task task) {
        store.updateDone(task);
    }

    @Override
    public boolean delete(Integer id) {
        return store.delete(id);
    }

    @Override
    public boolean update(Task task) {
        return store.update(task);
    }
}
