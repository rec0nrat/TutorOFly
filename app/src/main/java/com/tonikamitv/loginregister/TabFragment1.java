package com.tonikamitv.loginregister;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tylweiss on 11/22/2015.
 */
public class TabFragment1 extends Fragment implements View.OnClickListener{
    User user;
    EditText messageText;
    Button button;
    View container;
    //UserLocalStore userLocalStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.tab_fragment_1 ,null);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        button = (Button) view.findViewById(R.id.btnPostMessage);
        messageText = (EditText) view.findViewById(R.id.tutorMessageInput);
        container = view.findViewById(R.id.fragmentContainer);
        button.setOnClickListener(this);
        user = ((MainActivity)getActivity()).getCurrentUser();

    }

    public void postMessageClicked(){

        ServerRequests serverRequest = new ServerRequests(getActivity());

        String message = messageText.getText().toString();

        serverRequest.storeUserMessageInBackground(user, message, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                   // showErrorMessage();
                } else {
                    //logUserIn(returnedUser);
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        postMessageClicked();
    }


}