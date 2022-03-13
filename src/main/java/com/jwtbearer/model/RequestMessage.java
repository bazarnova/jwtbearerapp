package com.jwtbearer.model;

public class RequestMessage {
    private String message;
    private String name;

    public RequestMessage(String message, String name) {
        this.message = message;
        this.name = name;
    }

    public RequestMessage() {
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
