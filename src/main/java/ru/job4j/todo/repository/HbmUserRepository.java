package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import java.util.Optional;

@Repository
public class HbmUserRepository implements UserRepository {

    private final SessionFactory sf;

    public HbmUserRepository(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            result = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(
                "from User as u where u.email = :fEmail AND u.password = :fPassword", User.class);
        query.setParameter("fEmail", email);
        query.setParameter("fPassword", password);
        Optional<User> result = query.uniqueResultOptional();
        session.close();
        return result;
    }
}

