package com.safechat.android.safechat;

/**
 * Created by owotu on 2018-02-17.
 */

public class Message {
    private String text;
    private Long time;
    private Media media;
    public Message(String text, Long time, Media media){
        this.text = text;
        this.time = time;
        this.media = media;
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
}
