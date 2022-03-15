package com.github.bazarnova.jwtbearer.repository;

import com.github.bazarnova.jwtbearer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
