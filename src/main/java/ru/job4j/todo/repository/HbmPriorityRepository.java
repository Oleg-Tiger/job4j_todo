package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import java.util.List;
import java.util.Optional;

@Repository
public class HbmPriorityRepository implements PriorityRepository {

    private final SessionFactory sf;

    public HbmPriorityRepository(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public List<Priority> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Priority> result = session.createQuery("from Priority", Priority.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Optional<Priority> findById(int id) {
        Session session = sf.openSession();
        Query<Priority> query = session.createQuery(
                "from Priority where id = :fId", Priority.class);
        query.setParameter("fId", id);
        Optional<Priority> result = query.uniqueResultOptional();
        session.close();
        return result;
    }
}
