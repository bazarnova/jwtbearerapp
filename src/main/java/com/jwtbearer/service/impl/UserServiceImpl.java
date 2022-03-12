package com.jwtbearer.service.impl;

import com.jwtbearer.model.Cred;
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

    private User validateUserAndPassword(Cred cred) {
        if (cred == null
                || Strings.isBlank(cred.getName())
                || Strings.isBlank(cred.getPassword())) {
            throw new RuntimeException("Payload is null");
        }

        User user = userRepository.findByName(cred.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getPassword() != null && user.getPassword().equals(cred.getPassword())) {
            return user;
        }

        throw new RuntimeException("Wrong credentials");
    }

    @Override
    public String getTokenForCredentials(Cred cred) {
        User user = validateUserAndPassword(cred);
        return tokenProvider.createToken(user);
    }
}
