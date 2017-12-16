package com.yangb66.y_studio.model;

/**
 * Created by 彪 on 2017/12/17.
 */

public class UrlHint {
    private String url, title; // url地址，title标题
    public UrlHint(String url0, String title0){
        setUrl(url0);
        setTitle(title0);
    }
    public void setUrl(String url0){
        url = url0;
    }
    public void setTitle(String title0){
        title = title0;
    }
    public String getUrl(){
        return url;
    }
    public String getTitle(){
        return title;
    }

}
