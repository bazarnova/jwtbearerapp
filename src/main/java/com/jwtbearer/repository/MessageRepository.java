package com.jwtbearer.repository;

import com.jwtbearer.model.Message;
import com.jwtbearer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<User, Long> {

    @Modifying(clearAutomatically = true)
//    @Query(value = "SELECT message FROM " +
//            "(SELECT m.message, s.name FROM jwtbearer.users s join jwtbearer.messages m on s.id=m.user_id) " +
//            "where jwtbearer.users.name = :name  ORDER BY id DESC LIMIT 0,10")
    @Query(value = "SELECT message FROM Message")
    List<Message> findTenMessagesByUserId(String name);
}
