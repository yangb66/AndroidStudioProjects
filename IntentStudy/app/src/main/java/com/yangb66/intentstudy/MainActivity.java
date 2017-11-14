package com.yangb66.intentstudy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Goods implements Serializable {
    public int number;
    public String name;
    public double price;
    public String infomation;
    public String infoStyle;
    public boolean isInCart;
    public boolean isCollected;
    public Goods(int number1, String name1, double price1, String infomation1, String infoStyle1, boolean isInCart1, boolean isCollected1){
        number = number1;
        name = name1;
        price = price1;
        infomation = infomation1;
        infoStyle = infoStyle1;
        isInCart = isInCart1;
        isCollected = isCollected1;
    }
}

class Element implements Serializable{
    public Button b;
    public String s;
    public Element(Button b1, String s1) {
        b = b1;
        s = s1;
    }
}

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ListView listView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.mainTextView);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.baidu.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button = (Button)findViewById(R.id.shopListButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("str", "Hello, second activity");
                startActivity(intent);
            }
        });

        listView = (ListView)findViewById(R.id.myListView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getData()));
    }

    List<String> getData(){
        List<String> data = new ArrayList<String>();
        data.add("test 1");
        data.add("test 2");
        data.add("test 3");
        return data;
    }
}
