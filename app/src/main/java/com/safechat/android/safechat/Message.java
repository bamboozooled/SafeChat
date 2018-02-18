package com.safechat.android.safechat;

/**
 * Created by owotu on 2018-02-17.
 */

public class Message {
    private String text;
    private Long time;
    private Media media;
    private String userName;

    public Message() {
    }

    public Message(String text, Long time, Media media, String userName){
        this.text = text;
        this.time = time;
        this.media = media;
        this.userName = userName;
    }

    public String getText(){
        return this.text;
    }

    public Long getTime(){
        return this.time;
    }

    public Media getMedia(){
        return this.media;
    }

    public String getUserName(){
        return this.userName;
    }
}
