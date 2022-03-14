package com.jwtbearer.service;

import com.jwtbearer.model.Message;
import com.jwtbearer.model.RequestMessage;

import java.util.List;

public interface MessageService {
    List<Message> getMessages(RequestMessage requestMessage);

    void save(Message message);

    void parseAndSave(RequestMessage requestMessage);
}
