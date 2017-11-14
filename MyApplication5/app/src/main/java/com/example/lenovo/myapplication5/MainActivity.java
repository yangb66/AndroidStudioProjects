package com.example.lenovo.myapplication5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

import static android.R.attr.data;
import static com.example.lenovo.myapplication5.R.id.listview;


public class MainActivity extends AppCompatActivity {
    private static final String STATICACTION = "com.example.ex4.MyStaticFliter";
    private static final String DYNAMICACTION = "com.example.ex4.MyDynamicFliter";
    MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity","Oncreate");
        myApp = (MyApp) getApplication();

//        EventBus.getDefault().register(this);
        //静态广播
        Random random = new Random();
        if(myApp.shoppinggoods.size() != 0){
            int random2 = random.nextInt(myApp.shoppinggoods.size());
            Goods tempgoods = myApp.shoppinggoods.get(random2);
            Intent intentBroadcast = new Intent(STATICACTION);
            Bundle bundle = new Bundle();
            bundle.putSerializable("goods", tempgoods);
            intentBroadcast.putExtras(bundle);
            sendBroadcast(intentBroadcast);
        }

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
//        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.shoppinglist, new String[]{"firstletter", "name"}, new int[]{R.id.firstletter, R.id.name});
//        ListView listview = (ListView) findViewById(R.id.recycler_view);
//        listview.setAdapter(simpleAdapter);

        final CommonAdapter commonAdapter = new CommonAdapter(myApp.shoppinggoods);

        RecyclerView listview = (RecyclerView)findViewById(R.id.recycler_view);
        listview.setLayoutManager(new LinearLayoutManager(this));
////        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
////        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
//        listview.setAdapter(commonAdapter);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(commonAdapter);
        animationAdapter.setDuration(1000);
        listview.setAdapter(animationAdapter);
        listview.setItemAnimator(new OvershootInLeftAnimator());

        commonAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int i) {
                Goods temp = myApp.shoppinggoods.get(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", temp);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, goodsactivity.class);
                intent.putExtras(bundle);
//                intent.putExtra("goods", temp);
                startActivity(intent);
            }

            @Override
            public void onLongClick(final int position) {
                Goods temp = myApp.shoppinggoods.get(position);
                final String name = temp.name;
                final int num = temp.number;
                AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                message.setTitle("删除商品");
                message.setMessage("确定删除"+name+"?");
                message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myApp.shoppinggoods.remove(position);
                        data.remove(position);
                        commonAdapter.notifyDataSetChanged();
                        int Position = position+1;
                        Toast.makeText(MainActivity.this,"移除第"+Position+"个商品",Toast.LENGTH_SHORT).show();
                    }
                });
                message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                message.create().show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
