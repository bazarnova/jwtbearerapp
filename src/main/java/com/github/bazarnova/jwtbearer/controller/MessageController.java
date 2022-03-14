package com.jwtbearer.controller;

import com.jwtbearer.model.Message;
import com.jwtbearer.model.RequestMessage;
import com.jwtbearer.service.MessageService;
import com.jwtbearer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody RequestMessage requestMessage) {

        if (isRequiredHistory(requestMessage)) {
            List<Message> messageList = messageService.getMessages(requestMessage);
            Map<String, Object> response = new HashMap<>();
            response.put("response", messageList.stream()
                    .map(Message::getMessage)
                    .collect(Collectors.toList()));
            return ResponseEntity.ok(response);
        } else {
            try {
                messageService.parseAndSave(requestMessage);

                Map<String, Object> response = new HashMap<>();
                response.put("response", "Message saved");

                return ResponseEntity.ok(response);

            } catch (AuthenticationException e) {
                throw new BadCredentialsException("Invalid username or password");
            }
        }
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Map<String, String>> handleException(RuntimeException ex) {
        Map<String, String> m = new HashMap<>();
        m.put("Error", ex.getMessage());
        return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
    }

    public boolean isRequiredHistory(RequestMessage requestMessage) {
        String message = requestMessage.getMessage();
        String[] words = message.split(" ");
        return words.length >= 2 && "history".equalsIgnoreCase(words[0]) && words[1].matches("[0-9]+");
    }
}
