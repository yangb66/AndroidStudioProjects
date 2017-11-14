package com.example.lenovo.myapplication5;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;

public class goodsactivity extends AppCompatActivity {
    private MyApp myApp;
    private static final String STATICACTION = "com.example.ex4.MyStaticFliter";
    private static final String DYNAMICACTION = "com.example.ex4.MyDynamicFliter";
    Receiver dynamicReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsactivity);
        Log.i("goodsactivity","Oncreate");
        myApp = (MyApp) getApplication();
        registerRec();

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        final Goods get_goods = (Goods)bundle.getSerializable("goods");
        //设置图片
        ImageView image = (ImageView)findViewById(R.id.imageView);
        assert get_goods != null;
        image.setImageResource(get_goods.path);
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
//                Intent intent2 = new Intent();
//                intent2.setClass(goodsactivity.this, MainActivity.class);
//                startActivity(intent2);
                    finish();
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
//                myApp.cartgoods.add(get_goods);
                EventBus.getDefault().post(get_goods);
                Intent intentBroadcast = new Intent(DYNAMICACTION);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("goods", get_goods);
                intentBroadcast.putExtras(bundle2);
                sendBroadcast(intentBroadcast);
            }
        });
    }
    void registerRec()
    {
        dynamicReceiver = new Receiver();
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(DYNAMICACTION);   //添加动态广播的Action
        registerReceiver(dynamicReceiver, dynamic_filter);  //注册自定义动态广播信息
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
    }
}
