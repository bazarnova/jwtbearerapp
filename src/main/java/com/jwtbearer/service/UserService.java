package com.jwtbearer.service;

import com.jwtbearer.model.Cred;

public interface UserService {
    String getTokenForCredentials(Cred cred);
}
