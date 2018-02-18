package com.safechat.android.safechat;

/**
 * Created by owotu on 2018-02-17.
 */

public class Message {
    private String text;
    private Long time;
    private Media media;
    private String userName;
    private boolean checked;

    public Message() {
    }

    public Message(String text, Long time, Media media, String userName, boolean checked){
        this.text = text;
        this.time = time;
        this.media = media;
        this.userName = userName;
        this.checked = checked;
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

    public boolean getChecked(){
        return this.checked;
    }

    public void setChecked(boolean value){
        this.checked = value;
    }
}
