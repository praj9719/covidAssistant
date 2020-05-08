package com.pentagon.covidassisitant;

public class Message {
    String message;
    boolean b;

    public Message(String message, boolean b) {
        this.message = message;
        this.b = b;
    }

    public String getMessage() {
        return message;
    }

    public boolean isB() {
        return b;
    }
}
