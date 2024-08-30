package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.entity.User;
import ru.aabelimov.leathergoodsstore.mapper.UserMapper;
import ru.aabelimov.leathergoodsstore.repository.UserRepository;
import ru.aabelimov.leathergoodsstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceDefaultImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User %s not found!".formatted(username)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null); // TODO::
    }

    @Override
    public User createUser(CreateOrderDto createOrderDto) {
        User user = userMapper.toEntity(createOrderDto);
        return userRepository.save(user);
    }
}
