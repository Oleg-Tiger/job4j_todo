package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

@Repository
public class HbmCategoryRepository implements CategoryRepository {

    private final SessionFactory sf;

    public HbmCategoryRepository(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public List<Category> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Category> result = session.createQuery("from Category", Category.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Optional<Category> findById(int id) {
        Session session = sf.openSession();
        Query<Category> query = session.createQuery(
                "from Category where id = :fId", Category.class);
        query.setParameter("fId", id);
        Optional<Category> result = query.uniqueResultOptional();
        session.close();
        return result;
    }
}
