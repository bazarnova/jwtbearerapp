package com.jwtbearer.service.impl;

import com.jwtbearer.model.Message;
import com.jwtbearer.model.RequestMessage;
import com.jwtbearer.repository.MessageRepository;
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

    @Override
    public List<Message> getMessages(RequestMessage requestMessage) {
        String message = requestMessage.getMessage();
        String[] words = message.split(" ");

        Integer count = Integer.parseInt(words[1]);
        if(count>500){
            count = 500;
        }
        String userName = requestMessage.getName();
        return messageRepository.findMessagesByUserId(count, userName);
    }

    @Override
    public void save(Message message) {
        messageRepository.saveAndFlush(message);
    }

}
