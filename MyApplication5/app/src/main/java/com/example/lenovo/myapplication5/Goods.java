package com.example.lenovo.myapplication5;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/10/24.
 */

public class Goods implements Serializable {
    public int number;
    public String name;
    public String price;
    public String style;
    public String information;
    public String path;
    public boolean iscart;
    public boolean iscollect;
    public Goods(int Number, String Name, String Price,
                 String Style, String Information, String Path,
                 boolean Iscart,boolean Iscollect)
    {
        number = Number;
        name = Name;
        price = Price;
        style = Style;
        information = Information;
        path = Path;
        iscart = Iscart;
        iscollect = Iscollect;
    }

}
