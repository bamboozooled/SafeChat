package com.safechat.android.safechat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by owotu on 2018-02-17.
 */

public class ChatMaster extends RecyclerView.Adapter<ChatMaster.ViewHolder> {
    ArrayList<Message> messageStorage;

    public ChatMaster(ArrayList<Message> messageStorage){
        this.messageStorage = messageStorage;
    }
    @Override
    public ChatMaster.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelayout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatMaster.ViewHolder holder, int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return messageStorage.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;
        ImageView image;
        TextView timestamp;
        public ViewHolder(final View v) {
            super(v);
            name = (TextView) itemView.findViewById(R.id.usertextname);
            message = (TextView) itemView.findViewById(R.id.usertextmessage);
            image = (ImageView) itemView.findViewById(R.id.messageimage);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
        }

        public void bindView(){
            Message m = messageStorage.get(getAdapterPosition());
            name.setText(m.getUserName());
            message.setText(m.getText());
            if(m.getMedia() != null) {
                if(m.getMedia().getSafe()) {
                    Glide.with(itemView.getContext())
                            .load(m.getMedia().getUrl())
                            .into(image);
                }
                image.setVisibility(View.VISIBLE);
            } else {
                image.setVisibility(View.GONE);
            }
            timestamp.setText(getTime(m.getTime()));
        }

        public String getTime(Long milli) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(milli);

            String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
            String minute = String.valueOf(c.get(Calendar.MINUTE));
            if(minute.length() == 1) {
                minute = "0" + minute;
            }
            if(hour.length() == 1) {
                hour = "0" + hour;
            }
            return hour + ":" + minute;
        }
    }

}
