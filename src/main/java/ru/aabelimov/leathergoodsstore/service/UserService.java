package ru.aabelimov.leathergoodsstore.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.User;

public interface UserService extends UserDetailsService {

    User createUser(CreateOrderDto createOrderDto);

    User getUserByUsername(String username);

    void updateUser(User user, CreateOrderDto dto);
}
