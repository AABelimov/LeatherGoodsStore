package ru.aabelimov.leathergoodsstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.aabelimov.leathergoodsstore.repository.UserRepository;
import ru.aabelimov.leathergoodsstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceDefaultImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User %s not found!".formatted(username)));
    }
}
