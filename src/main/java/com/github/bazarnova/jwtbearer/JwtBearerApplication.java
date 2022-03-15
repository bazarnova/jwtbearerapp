package com.github.bazarnova.jwtbearer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class JwtBearerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtBearerApplication.class, args);
    }
}
