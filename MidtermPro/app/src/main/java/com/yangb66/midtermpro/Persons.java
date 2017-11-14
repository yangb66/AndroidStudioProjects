package com.yangb66.midtermpro;

import android.app.Application;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2017/11/7.
 */

public class Persons extends Application {
    List<Person> personsList;
    public Persons(){
        personsList= new ArrayList<Person>();
        personsList.add(1,new Person("曹操","男","155","220","豫州沛国谯（安徽亳州市亳县）","魏国"));
        personsList.add(2,new Person("刘备","男","161","223","幽州涿郡涿（河北保定市涿州）","蜀国"));
    }
}
