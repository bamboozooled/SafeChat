package com.safechat.android.safechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by owotu on 2018-02-17.
 */

public class AllUsersAdapter extends ArrayAdapter<User> {
    private Context mContext;

    public AllUsersAdapter(Context context, List<User> list){
        super(context,0,list);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.userviewlayout,parent,false);
        }


        User adding = getItem(position);

        TextView title = (TextView) listItem.findViewById(R.id.UserName);
        title.setText("UserName: " + adding.getName());

        TextView email = (TextView) listItem.findViewById(R.id.UserEmail);
        email.setText("Email: " + adding.getEmail());

        ImageView imageDone = (ImageView) listItem.findViewById(R.id.UserImage);

        Glide.with(getContext())
                .load(adding.getPhotoUrl())
                .override(250,250)
                .into(imageDone);


        return  listItem;
    }
}
