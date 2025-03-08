package com.mallang.springrediscache.domain.service;

import com.mallang.springrediscache.domain.entity.User;
import com.mallang.springrediscache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }

}