package com.tonikamitv.loginregister;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tylweiss on 12/16/2015.
 */
public class Message implements Serializable {

    private String msg_title, init_msg_txt, username;

    public String getMsg_title() {
        return msg_title;
    }

    public void setMsg_title(String msg_title) {
        this.msg_title = msg_title;
    }

    public String getInit_msg_txt() {
        return init_msg_txt;
    }

    public void setInit_msg_txt(String init_msg_txt) {
        this.init_msg_txt = init_msg_txt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public int getHelp_cnt() {
        return help_cnt;
    }

    public void setHelp_cnt(int help_cnt) {
        this.help_cnt = help_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    public int getSolved_cnt() {
        return solved_cnt;
    }

    public void setSolved_cnt(int solved_cnt) {
        this.solved_cnt = solved_cnt;
    }

    public int getLikes_cnt() {
        return likes_cnt;
    }

    public void setLikes_cnt(int likes_cnt) {
        this.likes_cnt = likes_cnt;
    }

    public String getInit_time_stamp() {
        return init_time_stamp;
    }

    public void setInit_time_stamp(String init_time_stamp) {
        this.init_time_stamp = init_time_stamp;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public boolean is_anon() {
        return is_anon;
    }

    public void setIs_anon(boolean is_anon) {
        this.is_anon = is_anon;
    }

    private int post_id, user_id, tag_id, help_cnt, comment_cnt, solved_cnt, likes_cnt;
    private String  init_time_stamp, last_update, title;
    private boolean is_anon;



    public Message(String message, User user){

        //new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
        this.username = user.username;
        this.init_msg_txt = message;
        this.title = (message.length() > 70) ?  message.substring(0,67) + "..." : message;
        java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        this.post_id = 0;
        this.user_id = user.id;
        this.tag_id = 1;
        this.help_cnt = 0;
        this.comment_cnt = 0;
        this.solved_cnt = 0;
        this.likes_cnt = 0;
        this.init_time_stamp = timestamp.toString();
        this.last_update = timestamp.toString();
        this.is_anon = false;

       // java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
       // Log.i("Date/Time Request:  ", currentTimestamp.toString());

    }

    public Message( String init_txt,  String username, String timestamp, String update, int post_id, int user_id, int tag_id, int help, int comment,
                   int solved, int likes, boolean anon){
        this.username = username;
        this.init_msg_txt = init_txt;
        //java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        this.title = (init_txt.length() > 70) ?  init_txt.substring(0,67) + "..." : init_txt;
        this.post_id = post_id;
        this.user_id = user_id;
        this.tag_id = tag_id;
        this.help_cnt = help;
        this.comment_cnt = comment;
        this.solved_cnt = solved;
        this.likes_cnt = likes;
        this.init_time_stamp = timestamp;
        this.last_update = update;
        this.is_anon = anon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
