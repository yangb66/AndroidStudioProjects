package com.example.lenovo.myapplication5;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {
    public List<Goods> shoppinggoods;
    public List<Goods> cartgoods;

    @Override
    public void onCreate() {
        super.onCreate();
        shoppinggoods = new ArrayList<Goods>(){{
            add(new Goods(1, "Enchated Forest","¥ 5.00","作者","Johanna Basford","enchatedforest",false,false));
            add(new Goods(2, "Arla Milk","¥ 59.00","产地","德国","arla",false,false));
            add(new Goods(3, "Devondale Milk","¥ 79.00","产地","澳大利亚","devondale",false,false));
            add(new Goods(4, "Kindle Oasis","¥ 2399.00","版本","8GB","kindle",false,false));
            add(new Goods(5, "waitrose 早餐麦片","¥ 179.00","重量","2Kg","waitrose",false,false));
            add(new Goods(6, "Mcvitie's 饼干","¥ 14.90","产地","英国","mcvitie",false,false));
            add(new Goods(7, "Ferrero Rocher","¥ 132.59","重量","300g","ferrero",false,false));
            add(new Goods(8, "Maltesers","¥ 141.43","重量","118g","maltesers",false,false));
            add(new Goods(9, "Lindt","¥ 139.43","重量","249g","lindt",false,false));
            add(new Goods(10, "Borggreve","¥ 28.90","重量","640g","borggreve",false,false));
        }};
        cartgoods = new ArrayList<Goods>(){};
    }
}
