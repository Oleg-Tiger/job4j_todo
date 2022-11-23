package ru.job4j.todo.util;

import ru.job4j.todo.model.User;
import javax.servlet.http.HttpSession;

public final class UserUtil {

    private UserUtil() {
    }

    public static User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}