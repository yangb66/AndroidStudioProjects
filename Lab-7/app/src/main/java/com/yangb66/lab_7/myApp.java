package com.yangb66.lab_7;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2017/12/2.
 */

public class myApp extends Application {
    public List<Activity> queue = new ArrayList<>();

    public void add(Activity a){
        if(queue.contains(a) == false) queue.add(a);
    }



    public void removeAll(){
        for(int i=0; i<queue.size(); i++){
            Activity a = queue.get(i);
            if(a != null) a.finish();
        }
    }
}
