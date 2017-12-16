package com.example.ysing.retrfitexample;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {
    public interface ApiService{
        @GET("adat/sk/{cityId}.html")
        Call<ResponseBody> getWeather(@Path("cityId") String cityId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //retrofitExample();
        observableExample();
        //httpURL(); //http Example
        //wifiMethod();    // WiFi Example
//        xmlParse();   //XML Example



        //threadModeExample();
        //mapExample();
    }

    private void retrofitExample() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.weather.com.cn/") //设置baseUrl
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> callObject = apiService.getWeather("101010100");

        // 同步请求
        try {
            System.out.println(callObject.execute().body().string());
            System.out.println("这是同步请求后的打印");
        }catch (IOException e) {
            e.printStackTrace();
        }

        //   异步请求
        /*callObject.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                    System.out.println("这是异步请求的结果");
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        Log.i("异步说明", "这是异步请求后的执行语句");*/
    }


    private void observableExample() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        //创建Observer
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i("Obserer对象", "subscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Observer next", "" + integer);

            }

            @Override
            public void onError(Throwable e) {
                Log.i("Error:", e + "");
            }

            @Override
            public void onComplete() {
                Log.i("complete :", "Observer Completed");
            }
        };

        //调用进行连接
        observable.subscribe(new Observer<Integer>() {
            private Disposable disposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.i("Obserer对象", "subscribe");
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("Observer next", "" + integer);
                ++i;
                if(i == 2) {
                    Log.d("Observer", "dispose");
                    disposable.dispose();
                    Log.d("Observer", "disposed");
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.i("Error:", e + "");
            }

            @Override
            public void onComplete() {
                Log.i("complete :", "Observer Completed");
            }
        });
    }

    private void xmlParse() {
        try {
            URL text = new URL("https://stackoverflow.com/feeds/");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(text.openStream(), null);
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if(tagName.compareTo("link") == 0) {
                            System.out.println("rel attributeValue  : " + parser.getAttributeValue(null, "rel"));
                            System.out.println("href attributeValue ： " + parser.getAttributeValue(null, "href"));
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void httpURL() {
        try {
            URL text = new URL("http://sysu.github.io/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) text.openConnection();
            Log.i("HTTP", "respCode = " + httpURLConnection.getResponseCode());
            Log.i("HTTP", "contentType = " + httpURLConnection.getContentType());
            Log.i("HTTP", "content = " + httpURLConnection.getContent());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void mapExample() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("Observable Object:", "emitter ");
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        });

        observable.map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "Map Function : interger " + integer + " to string";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("Consumer Object", s);
            }
        });
    }

    private void wifiMethod() {
            WifiManager  wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            System.out.println("WiFi Status : " + WifiConfiguration.Status.CURRENT);
            System.out.println("BSSID : " + wifiInfo.getBSSID());
            System.out.println("is hidden SSID ： "  + wifiInfo.getHiddenSSID());
            System.out.println("IP Address : " + wifiInfo.getIpAddress());
            System.out.println("Link Speed " + wifiInfo.getLinkSpeed());
            System.out.println("MAC Address : " + wifiInfo.getMacAddress());
            System.out.println("RSSI : " + wifiInfo.getRssi());
            System.out.println("SSID : " + wifiInfo.getSSID());
    }


    private void threadModeExample() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("Observale Object", "Thread is : " + Thread.currentThread().getName());
                Log.d("Observale Object", "emit ");
                e.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() { //说明一下comsumer object
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d("Consumer Object", "Thread is:" + Thread.currentThread().getName());
                Log.d("Consumer Object", "onNext:" + integer);
            }
        };

        observable.subscribeOn(Schedulers.newThread())  //subscribe线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

    }
}
