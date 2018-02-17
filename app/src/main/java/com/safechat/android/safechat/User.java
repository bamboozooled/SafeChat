package com.safechat.android.safechat;

/**
 * Created by owotu on 2018-02-17.
 */

public class User {
    private String name;
    private String email;
    private String uid;
    private String imgUrl;

    public User(){

    }
    public User(String name, String email, String uid, String imgUrl){
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.imgUrl = imgUrl;
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

    public String getImgUrl(){
        return this.imgUrl;
    }
}
