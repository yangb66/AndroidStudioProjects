package com.yangb66.y_studio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.yangb66.y_studio.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RelativeLayout main_layout;
    ListView funcList;

    List<Map<String, Object>> funcListData;
    SimpleAdapter simpleAdapter;

    void init(){
        funcList = (ListView) findViewById(R.id.func_list);
        funcListData = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(this, funcListData, R.layout.func_list_item,
                new String[]{getString(R.string.funcName), getString(R.string.funcDes)}, new int[]{R.id.funcName, R.id.funcDes} );
        funcList.setAdapter(simpleAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获得布局
        main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        init();
        // 添加WebView功能
        addItem(getString(R.string.WebView), getString(R.string.WebViewDes) );
        // 添加动态按钮的功能
        addItem(getString(R.string.DynamicButton), getString(R.string.DynamicButtonDes) );
        // 添加动画测试功能
        addItem(getString(R.string.Animation), getString(R.string.AnimationDes));

        // 设置功能列表的点击事件
        funcList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String funcName = funcListData.get(position).get("funcName").toString();
                if(funcName == getString(R.string.WebView)){
                    Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                    startActivity(intent);
                }else if(funcName == getString(R.string.DynamicButton)){
                    Intent intent = new Intent(MainActivity.this, DynamicButtonActivity.class);
                    startActivity(intent);
                } else if(funcName == getString(R.string.Animation)){
                    Intent intent = new Intent(MainActivity.this, AnimationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    void addItem(String str1, String str2){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(getString(R.string.funcName), str1);
        map.put(getString(R.string.funcDes), str2);
        funcListData.add(map);
        simpleAdapter.notifyDataSetChanged();
    }

    void makeToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
