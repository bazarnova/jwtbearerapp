package com.jwtbearer.model;

public class RequestMessage {
    private String name;
    private String message;


    public RequestMessage(String name, String message) {
        this.name = name;
        this.message = message;

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
