package com.safechat.android.safechat;

/**
 * Created by owotu on 2018-02-17.
 */

public class Media {
    private String url;
    private String path;
    private String type;

    public Media(String url, String path, String type){
        this.url = url;
        this.path = path;
        this.type = type;
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
}
