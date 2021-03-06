package com.github.bazarnova.jwtbearer.model;

import javax.persistence.*;

@Entity
@Table(name = "messages", schema = "jwtbearer")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String message;

    public Message() {
    }

    public Message(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public Message(Long id, User user, String message) {
        this.id = id;
        this.user = user;
        this.message = message;
    }

    public Message(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

