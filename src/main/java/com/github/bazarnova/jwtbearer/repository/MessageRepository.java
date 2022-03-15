package com.github.bazarnova.jwtbearer.repository;

import com.github.bazarnova.jwtbearer.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findHistorySizeForUser(Integer count, String name);
}
