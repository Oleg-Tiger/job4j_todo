package ru.job4j.todo.validation;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class Validate {

    public static void checkOptional(Optional<?> result, HttpServletResponse response) throws IOException {
        if (result.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public static void updateDeleteComplete(boolean result, HttpServletResponse response) throws IOException {
        if (!result) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
