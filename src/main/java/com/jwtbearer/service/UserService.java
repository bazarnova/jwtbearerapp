package com.jwtbearer.service;

import com.jwtbearer.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);
    User create(User user);
}
