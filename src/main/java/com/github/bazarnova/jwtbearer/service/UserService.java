package com.github.bazarnova.jwtbearer.service;


import com.github.bazarnova.jwtbearer.model.RequestCredentials;
import com.github.bazarnova.jwtbearer.model.User;

public interface UserService {
    String getTokenForCredentials(RequestCredentials requestCredentials);
    User findByName(String name);

}
