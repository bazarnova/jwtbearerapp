package com.github.bazarnova.jwtbearer.service;


import com.github.bazarnova.jwtbearer.model.Message;
import com.github.bazarnova.jwtbearer.model.RequestMessage;

import java.util.List;

public interface MessageService {
    List<Message> getMessages(RequestMessage requestMessage);

    void save(Message message);

    void parseAndSave(RequestMessage requestMessage);

    String getHistorySize(String message);
}
