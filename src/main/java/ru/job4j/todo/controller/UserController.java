package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user,
                               @RequestParam("userZone.id") String userZoneId) {
        Optional<User> regUser = userService.add(user, userZoneId);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Данная электронная почта уже используется");
            return "redirect:/registrationPage?fail=true";
        }
        return "redirect:/loginPage";
    }

    @GetMapping("/registrationPage")
    public String registrationPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("userZones", ZoneId.getAvailableZoneIds());
        model.addAttribute("fail", fail != null);
        return "user/registration";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/tasks/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}

