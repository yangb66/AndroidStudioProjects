package com.yangb66.y_studio.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yangb66.y_studio.R;
import com.yangb66.y_studio.adapter.HintListAdapter;
import com.yangb66.y_studio.db.SQLOH;
import com.yangb66.y_studio.model.UrlHint;

import java.util.ArrayList;
import java.util.List;

import static com.yangb66.y_studio.R.id.url;


public class WebViewActivity extends AppCompatActivity {

    RelativeLayout layout;
    WebView wv;
    Button visit, clearUrl, back, forward, leave, home, setting;//访问按钮，清除输入内容，后退，前进，退出, 主页， 设置
    ProgressBar progressBar;
    EditText urlEdit;
    ListView urlHintList;
    TextView clearList, packUpList; //清除列表和收起列表

    // 提示列表数据和适配器
    List<UrlHint> urlHintListData;
    HintListAdapter hintListAdapter;
    // 主页地址
    String homeUrl = "http://www.hao123.com/";
    // 存储
    private SQLOH sqloh;
    //判断第一次登录
    public static final String shareName = "YStudioShare";
    public static final int shareMode = MODE_PRIVATE;
    private static final String firstLogin = "firstLogin";
    private static final String urlHintSetKey = "urlHintSetKey";

    void init(){
        //获取布局
        layout = (RelativeLayout) findViewById(R.id.webview_layout);
        // WebView
        wv = (WebView) findViewById(R.id.wv);
        //配置webview
        configWebView();
        // 访问按钮
        visit = (Button) findViewById(R.id.visit);
        // 清除输入url
        clearUrl = (Button) findViewById(R.id.clearUrl);
        // 进度条
        progressBar = (ProgressBar) findViewById(R.id.loadding_progress);
        // url 输入框
        urlEdit = (EditText) findViewById(url);
        // url 提示列表
        urlHintList = (ListView) findViewById(R.id.url_hint_list);
        // 清除列表按钮
        clearList = (TextView) findViewById(R.id.clear_list);
        // 收起列表
        packUpList = (TextView) findViewById(R.id.packup);
        // 前进，后退，离开,主页，设置
        forward = (Button) findViewById(R.id.forward);
        back = (Button) findViewById(R.id.back);
        leave = (Button) findViewById(R.id.leave);
        home = (Button) findViewById(R.id.home);
        setting = (Button) findViewById(R.id.setting);

        //设置提示列表适配器
        urlHintListData = new ArrayList<>();
        hintListAdapter = new HintListAdapter(this, urlHintListData);
        urlHintList.setAdapter(hintListAdapter);
        //获取数据库
        sqloh = new SQLOH(this);
        // 判断是否是第一次登录
        SharedPreferences sharedPreferences = getSharedPreferences(shareName, shareMode);
        if(sharedPreferences.getString(firstLogin, firstLogin) == firstLogin){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(firstLogin, "not"+firstLogin);
            editor.commit();
            sqloh.insert(new UrlHint("http://www.baidu.com/", "百度一下"));
        }
        Cursor cursor = sqloh.getFirstCursor();
        while(cursor != null && cursor.moveToNext()){
            addUrlHintItem(new UrlHint(cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
        Log.i("listsize", String.valueOf(urlHintListData.size()));
        // 设置列表不可见
        setListVisibility(View.GONE);
    }

    void configWebView(){
        //声明WebSettings子类
        WebSettings webSettings = wv.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //支持插件
        //webSettings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //优先使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        //不使用缓存:
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //结合使用
//        if (NetStatusUtil.isConnected(getApplicationContext())) {
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        }
//
//        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
//        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
//        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
//
//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

        //设置WebViewClient
        //步骤3. 复写shouldOverrideUrlLoading()方法，
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使得打开网页时不调用系统浏览器， 而是在本WebView中显示
                view.loadUrl(url);
                return true;
            }

            @Override
            public void  onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                progressBar.setVisibility(View.VISIBLE);
                // 设置网址显示
                urlEdit.setText(url);
                // 隐藏提示列表
                setListVisibility(View.GONE);
                //关闭软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive()&&getCurrentFocus()!=null){
                    if (getCurrentFocus().getWindowToken()!=null) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                progressBar.setVisibility(View.GONE);
            }


//            @Override
//            public boolean onLoadResource(WebView view, String url) {
//                //设定加载资源的操作
//            }

//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
//                switch(errorCode){
//                    //该方法传回了错误码，根据错误类型可以进行不同的错误分类处理
//                    case HttpStatus.SC_NOT_FOUND:
//                        view.loadUrl("file:///android_assets/error_handle.html");
//                        break;
//                }
//            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {//处理https请
                handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }
        });

        //设置WebChromeClient类
        wv.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                // 添加url到提示列表
                sqloh.insert(new UrlHint(view.getUrl(), title));
                addUrlHintItem(new UrlHint(view.getUrl(), title));
                Log.i("listsize", String.valueOf(urlHintListData.size()));

            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    progressBar.setProgress(newProgress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        init();
        wv.loadUrl(homeUrl);
        // 点击访问按钮
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置列表可见
                setListVisibility(View.GONE);
                // 访问网址
                wv.loadUrl(urlEdit.getText().toString());
            }
        });
        // 清除网址输入按钮
        clearUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlEdit.setText("");
            }
        });
        // 点击url编辑框,弹出提示列表
        urlEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置列表可见
                if(urlHintListData.size() > 0)
                    setListVisibility(View.VISIBLE);
            }
        });
        // 清除列表按钮
        clearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除存储
                sqloh.clear();
                // 清除列表
                urlHintListData.clear();
                //设置列表不可见
                setListVisibility(View.GONE);
            }
        });
        // 列表项被点击
        // 实现列表适配器的接口
        hintListAdapter.setOnItemRemoveClick(new HintListAdapter.OnItemChildClickListener() {
            @Override
            public void onRemoveClick(int position) {
                //移除存储
                sqloh.delete(urlHintListData.get(position));
                // 移除项
                urlHintListData.remove(position);

                hintListAdapter.notifyDataSetChanged();
                if(urlHintListData.size() == 0){
                    setListVisibility(View.GONE);
                }
            }

            @Override
            public void onHintClick(int position) {
                // 内容复制到编辑框
                urlEdit.setText(urlHintListData.get(position).getUrl());
            }
        });
        // 收起列表被点击
        packUpList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListVisibility(View.GONE);
            }
        });
        // 前进
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnForward();
            }
        });
        // 后退
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnBack();
            }
        });
        // 退出
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 主页
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl(homeUrl);
            }
        });
        // 设置
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //往列表添加元素,没有才加
    void addUrlHintItem(UrlHint urlHint){
        if(!urlHintListData.contains(urlHint)){
            urlHintListData.add(urlHint);
            for(int i=urlHintListData.size()-1; i>=1; i-=1){
                urlHintListData.set(i, urlHintListData.get(i-1));
            }
            urlHintListData.set(0, urlHint);
            hintListAdapter.notifyDataSetChanged();
        }
    }

    // 设置列表可见属性
    void setListVisibility(int vis){
        urlHintList.setVisibility(vis);
        clearList.setVisibility(vis);
        packUpList.setVisibility(vis);

        hintListAdapter.notifyDataSetChanged();
    }

    //重载后退键功能
    @Override
    public void onBackPressed() {
        setOnBack();
    }

    // 后退网页
    void setOnBack(){
        //如果回到首页则退出
        if(wv != null) {
            if(wv.canGoBack()) wv.goBack();
            else finish();
        }
    }

    void setOnForward(){
        if(wv != null) {
            wv.goForward();
        }
    }
}
