package com.yangb66.traversefiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Map<String, Object>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        data = new ArrayList<>();
        for(int i=0; i<20; i++){
            Map<String, Object> tmp = new LinkedHashMap<>();
            tmp.put("name", String.valueOf(i));
            tmp.put("info", String.valueOf(i+1));
            data.add(tmp);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), data, R.layout.itemlayout,
                new String[]{"name", "info"}, new int[]{R.id.name, R.id.info});
        listView.setAdapter(simpleAdapter);

        //遍历本地文件夹
    }
}
