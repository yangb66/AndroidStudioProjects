package com.yangb66.myapplication41;

import java.io.Serializable;

/**
 * Created by 彪 on 2017/10/31.
 */

public class Goods implements Serializable {
    public int number;
    public String name;
    public double price;
    public String infomation;
    public String infoStyle;
    public boolean isInCart;
    public boolean isCollected;
    public Goods(int number1, String name1, double price1, String infomation1, String infoStyle1, boolean isInCart1, boolean isCollected1){
        number = number1;
        name = name1;
        price = price1;
        infomation = infomation1;
        infoStyle = infoStyle1;
        isInCart = isInCart1;
        isCollected = isCollected1;
    }
}
