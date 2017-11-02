package com.example.lenovo.myapplication5;

import android.app.DownloadManager;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class cartactivity extends AppCompatActivity {

    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartactivity);
        myApp = (MyApp) getApplication();

        final List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> temp2 = new LinkedHashMap<>();
        temp2.put("firstletter", "*");
        temp2.put("name", "购物车");
        temp2.put("price", "价格");
        data.add(temp2);
        for (int i = 0; i < myApp.cartgoods.size(); i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("firstletter", myApp.cartgoods.get(i).name.substring(0, 1));
            temp.put("name", myApp.cartgoods.get(i).name);
            temp.put("price", myApp.cartgoods.get(i).price);
            data.add(temp);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.cartlist, new String[]{"firstletter", "name","price"}, new int[]{R.id.firstletter, R.id.name, R.id.price});
        ListView listview = (ListView) findViewById(R.id.recycler_view);
        listview.setAdapter(simpleAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    i = i-1;
                    Intent intent = new Intent(cartactivity.this, goodsactivity.class);
                    Goods temp = myApp.cartgoods.get(i);
                    intent.putExtra("Goods", temp);
                    startActivity(intent);
                }
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(position > 0){
                    final int Position = position-1;
                    Goods temp = myApp.cartgoods.get(Position);
                    final String name = temp.name;
                    AlertDialog.Builder message = new AlertDialog.Builder(cartactivity.this);
                    message.setTitle("移除商品");
                    message.setMessage("从购物车移除"+name+"?");
                    message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
//                        myApp.goods2.remove(position);
                            myApp.cartgoods.remove(Position);
                            data.remove(position);
                            simpleAdapter.notifyDataSetChanged();
//                        View.postInvalidate();
//                        Intent intent = new Intent(cartactivity.this, cartactivity.this);
//                        startActivity(intent);
                            Toast.makeText(cartactivity.this,"移除第"+position+"个商品",Toast.LENGTH_SHORT).show();
                        }
                    });
                    message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    message.create().show();
                }
                return true;
            }
        });
        final FloatingActionButton home = (FloatingActionButton)findViewById(R.id.fab);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(cartactivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
