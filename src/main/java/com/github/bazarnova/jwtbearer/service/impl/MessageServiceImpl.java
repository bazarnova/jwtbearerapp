package com.github.bazarnova.jwtbearer.service.impl;

import com.github.bazarnova.jwtbearer.model.Message;
import com.github.bazarnova.jwtbearer.model.RequestMessage;
import com.github.bazarnova.jwtbearer.model.User;
import com.github.bazarnova.jwtbearer.repository.MessageRepository;
import com.github.bazarnova.jwtbearer.security.JwtTokenProvider;
import com.github.bazarnova.jwtbearer.service.MessageService;
import com.github.bazarnova.jwtbearer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public List<Message> getMessages(RequestMessage requestMessage) {
        String userName = requestMessage.getName();
        String message = requestMessage.getMessage();

        int count = Integer.parseInt(getHistorySize(message));
        if (count > 500) {
            count = 500;
        }

        return messageRepository.findHistorySizeForUser(count, userName);
    }

    @Override
    public void save(Message message) {
        messageRepository.saveAndFlush(message);
    }

    @Override
    public void parseAndSave(RequestMessage requestMessage) {
        String userFromToken = tokenProvider.getCurrentUserNameFromToken();

        if (userFromToken == null || !userFromToken.equals(requestMessage.getName())) {
            throw new RuntimeException("Wrong user or token.");
        }
        User user = userService.findByName(userFromToken);
        Message message = new Message(user, requestMessage.getMessage());
        save(message);
    }

    public String getHistorySize(String message) {
        String[] words = message.split(" ");

        if (words.length == 2 && "history".equalsIgnoreCase(words[0]) && words[1].matches("[0-9]+")) {
            return words[1];
        } else {
            return null;
        }
    }

}
