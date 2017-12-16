package com.yangb66.lab_web;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yangb66.lab_web.adapter.MyAdapter;
import com.yangb66.lab_web.db.SQLOH;
import com.yangb66.lab_web.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class MainActivity extends AppCompatActivity {
    Button clearButton, fetchButton;
    EditText accountEdit;
    RecyclerView accountRecyclerView;
    ProgressBar progressBar;

    List<Account> accountList;
    ScaleInAnimationAdapter animationAdapter;

    SQLOH sqloh;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
            switch (msg.what){
                case 100:           // 网络请求成功
                    animationAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    void init(){
        clearButton = (Button)findViewById(R.id.button);
        fetchButton = (Button)findViewById(R.id.button2);
        accountEdit = (EditText)findViewById(R.id.editText);
        accountRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        initData();
    }

    void initData(){
        sqloh = new SQLOH(this);
        accountList = new ArrayList<>();
        Cursor cursor = sqloh.getFirstCursor();
        while(cursor.moveToNext()){
            Account account = new Account(cursor.getString(1), cursor.getInt(2), cursor.getString(3));
            accountList.add(account);
        }
        cursor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        // 设置布局管理器
        accountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 构造适配器
        MyAdapter myAdapter = new MyAdapter(this, accountList);
        // 升级为动画适配器
        animationAdapter = new ScaleInAnimationAdapter(myAdapter);
        animationAdapter.setDuration(1000);
        accountRecyclerView.setAdapter(animationAdapter);
        accountRecyclerView.setItemAnimator(new ScaleInBottomAnimator());
        // 设置清楚列表
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountList.clear();
                animationAdapter.notifyDataSetChanged();
            }
        });
        // 设置列表点击事件
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //打开相应activity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("login", accountList.get(position).getLogin());
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(int position) {
                //删除数据库数据
                sqloh.delete(accountList.get(position).getLogin());
                //删除列表元素
                accountList.remove(position);
                animationAdapter.notifyDataSetChanged();
            }
        });
        // 设置连网获取账号
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                requestApi(accountEdit.getText().toString());
            }
        });

    }

    void makeToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void requestApi(final String str){
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.github.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(createOkHttp())
                            .build();
                    //实现接口
                    GitHubService service = retrofit.create(GitHubService.class);

                    Call<Account> call1 = service.getAccounts(str);
                    call1.enqueue(new Callback<Account>() {
                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            if(response.isSuccessful()){
                                Account account = response.body();
                                //将账号加入列表头部位置
                                accountList.add(account);
                                for(int i=accountList.size()-1; i>0; i--){
                                    accountList.set(i, accountList.get(i-1));
                                }
                                accountList.set(0, account);
                                //修改UI
                                handler.obtainMessage(100).sendToTarget();
                                //添加到数据库，没有才增加
                                if(!sqloh.query(account.getLogin())){
                                    sqloh.insert(account.getLogin(), account.getId(), account.getBlog());
                                }
                                else{
                                    sqloh.update(account.getLogin(), account.getId(), account.getBlog());
                                }
                            }
                            else{
                                makeToast("请确认你搜索的用户存在");
                                handler.obtainMessage(101).sendToTarget();
                            }
                        }
                        @Override
                        public void onFailure(Call<Account> call, Throwable t) {
                            makeToast("搜索失败");
                            handler.obtainMessage(102).sendToTarget();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(103).sendToTarget();
                }
            }
        };
        thread.start();
    }

    public void requestApi2(final String str){
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.github.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(createOkHttp())
                            .build();
                    //实现接口
                    GitHubService2 service = retrofit.create(GitHubService2.class);

                    //RxJava
                    service.getAccountsByObservable(str)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Account>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Account value) {
                                    accountList.add(value);
                                    handler.obtainMessage(100).sendToTarget();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    System.out.print("传输失败");
                                }

                                @Override
                                public void onComplete() {
                                    System.out.print("传输完成");
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(103).sendToTarget();
                }
            }
        };
        thread.start();
    }

    private interface GitHubService {
        @GET("users/{user}")
        Call<Account> getAccounts(@Path("user") String user);
    }

    private interface GitHubService2 {
        @GET("users/{user}")
        Observable<Account> getAccountsByObservable(@Path("user") String user);
    }

    private static OkHttpClient createOkHttp(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    void addAccount(Account account){
        //将账号加入列表头部位置
        accountList.add(account);
        for(int i=accountList.size()-1; i>0; i=i-1){
            accountList.set(i, accountList.get(i-1));
        }
        accountList.set(0, account);
        //添加到数据库，没有才增加
        if(!sqloh.query(account.getLogin())){
            sqloh.insert(account.getLogin(), account.getId(), account.getBlog());
        }
        else{
            sqloh.update(account.getLogin(), account.getId(), account.getBlog());
        }
    }

    void removeAccount(int position){
        //删除数据库数据
        sqloh.delete(accountList.get(position).getLogin());
        //删除列表元素
        accountList.remove(position);
    }

}
