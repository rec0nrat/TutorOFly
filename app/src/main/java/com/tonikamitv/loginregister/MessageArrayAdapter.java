package com.tonikamitv.loginregister;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rec0nrat on 7/22/2016.
 */
class MessageArrayAdapter extends ArrayAdapter<Message>{


    public MessageArrayAdapter(Context context, int resource ,ArrayList<Message> messages) {
        super(context, resource, messages);
    }

    @Override
    public int getItemViewType(int position) {
        return (getItem(position).is_reply())?1:0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater message_inflator = LayoutInflater.from(getContext());

        int view_type = getItemViewType(position);

        View message_view;

        switch (view_type){
            case 1:
                message_view = message_inflator.inflate(R.layout.help_board_item_reply, parent, false);
                break;
            case 2:
                message_view = message_inflator.inflate(R.layout.help_board_item, parent, false);
                break;
            default:
                message_view = message_inflator.inflate(R.layout.help_board_item, parent, false);
                break;
        }

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

        int likes = message.getLikes_cnt();
        int comments = message.getComment_cnt();

        user_avatar_img.setImageResource(R.drawable.anon);
        user_name_txt.setText(message.getUsername());
        post_date_init_txt.setText((CharSequence)message.getInit_time_stamp().toString());
        message_content_txt.setText(message.getTitle());
        message_hashtag_txt.setText(new String("#ACS"));
        message_last_updated_txt.setText((CharSequence) message.getLast_update().toString());
        like_count_txt.setText( Integer.toString(likes));
        comment_count_txt.setText(Integer.toString(comments));
        like_icon_img.setImageResource(R.drawable.likeit);
        comment_icon_img.setImageResource(R.drawable.comment);

        return message_view;
    }
}
