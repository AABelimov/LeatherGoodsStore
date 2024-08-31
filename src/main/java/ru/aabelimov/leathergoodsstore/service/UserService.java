package ru.aabelimov.leathergoodsstore.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.User;

public interface UserService extends UserDetailsService {

    User getUserByUsername(String username);

    User createUser(CreateOrderDto createOrderDto);
}
