package com.tonikamitv.loginregister;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

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

        String[] userMessages = {"Message1", "Message2", "Message3"};

        final ListAdapter theAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userMessages);
        ListView theListView = (ListView) rootView.findViewById(R.id.listView);

        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


            }
        });


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