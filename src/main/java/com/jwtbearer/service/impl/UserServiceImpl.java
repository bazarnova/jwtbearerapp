package com.jwtbearer.service.impl;

import com.jwtbearer.model.RequestCredentials;
import com.jwtbearer.model.User;
import com.jwtbearer.repository.UserRepository;
import com.jwtbearer.security.JwtTokenProvider;
import com.jwtbearer.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private User validateUserAndPassword(RequestCredentials requestCredentials) {
        if (requestCredentials == null
                || Strings.isBlank(requestCredentials.getName())
                || Strings.isBlank(requestCredentials.getPassword())) {
            throw new RuntimeException("Payload is null");
        }

        User user = findByName(requestCredentials.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getPassword() != null && user.getPassword().equals(requestCredentials.getPassword())) {
            return user;
        }

        throw new RuntimeException("Wrong credentials");
    }

    @Override
    public String getTokenForCredentials(RequestCredentials requestCredentials) {
        User user = validateUserAndPassword(requestCredentials);
        return tokenProvider.createToken(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
