package com.jwtbearer.repository;

import com.jwtbearer.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesByUserId(Integer count, String name);
}
