package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HbmTaskRepository implements TaskRepository {

    private final CrudRepository crudRepository;

    @Override
    public List<Task> findAll() {
        return crudRepository.query(
                "select distinct t from Task t left join fetch t.categories join fetch t.priority", Task.class
        );
    }

    @Override
    public boolean add(Task task) {
        boolean result = true;
        try {
            crudRepository.run(session -> session.save(task));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public  List<Task> findFilter(boolean done) {
        return crudRepository.query(
                "select distinct t from Task t left join fetch t.categories join fetch t.priority where t.done = :fDone",
                Task.class, Map.of("fDone", done)
        );
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return crudRepository.optional("from Task as t where t.id = :fId", Task.class,
                Map.of("fId", id));
    }

    @Override
    public boolean updateDone(Integer id) {
        return changeDone(id, true);
    }

    @Override
    public boolean updateNotDone(Integer id) {
        return changeDone(id, false);
    }

    @Override
    public boolean delete(Integer id) {
        boolean result = true;
        try {
            crudRepository.run("DELETE Task WHERE id = :fId", Map.of("fId", id));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean update(Task task) {
        boolean result = true;
        try {
            crudRepository.run(session -> session.merge(task));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private boolean changeDone(Integer id, boolean done) {
        boolean result = true;
        try {
            crudRepository.run("UPDATE Task SET done = :fDone WHERE id = :fId",
                    Map.of("fDone", done, "fId", id));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
