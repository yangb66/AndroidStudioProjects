package com.example.lenovo.myapplication5;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;

public class goodsactivity extends AppCompatActivity {
    private MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsactivity);
        myApp = (MyApp) getApplication();

        final Goods get_goods = (Goods) getIntent().getSerializableExtra("Goods");
        //设置图片
        ImageView image = (ImageView)findViewById(R.id.imageView);
        int i = get_goods.number;
        if(i == 1) image.setImageResource(R.mipmap.enchatedforest);
        if(i == 2) image.setImageResource(R.mipmap.arla);
        if(i == 3) image.setImageResource(R.mipmap.devondale);
        if(i == 4) image.setImageResource(R.mipmap.kindle);
        if(i == 5) image.setImageResource(R.mipmap.waitrose);
        if(i == 6) image.setImageResource(R.mipmap.mcvitie);
        if(i == 7) image.setImageResource(R.mipmap.ferrero);
        if(i == 8) image.setImageResource(R.mipmap.maltesers);
        if(i == 9) image.setImageResource(R.mipmap.lindt);
        if(i == 10) image.setImageResource(R.mipmap.borggreve);
        //设置名称
        TextView text = (TextView)findViewById(R.id.textView);
        text.setText(get_goods.name);
        //设置星星
        final Button star = (Button)findViewById(R.id.star);
        if(!get_goods.iscart){
            star.setBackgroundResource(R.mipmap.empty_star);
        }
        else{
            star.setBackgroundResource(R.mipmap.full_star);
        }
        star.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_goods.iscart = !get_goods.iscart;
                if(!get_goods.iscart){
                    star.setBackgroundResource(R.mipmap.empty_star);
                }
                else{
                    star.setBackgroundResource(R.mipmap.full_star);
                }
                for(int i=0; i<myApp.shoppinggoods.size(); i++){
                    if(myApp.shoppinggoods.get(i).number == get_goods.number){
                        myApp.shoppinggoods.get(i).iscart = !(myApp.shoppinggoods.get(i).iscart);
                    }
                }
                for(int i=0; i<myApp.cartgoods.size(); i++){
                    if(myApp.cartgoods.get(i).number == get_goods.number){
                        myApp.cartgoods.get(i).iscart = !(myApp.cartgoods.get(i).iscart);
                    }
                }
            }
        });
        //返回键
        Button home = (Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(goodsactivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //价格等信息
        TextView textview = (TextView)findViewById(R.id.price);
        textview.setText(get_goods.price);

        textview = (TextView)findViewById(R.id.style);
        textview.setText(get_goods.style);

        textview = (TextView)findViewById(R.id.information);
        textview.setText(get_goods.information);


        final List<Map<String, Object>> data = new ArrayList<>();
        String[] Word = new String[]{"  一键下单", "  分享商品", "  不感兴趣", "  查看更多商品促销信息"};
        for(int k=0; k<4; k++){
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("word", Word[k]);
            data.add(temp);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.easylist, new String[]{"word"}, new int []{R.id.word});
        ListView listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(simpleAdapter);

        //点击购物车图标
        Button button = (Button)findViewById(R.id.cart);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String temp = get_goods.name;
                Toast.makeText(goodsactivity.this,temp + "被添加到购物车",Toast.LENGTH_SHORT).show();
                get_goods.iscart = true;
                myApp.cartgoods.add(get_goods);
            }
        });


    }
}
