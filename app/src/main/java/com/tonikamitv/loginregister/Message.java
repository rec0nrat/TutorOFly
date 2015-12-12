package com.tonikamitv.loginregister;

import java.io.Serializable;

public class Message implements Serializable {

    String name, username, password, message;
    int age;

    public Message(String name, int age, String username, String password, String message) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
        this.message = message;
    }

    public Message(String username, String message) {
        this("", -1, username, "", message);
    }
}