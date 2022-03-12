package com.jwtbearer.model;

public class Msg {
    private String message;
    private String name;

    public Msg(String message, String name) {
        this.message = message;
        this.name = name;
    }

    public Msg() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
