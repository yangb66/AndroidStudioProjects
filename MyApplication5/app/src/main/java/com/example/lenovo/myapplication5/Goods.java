package com.example.lenovo.myapplication5;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/10/24.
 */

public class Goods implements Serializable {
    public int number;  //商品号码
    public String name; //商品名字
    public String price;//商品价格
    public String style;//信息类型
    public String information;//详细信息
    public int path; //图片路径
    public boolean iscart; //是否进入购物车
    public boolean iscollect; //是否被收藏
    public Goods(int Number, String Name, String Price, String Style, String Information, int Path, boolean Iscart,boolean Iscollect)
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
