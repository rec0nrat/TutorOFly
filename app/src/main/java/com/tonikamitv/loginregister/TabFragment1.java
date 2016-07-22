package com.tonikamitv.loginregister;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by tylweiss on 11/22/2015.
 */
public class TabFragment1 extends Fragment implements View.OnClickListener{
    User user;
    EditText messageText;
    Button button, syncButton;
    View container;
    ArrayList<String> help_board_messages = null;
    ListView theListView;
    ArrayAdapter help_board_adapter; // new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, help_board_messages);
    UserLocalStore userLocalStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.tab_fragment_1 ,null);

        theListView = (ListView) rootView.findViewById(R.id.listView);

        ///help_board_messages.add("Message1");
        //help_board_messages.add("Message2");
        //help_board_messages.add("Message10000");
        String[] ss = {"11", "22", "33"};
        help_board_messages = new ArrayList<String>();

        //help_board_messages.clear();
       // Toast.makeText(getActivity(), help_board_messages.toArray().toString(),
        //        Toast.LENGTH_LONG).show();

        //set the adapter and auto refresh if list is changed
        help_board_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, help_board_messages);
        theListView.setAdapter(help_board_adapter);
       // theAdapter.notifyDataSetChanged();
        //Debug
      //  theAdapter.add(new String("stuff"));


        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "Messsssge Clicked!",
                        Toast.LENGTH_LONG).show();

            }
        });


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        button = (Button) view.findViewById(R.id.btnPostMessage);
        syncButton = (Button)view.findViewById(R.id.btnSyncMessage);
        messageText = (EditText) view.findViewById(R.id.tutorMessageInput);
        container = view.findViewById(R.id.fragmentContainer);
        button.setOnClickListener(this);
        syncButton.setOnClickListener(this);
        user = ((MainActivity)getActivity()).getCurrentUser();



     //   MainActivity.closeKeyboard(getActivity().getApplicationContext(), this.container.getWindowToken());
        //view.findViewById(R.id.btnSyncMessage).requestFocus();

    }

    public void postMessageClicked(){

        ServerRequests serverRequest = new ServerRequests(getActivity());

        Message message = new Message( user.username, messageText.getText().toString());

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

        messageText.getText().clear();

        Toast.makeText(getActivity(), "Posting The Message!",
                Toast.LENGTH_LONG).show();
        help_board_messages.add(message.getInit_msg_txt());
        help_board_adapter.notifyDataSetChanged();
        MainActivity.closeKeyboard(this.getActivity(), this.container.getWindowToken());

    }

    public void syncMessageClicked(){

        ServerRequests serverRequest = new ServerRequests(getActivity());
       // Toast.makeText(getActivity(), "Seeeeinking the POSTSSS!!!!! =)",
         //       Toast.LENGTH_LONG).show();


        String message = messageText.getText().toString();

        serverRequest.fetchMessagesAsyncTask(new GetMessageCallback() {
            @Override
            public void done(Message returnedMessages) {
                if (returnedMessages == null) {
                    // showErrorMessage();
                } else {

                    help_board_messages.add(returnedMessages.getInit_msg_txt());


                    ArrayAdapter help_board_adapter;
                    help_board_adapter = new ArrayAdapter(getActivity(), android.R.layout.expandable_list_content, help_board_messages);

                    theListView.setAdapter(help_board_adapter);

                    theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            Toast.makeText(getActivity(), "Messsssge Clicked!",
                                    Toast.LENGTH_LONG).show();

                        }
                    });

                    Toast.makeText(getActivity(), returnedMessages.getInit_msg_txt(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnPostMessage:
                postMessageClicked();
                break;
            case R.id.btnSyncMessage:
                syncMessageClicked();
                break;

        }
    }



}