package com.yangb66.lab_web;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yangb66.lab_web.model.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class SecondActivity extends AppCompatActivity {

    ListView reposListView;
    ProgressBar progressBar;
    TextView textView;

    String login;
    List<Map<String,Object>> reposListData = new ArrayList<>();
    SimpleAdapter simpleAdapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
            switch(msg.what){
                case 100:
                    simpleAdapter.notifyDataSetChanged();
                    break;
                case 200:
                    textView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    void init(){
        reposListView = (ListView)findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.text1);

        simpleAdapter = new SimpleAdapter(this, reposListData, R.layout.repos_item,
                new String[]{"name", "language", "description"}, new int[]{R.id.name, R.id.language, R.id.description});
        reposListView.setAdapter(simpleAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
        login = getIntent().getStringExtra("login");
//        makeToast("->" + login);
        progressBar.setVisibility(View.VISIBLE);
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.github.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(createOkHttp())
                            .build();
                    GitHubService service = retrofit.create(GitHubService.class);
                    Call<List<Repository>> call1 = service.getRepos(login);
                    call1.enqueue(new Callback<List<Repository>>() {
                        @Override
                        public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                            if(response.isSuccessful()){
                                List<Repository> data = response.body();
                                for(int i=0; i<data.size(); i++){
                                    addListItem(data.get(i));
                                }
                                handler.obtainMessage(100).sendToTarget();
                                if(data.size() == 0){
                                    handler.obtainMessage(200).sendToTarget();
                                }
                            }
                            else{
                                makeToast("响应失败");
                                handler.obtainMessage(101).sendToTarget();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Repository>> call, Throwable t) {
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

    private interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<Repository>> getRepos(@Path("user") String user);
    }

    private static OkHttpClient createOkHttp(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    void makeToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    void addListItem(Repository repository){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", repository.getName());
        map.put("language", repository.getLanguage());
        map.put("description", repository.getDescription());
        reposListData.add(map);
    }
}
