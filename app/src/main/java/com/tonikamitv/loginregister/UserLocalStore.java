package com.tonikamitv.loginregister;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tundealao on 29/03/15.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("name_first", user.name_first);
        userLocalDatabaseEditor.putString("name_last", user.name_last);
        userLocalDatabaseEditor.putString("hot_spot_id", user.hot_Spot_id +"");
        userLocalDatabaseEditor.putString("longitude", user.longitude+"");
        userLocalDatabaseEditor.putString("latitude", user.latitude+"");
        userLocalDatabaseEditor.putString("id", user.id + "");
        userLocalDatabaseEditor.putString("username", user.username);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.putString("email", user.email);
        userLocalDatabaseEditor.putString("status", user.status+"");
        userLocalDatabaseEditor.putString("user_type", user.user_type.toString());
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public User getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String fname = userLocalDatabase.getString("name_first", "");
        String lname = userLocalDatabase.getString("name_last", "");
        userLocalDatabase.getString("status","");

        int id = userLocalDatabase.getInt("id", 0);
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String email = userLocalDatabase.getString("email", "");
        //String name = userLocalDatabase.getString("user_type", "");


        User user = new User(fname, lname, id, username, password, email, User.user_types.student);
       // return user;
        return user;
    }
}
