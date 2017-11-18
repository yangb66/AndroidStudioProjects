package com.yangb66.layoutstudy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import static android.provider.UserDictionary.Words.APP_ID;

public class MainActivity extends AppCompatActivity {
    private ImageButton mImageButton;
    private MediaPlayer mediaPlayer;
    private ToggleButton musicChange;
    private SeekBar voiceChange;
    private int volume = 0;
    String ttf1 ="sunsatsen.ttf";
    int mp3 = R.raw.yinyu;

    private static final int PHOTO_GRAPH = 2;// 拍照
    private static final int PHOTO_ZOOM = 3; // 缩放
    private static final int PHOTO_RESULT = 4;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //设置button字体
        Button button=(Button)findViewById(R.id.setMusic);
        button.setTypeface(Typeface.createFromAsset(this.getAssets(),ttf1));

        //启动音乐服务
        mediaPlayer = MediaPlayer.create(MainActivity.this, mp3);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        musicChange = (ToggleButton)findViewById(R.id.musicChange);
        musicChange.setText("关");
        musicChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(musicChange.getText().equals("关")){
                    musicChange.setText("开");
                    mediaPlayer.setLooping(false);
                    mediaPlayer.stop();
                }
                else if(musicChange.getText().equals("开")){
                    musicChange.setText("关");
                    mediaPlayer = MediaPlayer.create(MainActivity.this, mp3);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            }
        });

        //设置音量
        voiceChange=(SeekBar) findViewById(R.id.voiceChange);
        //音量控制,初始化定义
        final AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //最大音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前音量
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        voiceChange.setMax(maxVolume);
        voiceChange.setProgress(currentVolume);
        voiceChange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,voiceChange.getProgress(),0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //点击图片修改图片
        mImageButton=(ImageButton)findViewById(R.id.imageButton);
        mImageButton.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(MainActivity.this,"long click", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("头像");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                String[] items=new String[]{"相册上传","拍摄上传","下载头像"};
                builder.setItems(items, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            //从相册获取图片
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                            startActivityForResult(intent, PHOTO_ZOOM);
                        }
                        else if(which == 1){
                            //从拍照获取图片
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                                    .getExternalStorageDirectory(),"temp.jpg")));
                            startActivityForResult(intent, PHOTO_GRAPH);
                        }
                        else if(which == 2){
                            Matrix m = mImageButton.getImageMatrix();
                        }
                    }
                });
                builder.create().show();
                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (resultCode == 0) return;

        // 拍照
        if (requestCode == PHOTO_GRAPH) {
            // 设置文件保存路径
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null) return;

        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
        }

        // 处理结果
        if (requestCode == PHOTO_RESULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                mImageButton.setImageBitmap(photo); //把图片显示在ImageView控件上
            }
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.setLooping(false);
        mediaPlayer.stop();
    }

    //回到窗口，播放
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(musicChange.getText().equals("关")){
            mediaPlayer = MediaPlayer.create(this, mp3);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESULT);
    }



}
