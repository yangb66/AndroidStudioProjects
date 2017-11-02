package com.yangb66.myapplication41;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2017/10/31.
 */

public class GoodsInfo extends Application{
    public List<Goods> goodsCart;
    public List<Goods> goodsContent;
    public GoodsInfo(){
        goodsCart = new ArrayList<Goods>(){};
        goodsContent = new ArrayList<Goods>(){{
            add(new Goods(1,"Enchated Forest", 5, "Johanna Basford", "作者", false, false));
            add(new Goods(2,"Arla Milk", 59, "德国", "产地", false, false));
            add(new Goods(3,"Devondale Milk", 79, "澳大利亚", "产地", false, false));
            add(new Goods(4,"Kindle Oasis", 2399, "8GB", "版本", false, false));
            add(new Goods(5,"waitrose 早餐麦片", 179, "2Kg", "重量", false, false));
            add(new Goods(6,"Mcvitie's 饼干", 14.9, "英国", "产地", false, false));
            add(new Goods(7,"Ferrero Rocher", 132.59, "300g", "重量", false, false));
            add(new Goods(8,"Maltesers", 141.43, "118g", "重量", false, false));
            add(new Goods(1,"Lindt", 139.43, "249g", "重量", false, false));
            add(new Goods(1,"orggreve", 28.9, "640g", "重量", false, false));
        }};
    }
}
