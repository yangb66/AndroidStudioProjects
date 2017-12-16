package com.yangb66.lab_web.model;

/**
 * Created by 彪 on 2017/12/12.
 */

public class Account {
    private String login, blog;
    private int id;
    public Account(String login0, int id0, String blog0){
        login = login0;
        id = id0;
        blog = blog0;
    }
    public String getLogin(){
        return login;
    }
    public String getBlog(){
        return blog;
    }
    public int getId(){
        return id;
    }
    public void setId(int id0){
        id = id0;
    }
    public void setBlog(String blog0){
        blog = blog0;
    }
}
