package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HbmUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<User> add(User user) {
        Optional<User> result;
        try {
            crudRepository.run(session -> session.save(user));
            result = Optional.of(user);
        } catch (Exception e) {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return crudRepository.optional("from User as u where u.email = :fEmail AND u.password = :fPassword", User.class,
                Map.of("fEmail", email, "fPassword", password));

    }
}

