package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;
import java.util.Optional;

@Service
public class HbmUserService implements UserService {

    private final UserRepository repository;

    public HbmUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> add(User user) {
        return repository.add(user);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return repository.findUserByEmailAndPassword(email, password);
    }
}

