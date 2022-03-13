package com.jwtbearer.controller;

import com.jwtbearer.model.RequestCredentials;
import com.jwtbearer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth/")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody RequestCredentials requestCredentials) {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("token", userService.getTokenForCredentials(requestCredentials));

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
