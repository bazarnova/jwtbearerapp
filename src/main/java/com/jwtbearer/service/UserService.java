package com.jwtbearer.service;

import com.jwtbearer.model.RequestCredentials;
import com.jwtbearer.model.User;

public interface UserService {
    String getTokenForCredentials(RequestCredentials requestCredentials);
    User findByName(String name);

}
