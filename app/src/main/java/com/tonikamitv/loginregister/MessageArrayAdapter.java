package com.tonikamitv.loginregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by rec0nrat on 7/22/2016.
 */
class MessageArrayAdapter extends ArrayAdapter<Message>{


    public MessageArrayAdapter(Context context, ArrayList<Message> messages) {
        super(context, R.layout.help_board_item, messages);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater message_inflator = LayoutInflater.from(getContext());
        View message_view = message_inflator.inflate(R.layout.help_board_item, parent, false);


        ImageView user_avatar_img = (ImageView )message_view.findViewById(R.id.user_avatar_img);
        TextView user_name_txt = (TextView)message_view.findViewById(R.id.user_name_txt);
        TextView post_date_init_txt = (TextView)message_view.findViewById(R.id.post_date_init_txt);
        TextView message_content_txt = (TextView)message_view.findViewById(R.id.message_content_txt);
        TextView message_hashtag_txt = (TextView)message_view.findViewById(R.id.message_hashtag_txt);
        TextView message_last_updated_txt = (TextView)message_view.findViewById(R.id.message_last_updated_txt);
        TextView like_count_txt = (TextView)message_view.findViewById(R.id.like_count_txt);
        TextView comment_count_txt = (TextView)message_view.findViewById(R.id.comment_count_txt);
        ImageView like_icon_img = (ImageView)message_view.findViewById(R.id.like_icon_img);
        ImageView comment_icon_img = (ImageView)message_view.findViewById(R.id.comment_icon_img);

        Message message = getItem(position);

        user_avatar_img.setImageResource(R.drawable.anon);
        user_name_txt.setText(message.getUsername());
        post_date_init_txt.setText((CharSequence)message.getInit_time_stamp().toString());
        message_content_txt.setText(message.getInit_msg_txt());
        message_hashtag_txt.setText(new String("#ACS"));
        message_last_updated_txt.setText((CharSequence) message.getLast_update().toString());
        like_count_txt.setText( "0");
        comment_count_txt.setText("0");
        like_icon_img.setImageResource(R.drawable.like);
        comment_icon_img.setImageResource(R.drawable.comment);

        return message_view;
    }
}
