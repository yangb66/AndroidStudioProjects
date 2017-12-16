package com.yangb66.birthday;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.yangb66.birthday.R.id.birthday;
import static com.yangb66.birthday.R.id.gift;
import static com.yangb66.birthday.R.id.phone;

public class MainActivity extends AppCompatActivity {

    private ListView itemList;
    SimpleAdapter simpleAdapter;
    List<Map<String, Object>> data = new ArrayList<>();
    int resource = R.layout.item;
    String[] from = new String[]{"name", "birthday", "gift"};
    int[] to = new int[]{R.id.itemName, R.id.itemBirthday, R.id.itemGift};
    TextView nameText, birthText, giftText;
    private mySQLOH sqloh;
    private int[] sortMode = new int[]{2, 1, 1, 1};    // 第一个参数为2对应按照日期排序的模式，第二个参数为1表示姓名排序是正排序，第三个参数为1表示日期排序是正排序

    SharedPreferences sharedPreferences;
    private static final String SHARE_NAME = "SAVE_SHARE_LAB8";
    private static final int SHARE_MODE = MODE_PRIVATE;

    // 加载数据库
    void initData(){
        readData();

        sqloh = new mySQLOH(getApplication());
        Cursor cursor = sqloh.getFirstCursor();
        while (cursor.moveToNext()){
            addToData(new Member(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        cursor.close();
        listSort();

        nameText = (TextView)findViewById(R.id.name);
        birthText = (TextView)findViewById(R.id.birthday);
        giftText = (TextView)findViewById(R.id.gift);
    }

    void readData(){
        sharedPreferences = getSharedPreferences(SHARE_NAME, SHARE_MODE);
        sortMode[0] = sharedPreferences.getInt("sortMode0", 2);
        sortMode[1] = sharedPreferences.getInt("sortMode1", 1);
        sortMode[2] = sharedPreferences.getInt("sortMode2", 1);
        sortMode[3] = sharedPreferences.getInt("sortMode3", 1);
        Log.i("share get", String.valueOf(sharedPreferences.getInt("sortMode0", 100)));
    }

    // onDestroy保存数据
    void saveData(){
        sharedPreferences= getSharedPreferences(SHARE_NAME, SHARE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("sortMode0", sortMode[0]);
        editor.putInt("sortMode1", sortMode[1]);
        editor.putInt("sortMode2", sortMode[2]);
        editor.putInt("sortMode3", sortMode[3]);
        editor.commit();
        Log.i("share put", String.valueOf(sharedPreferences.getInt("sortMode0", 100)));
    }

    // 初始化列表
    void init(){
        initData();
        itemList = (ListView)findViewById(R.id.itemList);
        simpleAdapter = new SimpleAdapter(getApplicationContext(), data, resource, from, to);
        itemList.setAdapter(simpleAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                setItemListClick(position);
            }
        });
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                return setItemListLongClick(position);
            }
        });
        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortMode[0] == 1) sortMode[1] = -sortMode[1];
                else sortMode[0] = 1;
                sortBy(1); simpleAdapter.notifyDataSetChanged();
            }
        });
        birthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortMode[0] == 2) sortMode[2] = -sortMode[2];
                else sortMode[0] = 2;
                sortBy(2); simpleAdapter.notifyDataSetChanged();
            }
        });
        giftText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortMode[0] == 3) sortMode[3] = -sortMode[3];
                else sortMode[0] = 3;
                sortBy(3); simpleAdapter.notifyDataSetChanged();
            }
        });
    }

    // 设置列表点击
    void setItemListClick(final int position){
        //自定义弹出框
        LayoutInflater factor = LayoutInflater.from(MainActivity.this);
        final View view_in = factor.inflate(R.layout.dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view_in);
        ((TextView)view_in.findViewById(R.id.title)).setText("修改数据");
        ((TextView)view_in.findViewById(R.id.name)).setText(getDataValue(position, 1));
        ((EditText)view_in.findViewById(birthday)).setText(getDataValue(position, 2));
        ((EditText)view_in.findViewById(gift)).setText(getDataValue(position, 3));
        ((TextView)view_in.findViewById(phone)).setText(getPhoneNumber(getDataValue(position, 1)));
        builder.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.get(position).put("birthday", ((EditText)view_in.findViewById(birthday)).getText());
                data.get(position).put("gift", ((EditText)view_in.findViewById(gift)).getText());
                sqloh.update(getDataValue(position, 1), getDataValue(position, 2), getDataValue(position, 3));
                simpleAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeToast("修改已放弃");
            }
        });
        builder.create().show();
    }

    // 设置列表长按
    boolean setItemListLongClick(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("是否删除"+data.get(position).get("name").toString()+"的信息");
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeToast("不删除联系人");
            }
        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqloh.delete(data.get(position).get("name").toString());
                data.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.create().show();
        return true;
    }

    // 设置标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 设置标题栏点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_item:
                // 跳转到增加条目的activity
                int requestCode = 0;
                startActivityForResult(new Intent(this, AddItemActivity.class), requestCode);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //获得返回值,添加元素
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == 1){
            addToData(new Member(intent.getStringExtra("name"), intent.getStringExtra("birthday"), intent.getStringExtra("gift")));
            listSort();
            simpleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    //获取data的内容
    String getDataValue(int position, int id){
        String ret = data.get(position).get("name").toString();
        switch (id){
            case 2:
                ret = data.get(position).get("birthday").toString();
            break;
            case 3:
                ret = data.get(position).get("gift").toString();
            break;
        }
        return ret;
    }

    // 添加元素到列表
    void addToData(Member member){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", member.name);
        map.put("birthday", member.birthday);
        map.put("gift", member.gift);
        data.add(map);
    }

    // Toast信息
    void makeToast(String msg){
        Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
    }

    //获取电话号码
    String getPhoneNumber(String name){
        String phoneNumber = "无";
        String[] projection = new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection = ContactsContract.Contacts.DISPLAY_NAME + "= ?";
        String[] selectionArgs = new String[]{ name };
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection, selectionArgs, null);
        if(cursor.moveToFirst()){
            int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNumber = cursor.getString(phoneNumberIndex);
        }
        return phoneNumber;
    }

    // 列表排序
    void listSort(){
        sortBy(sortMode[0]);      // 2 表示按照日期排序
    }

    // 列表按生日排序,归并排序
    void sortByDate2(){
        temData.clear();
        for(int i=0; i<data.size(); i++) temData.add(data.get(i));
        merge_sort(0, data.size());
    }
    //归并排序
    List<Map<String, Object>> temData = new ArrayList<>();
    void merge_sort(int x, int y){
        if(y - x > 1){
            int m = x + (y - x)/2;
            int p = x, q = m, i = x;
            merge_sort(x, m);
            merge_sort(m, y);
            while(p < m || q < y){
                if(q >= y && (p < m && cmpDate(p, q)<=0)) temData.set(i++, data.get(p++));
                else temData.set(i++, data.get(q++));
            }
            for(i = x; i < y; i ++){
                data.set(i, temData.get(i));
            }
        }
    }

    // 冒泡排序
    void sortBy(int type){
        for(int i=0; i<data.size(); i++){
            int k = i;
            for(int j=i+1; j<data.size(); j++) {
                int flag = 0;
                if(type == 1) flag = sortMode[1]*cmpName(k, j);
                if(type == 2) flag = sortMode[2]*cmpDate(k, j);
                if(type == 3) flag = sortMode[3]*cmpGift(k, j);
                if(flag < 0) {
                    k = j;
                }
            }
            if(k != i){
                Map<String, Object> map1 = data.get(i);
                Map<String, Object> map2 = data.get(k);
                data.set(i, map2);
                data.set(k, map1);
            }
        }
    }

    // 比较名字函数 <
    int cmpName(int i, int j){
        if(i >= data.size() || j >= data.size()) return -1;
        String di = data.get(i).get("name").toString();
        String dj = data.get(j).get("name").toString();
        return -di.compareTo(dj);
    }

    // 比较生日函数 <
    int cmpDate(int i, int j){
        if(i >= data.size() || j >= data.size()) return -1;
        String di = data.get(i).get("birthday").toString();
        String dj = data.get(j).get("birthday").toString();
        if(di.length() != 5 && dj.length() == 5) return -1;
        if(dj.length() != 5 && di.length() == 5) return 1;
        if(di.length() != 5 && dj.length() != 5) return 0;
        int t1 = ((di.charAt(0)-'0')*10+(di.charAt(1)-'0'))*31+(di.charAt(3)-'0')*10+(di.charAt(4)-'0');
        int t2 = ((dj.charAt(0)-'0')*10+(dj.charAt(1)-'0'))*31+(dj.charAt(3)-'0')*10+(dj.charAt(4)-'0');
        return -(t1 - t2);
    }

    // 比较礼物函数 <
    int cmpGift(int i, int j){
        if(i >= data.size() || j >= data.size()) return -1;
        String di = data.get(i).get("gift").toString();
        String dj = data.get(j).get("gift").toString();
        return ( di.length() - dj.length() );
    }
}
