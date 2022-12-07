package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskRepository;
import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
public class HbmTaskService implements TaskService {

    private final TaskRepository store;

    public HbmTaskService(TaskRepository store) {
        this.store = store;
    }

    @Override
    public List<Task> findAll(User user) {
        return convertCreatedWithZone(store.findAll(), user.getUserZone());
    }

    @Override
    public boolean add(Task task) {
        return store.add(task);
    }

    @Override
    public  List<Task> findFilter(boolean done, User user) {
        return convertCreatedWithZone(store.findFilter(done), user.getUserZone());
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return store.findById(id);
    }

    @Override
    public boolean updateDone(Integer id) {
        return store.updateDone(id);
    }

    @Override
    public boolean updateNotDone(Integer id) {
        return store.updateNotDone(id);
    }

    @Override
    public boolean delete(Integer id) {
        return store.delete(id);
    }

    @Override
    public boolean update(Task task, Task inDb) {
        task.setCreated(inDb.getCreated());
        task.setDone(inDb.isDone());
        task.setUser(inDb.getUser());
        return store.update(task);
    }

    private List<Task> convertCreatedWithZone(List<Task> withoutZone, String zoneId) {
        for (Task task : withoutZone) {
            LocalDateTime created = task.getCreated();
            ZonedDateTime zonedForDefault = created.atZone(ZoneId.systemDefault());
            ZonedDateTime zonedForUser = zonedForDefault.withZoneSameInstant(ZoneId.of(zoneId));
            task.setCreated(zonedForUser.toLocalDateTime());
        }
        return withoutZone;
    }
}
