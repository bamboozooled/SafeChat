package com.safechat.android.safechat;

/**
 * Created by owotu on 2018-02-17.
 */

public class Media {
    private String url;
    private String path;
    private String type;
    private boolean isSafe;

    public Media() {
    }

    public Media(String url, String path, String type, boolean isSafe){
        this.url = url;
        this.path = path;
        this.type = type;
        this.isSafe = isSafe;
    }

    public String getUrl(){
        return this.url;
    }

    public String getPath(){
        return this.path;
    }

    public String getType(){
        return this.type;
    }

    public boolean isSafe(){
        return this.isSafe;
    }

    public void setSafe(boolean value){
        this.isSafe = value;
    }
}
