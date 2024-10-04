package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.Role;
import ru.aabelimov.leathergoodsstore.entity.User;
import ru.aabelimov.leathergoodsstore.mapper.UserMapper;
import ru.aabelimov.leathergoodsstore.repository.UserRepository;
import ru.aabelimov.leathergoodsstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceDefaultImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User %s not found!".formatted(username)));
    }

    @Override
    public User createUser(CreateOrderDto createOrderDto) {
        User user = userMapper.toEntity(createOrderDto);
        return userRepository.save(user);
    }

    @Override
    public void createUser(String username, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName("admin");
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null); // TODO::
    }

    @Override
    public void updateUser(User user, CreateOrderDto dto) {
        user.setName(dto.name());
        user.setPhoneNumber(dto.phoneNumber());
        userRepository.save(user);
    }
}
