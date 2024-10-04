package ru.aabelimov.leathergoodsstore.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.entity.Role;
import ru.aabelimov.leathergoodsstore.entity.User;
import ru.aabelimov.leathergoodsstore.service.UserService;

@Component
@RequiredArgsConstructor
public class CreateAdmin implements ApplicationRunner {

    private final UserService userService;

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = userService.getUserByUsername(username);
        if (user == null && !username.isBlank() && !password.isBlank()) {
            userService.createUser(username, password, Role.ROLE_ADMIN);
        }
    }
}
