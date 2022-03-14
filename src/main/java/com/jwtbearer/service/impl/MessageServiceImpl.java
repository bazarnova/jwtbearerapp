package com.jwtbearer.service.impl;

import com.jwtbearer.model.Message;
import com.jwtbearer.model.RequestMessage;
import com.jwtbearer.model.User;
import com.jwtbearer.repository.MessageRepository;
import com.jwtbearer.security.JwtTokenProvider;
import com.jwtbearer.service.MessageService;
import com.jwtbearer.service.UserService;
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
        String message = requestMessage.getMessage();
        String[] words = message.split(" ");

        Integer count = Integer.parseInt(words[1]);
        if (count > 500) {
            count = 500;
        }
        String userName = requestMessage.getName();
        return messageRepository.findMessagesByUserId(count, userName);
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

}
