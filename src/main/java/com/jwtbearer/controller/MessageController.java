package com.jwtbearer.controller;

import com.jwtbearer.model.Message;
import com.jwtbearer.model.RequestMessage;
import com.jwtbearer.service.MessageService;
import com.jwtbearer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/message")
    public ResponseEntity<List<String>> sendMessage(@RequestBody RequestMessage requestMessage) {

        if (isRequiredHistory(requestMessage)) {
            List<Message> response = messageService.getMessages(requestMessage);

            return ResponseEntity.ok(response.stream()
                    .map(Message::getMessage)
                    .collect(Collectors.toList())
            );
        } else {
            try {
                Message message = new Message(userService.findByName(requestMessage.getName()), requestMessage.getMessage());
                messageService.save(message);

                List<String> response = new ArrayList<>();
                response.add("Message saved");

                return ResponseEntity.ok(response);

            } catch (AuthenticationException e) {
                throw new BadCredentialsException("Invalid username or password");
            }
        }
    }

    public boolean isRequiredHistory(RequestMessage requestMessage) {
        String message = requestMessage.getMessage();
        String[] words = message.split(" ");
        return words.length >= 2 && words[0].equalsIgnoreCase("history") && words[1].matches("[0-9]+");
    }
}
