package com.yangb66.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRV;
    private ListView mSV;

    void init(){
        mRV = (RecyclerView)findViewById(R.id.recycler_view);
        mSV = (ListView)findViewById(R.id.shoppinglist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        mRV.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        final String operations[] = {"text1", "text2", "text3"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_main, operations);
        mSV.setAdapter(arrayAdapter);
    }
}
