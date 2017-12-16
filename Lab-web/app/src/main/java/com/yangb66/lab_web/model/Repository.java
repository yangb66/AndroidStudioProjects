package com.yangb66.lab_web.model;

/**
 * Created by 彪 on 2017/12/13.
 */

public class Repository {
    private String name, language, description;
    public Repository(String name0, String language0, String description0){
        name = name0;
        language = language0;
        description = description0;
    }
    public String getName(){
        return name;
    }
    public String getLanguage(){
        return language;
    }
    public String getDescription(){
        return description;
    }
    public void setName(String name0){
        name = name0;
    }
    public void setLanguage(String language0){
        language = language0;
    }
    public void setDescription(String description0){
        description = description0;
    }
}
