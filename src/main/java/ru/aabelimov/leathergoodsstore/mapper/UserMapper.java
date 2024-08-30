package ru.aabelimov.leathergoodsstore.mapper;

import org.springframework.stereotype.Component;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.Role;
import ru.aabelimov.leathergoodsstore.entity.User;

@Component
public class UserMapper {

    public User toEntity(CreateOrderDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setName(dto.name());
        user.setPhoneNumber(dto.phoneNumber());
        user.setRole(Role.ROLE_USER);
        return user;
    }
}
