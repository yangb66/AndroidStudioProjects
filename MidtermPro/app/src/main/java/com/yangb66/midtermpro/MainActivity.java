package com.yangb66.midtermpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Persons persons;
    private RecyclerView mRecyclerView;
    void init(){
        persons=new Persons();
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        final CommonAdapter commonAdapter=new CommonAdapter(this,R.id.recyclerView, persons.personsList);
        mRecyclerView.setAdapter(commonAdapter);
    }
}
