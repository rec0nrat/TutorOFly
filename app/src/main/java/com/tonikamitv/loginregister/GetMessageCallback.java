package com.tonikamitv.loginregister;

import java.util.ArrayList;

/**
 * Created by tylweiss on 12/16/2015.
 */
public interface GetMessageCallback {

    public abstract void done(ArrayList<Message> returnedMessages);
}
