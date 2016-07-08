package com.tonikamitv.loginregister;

import java.io.Serializable;

public class User implements Serializable {

    public enum user_types { student, tutor, professor}

    String name_first, name_last, username, password, email;
    user_types user_type;
    int id;


    public User(String nameF, String nameL, int id, String username, String password, String email, user_types type) {
        this.name_first = nameF;
        this.name_last = nameL;
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
        this.user_type = type;
    }
/*
    public User(String username, String password) {
        this("", -1, username, password);
    }
    */
}
