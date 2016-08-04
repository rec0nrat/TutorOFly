package com.tonikamitv.loginregister;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by tylweiss on 11/22/2015.
 */
public class TabFragment1 extends Fragment implements View.OnClickListener{
    User user;
    EditText messageText;
    Button button, syncButton;
    View container;
    ArrayList<Message> help_board_messages;
    ListView theListView;
    ArrayAdapter help_board_adapter; // new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, help_board_messages);
    UserLocalStore userLocalStore;

    @Override
    public void onDestroy() {
        super.onDestroy();
        runnable = null;
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            //
            // Do the stuff
            //

            ServerRequests serverRequest = new ServerRequests(getActivity());


            serverRequest.fetchMessagesAsyncTask(new GetMessageCallback() {
                @Override
                public void done(ArrayList<Message> returnedMessages) {
                    if (returnedMessages == null) {
                        // showErrorMessage();
                    } else {

                        if( help_board_messages.isEmpty()){
                            for(Message message: returnedMessages) {
                                help_board_messages.add(message);
                                help_board_adapter.notifyDataSetChanged();
                            }
                        }else {

                            ArrayList<Message> add_list = new ArrayList<Message>();

                            for (Message temp : returnedMessages) {
                                boolean flag = true;
                                for (Message message : help_board_messages) {
                                    //Log.i("Iterations", " returned:" + temp.toString() + "  actual:" + message.toString());
                                    if (temp.getPost_id() == message.getPost_id()) {
                                        flag = false;
                                        //Log.i("check", "check");
                                    }
                                }
                                if(flag) add_list.add(temp);
                            }

                            for (Message message : add_list) {
                                help_board_messages.add(message);
                                help_board_adapter.notifyDataSetChanged();
                            }
                        }


                    }

                }
            });

            Toast.makeText(getActivity(), "Messages Refreshed!!!",
                    Toast.LENGTH_LONG).show();

            handler.postDelayed(this, 30000);
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.tab_fragment_1 ,null);

        theListView = (ListView) rootView.findViewById(R.id.listView);

        help_board_messages = new ArrayList<Message>();

        //set the adapter and auto refresh if list is changed
        help_board_adapter = new MessageArrayAdapter(this.getActivity(), 0, help_board_messages);
        theListView.setAdapter(help_board_adapter);


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
        // help_board_adapter.notifyDataSetChanged();
        //   MainActivity.closeKeyboard(getActivity().getApplicationContext(), this.container.getWindowToken());
        //view.findViewById(R.id.btnSyncMessage).requestFocus();
        syncMessageClicked();




    }

    public void postMessageClicked(){

        ServerRequests serverRequest = new ServerRequests(getActivity());

       final Message message = new Message(messageText.getText().toString(), user, false);
        //help_board_messages.add(message);

        serverRequest.storeUserMessageInBackground(user, message, new GetMessageCallback() {
            @Override
            public void done(ArrayList<Message> returnedMessages) {
                if (returnedMessages == null) {
                    help_board_messages.add(0,message);
                    help_board_adapter.notifyDataSetChanged();
                } else {
                    //logUserIn(returnedUser);
                }

            }
        });

        messageText.getText().clear();

        Toast.makeText(getActivity(), "Posting The Message!",
                Toast.LENGTH_LONG).show();
        //.add(message);
        //help_board_adapter.notifyDataSetChanged();
        ((MainActivity)getActivity()).hide_keys();
        //MainActivity.closeKeyboard(this.getActivity(), this.container.getWindowToken());

    }



    public void syncMessageClicked(){


                ServerRequests serverRequest = new ServerRequests(getActivity());
                // Toast.makeText(getActivity(), "Seeeeinking the POSTSSS!!!!! =)",
                //       Toast.LENGTH_LONG).show();
                //((MainActivity)getActivity()).hide_keys();


                serverRequest.fetchMessagesAsyncTask(new GetMessageCallback() {
                    @Override
                    public void done(ArrayList<Message> returnedMessages) {

                        if (returnedMessages == null) {
                            // showErrorMessage();
                        } else {


                            if( help_board_messages.isEmpty()){
                                for(Message message: returnedMessages) {
                                    help_board_messages.add(message);
                                    help_board_adapter.notifyDataSetChanged();
                                }
                            }else {

                                ArrayList<Message> add_list = new ArrayList<Message>();

                                for (Message temp : returnedMessages) {
                                    boolean flag = true;
                                    for (Message message : help_board_messages) {
                                        //Log.i("Iterations", " returned:" + temp.toString() + "  actual:" + message.toString());
                                        if (temp.getPost_id() == message.getPost_id()) {
                                            flag = false;
                                            //Log.i("check", "check");
                                        }
                                    }
                                    if(flag) add_list.add(temp);
                                }

                                for (Message message : add_list) {
                                    help_board_messages.add(message);
                                    help_board_adapter.notifyDataSetChanged();
                                }
                            }


                            // ArrayAdapter help_board_adapter;
                            // help_board_adapter = new ArrayAdapter(getActivity(), android.R.layout.expandable_list_content, help_board_messages);

                            //  theListView.setAdapter(help_board_adapter);

                            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                                    TextView msg_txt = (TextView)view.findViewById(R.id.message_content_txt);

                                   // Toast.makeText(getActivity(), "Messsssge Clicked!",
                                     //       Toast.LENGTH_LONG).show();

                                    if(help_board_messages.get(position).getInit_msg_txt().length() > 70) {
                                       // runnable.run();

                                        if (msg_txt.getText().length() <= 70) {
                                            msg_txt.setText(help_board_messages.get(position).getInit_msg_txt());
                                        } else {
                                            msg_txt.setText(help_board_messages.get(position).getTitle());
                                        }
                                        //help_board_adapter.notifyDataSetChanged();
                                    }

                                    ((MainActivity)getActivity()).hide_keys();

                                }
                            });




                        }

                    }
                });



        runnable.run();

        Toast.makeText(getActivity(), "Messages Refreshed!!!",
                        Toast.LENGTH_LONG).show();






       // help_board_adapter.notifyDataSetChanged();



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