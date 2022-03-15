package com.github.bazarnova.jwtbearer.controller;

import com.github.bazarnova.jwtbearer.model.Message;
import com.github.bazarnova.jwtbearer.model.RequestMessage;
import com.github.bazarnova.jwtbearer.service.MessageService;
import com.github.bazarnova.jwtbearer.service.UserService;
import org.apache.logging.log4j.util.Strings;
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

        String historyRequest = messageService.getHistorySize(requestMessage.getMessage());
        if (Strings.isBlank(historyRequest)) {
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
}
