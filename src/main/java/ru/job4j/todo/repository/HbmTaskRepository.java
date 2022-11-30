package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Optional;

@Repository
public class HbmTaskRepository implements TaskRepository {

    private final SessionFactory sf;

    public HbmTaskRepository(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("from Task as t join fetch t.priority", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Optional<Task> add(Task task) {
        Optional<Task> result = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            result = Optional.of(task);
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public  List<Task> findFilter(boolean done) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(
                "from Task as t join fetch t.priority where t.done = :fDone", Task.class);
        query.setParameter("fDone", done);
        List<Task> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(
                "from Task as t where t.id = :fId", Task.class);
        query.setParameter("fId", id);
        Optional<Task> result = query.uniqueResultOptional();
        session.close();
        return result;
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
        Session session = sf.openSession();
        int result = 0;
        try {
            session.beginTransaction();
            result = session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result != 0;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        int result = 0;
        try {
            session.beginTransaction();
            result = session.createQuery(
                            "UPDATE Task SET name = :fName, description = :fDescription, priority_id = :fPriority WHERE id = :fId")
                    .setParameter("fName", task.getName())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fPriority", task.getPriority().getId())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result != 0;
    }

    private boolean changeDone(Integer id, boolean done) {
        Session session = sf.openSession();
        int result = 0;
        try {
            session.beginTransaction();
            result = session.createQuery(
                            "UPDATE Task SET done = :fDone WHERE id = :fId")
                    .setParameter("fDone", done)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result != 0;
    }
}
