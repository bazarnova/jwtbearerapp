package com.jwtbearer.controller;

import com.jwtbearer.model.Message;
import com.jwtbearer.model.Msg;
import com.jwtbearer.repository.MessageRepository;
import com.jwtbearer.repository.UserRepository;
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
@RequestMapping(value = "/")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/message")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody Msg msg) {
        try {
            Message m = new Message(userRepository.findByName(msg.getName()), msg.getMessage());
            messageRepository.saveAndFlush(m);

            Map<String, String> response = new HashMap<>();
            response.put("token", m.getId() + "");

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }



}
