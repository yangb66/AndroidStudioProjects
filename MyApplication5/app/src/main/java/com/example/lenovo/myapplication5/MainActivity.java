package com.example.lenovo.myapplication5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

//    MyApp myApp = (MyApp) getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MyApp myApp = (MyApp) getApplication();
//        myApp = (MyApp) getApplication();
////        final List<Goods> goods = new ArrayList<Goods>(){{
////            add(new Goods(1, "Enchated Forest",5.0,"作者","Johanna Basford","enchatedforest",false,false));
////            add(new Goods(2, "Arla Milk",59.00,"产地","德国","arla",false,false));
////            add(new Goods(3, "Devondale Milk",79.00,"产地","澳大利亚","devondale",false,false));
////            add(new Goods(4, "Kindle Oasis",2399.00,"版本","8GB","kindle",false,false));
////            add(new Goods(5, "waitrose 早餐麦片",179.00,"重量","2Kg","waitrose",false,false));
////            add(new Goods(6, "Mcvitie's 饼干",14.90,"产地","英国","mcvitie",false,false));
////            add(new Goods(7, "Ferrero Rocher",132.59,"重量","300g","ferrero",false,false));
////            add(new Goods(8, "Maltesers",141.43,"重量","118g","maltesers",false,false));
////            add(new Goods(9, "Lindt",139.43,"重量","249g","lindt",false,false));
////            add(new Goods(10, "Borggreve",28.90,"重量","640g","borggreve",false,false));
////        }};
//
        String[] Name = new String[myApp.shoppinggoods.size()];
        for(int i=0; i<myApp.shoppinggoods.size(); i++){
            String x = myApp.shoppinggoods.get(i).name;
            Name[i] = x;
        }
        final List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < myApp.shoppinggoods.size(); i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("firstletter", Name[i].substring(0, 1));
            temp.put("name", Name[i]);
            data.add(temp);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.shoppinglist, new String[]{"firstletter", "name"}, new int[]{R.id.firstletter, R.id.name});
        ListView listview = (ListView) findViewById(R.id.recycler_view);
        listview.setAdapter(simpleAdapter);



////        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
////        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
////        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
////        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
////        mRecyclerView.setAdapter((ArrayAdapter)simpleAdapter);
//


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, goodsactivity.class);
                Goods temp = myApp.shoppinggoods.get(i);
                intent.putExtra("Goods", temp);
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Goods temp = myApp.shoppinggoods.get(position);
                final String name = temp.name;
                final int num = temp.number;
                AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                message.setTitle("删除商品");
                message.setMessage("确定删除"+name+"?");
                message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myApp.shoppinggoods.remove(position);
//                        myApp.goods2.get(num).iscart = false;
                        data.remove(position);
                        simpleAdapter.notifyDataSetChanged();
//                        View.postInvalidate();
//                        Intent intent = new Intent(cartactivity.this, cartactivity.this);
//                        startActivity(intent);
                        int Position = position+1;
                        Toast.makeText(MainActivity.this,"移除第"+Position+"个商品",Toast.LENGTH_SHORT).show();
                    }
                });
                message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                message.create().show();
                return true;
            }
        });
        final FloatingActionButton shoppingcart = (FloatingActionButton)findViewById(R.id.fab);
        shoppingcart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cartactivity.class);
                startActivity(intent);
            }
        });
    }

}
