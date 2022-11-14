package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;

@Repository
public class TaskRepository {

    private final SessionFactory sf;

    public TaskRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("from ru.job4j.todo.model.Task", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Task add(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    public  List<Task> findFilter(boolean done) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(
                "from Task as t where t.done = :fDone", Task.class);
        query.setParameter("fDone", done);
        List<Task> result = query.getResultList();
        session.close();
        return result;
    }
}
