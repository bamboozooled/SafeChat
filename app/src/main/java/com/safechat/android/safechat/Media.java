package com.safechat.android.safechat;

/**
 * Created by owotu on 2018-02-17.
 */

public class Media {
    private String url;
    private String path;
    private String type;
    private boolean safe;

    public Media() {
    }

    public Media(String url, String path, String type, boolean safe){
        this.url = url;
        this.path = path;
        this.type = type;
        this.safe = safe;
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

    public boolean getSafe() {
        return safe;
    }

    public void setSafe(boolean val){
        this.safe = val;
    }
}
