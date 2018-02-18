package com.safechat.android.safechat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by owotu on 2018-02-17.
 */

public class User implements Parcelable {
    private String name;
    private String email;
    private String uid;
    private String photoUrl;

    public User(){

    }
    public User(String name, String email, String uid, String photoUrl){
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public String getName(){
        return this.name;
    }

    public String getUid(){
        return this.uid;
    }

    public String getEmail(){
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        // TODO Auto-generated method stub
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(uid);
        dest.writeString(photoUrl);
    }

    public User(Parcel in) {
        name = in.readString();
        email = in.readString();
        uid = in.readString();
        photoUrl = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
