package com.tonikamitv.loginregister;

import android.media.Image;

import java.io.Serializable;

public class User implements Serializable {

    public enum user_types { student, tutor, professor}

    String name_first, name_last, username, password, email;
    user_types user_type;
    int id, hot_Spot_id;
    float latitude, longitude;
    boolean status;
    Image avatar;


    public User(String fname, String lname, int id, String username, String password, String email, user_types type) {
        this.name_first = fname;
        this.name_last = lname;
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.user_type = type;
        this.status = false;
        this.avatar = null;
        this.latitude = 0;
        this.longitude = 0;
        this.hot_Spot_id = 0;
    }

    public User(String username, String password) {
        this.name_first = "";
        this.name_last = "";
        this.id = 0;
        this.username = username;
        this.password = password;
        this.email = "";
        this.user_type = null;
        this.status = false;
        this.avatar = null;
        this.latitude = 0;
        this.longitude = 0;
        this.hot_Spot_id = 0;
        //this("User", -1, username, password);
    }

}
