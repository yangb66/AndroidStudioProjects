package com.yangb66.layoutstudy;

import android.app.Activity;
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
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class MainActivity extends AppCompatActivity {
    private ImageButton mImageButton;
    public MediaPlayer mediaPlayer;
    private ToggleButton musicChange;
    private SeekBar voiceChange;
    private Button button;
    private int volume = 0;
    String[] ttf1 = {"sunsatsen.ttf","fanti.ttf"};
    int mp3 = R.raw.yinyu;
    Activity mActivity;
    IUiListener mUiListener;

    private static final int PHOTO_GRAPH = 2;// 拍照
    private static final int PHOTO_ZOOM = 3; // 缩放
    private static final int PHOTO_RESULT = 4;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private int fontType=0, totalFontType=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity=MainActivity.this;
        mUiListener=new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };

        //设置button字体
        button=(Button)findViewById(R.id.setMusic);

        button.setTypeface(Typeface.createFromAsset(this.getAssets(),ttf1[fontType]));
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fontType=(fontType+1)%totalFontType;
                button.setTypeface(Typeface.createFromAsset(MainActivity.this.getAssets(),ttf1[fontType]));
                Intent intent=new Intent(MainActivity.this, Appset.class);
                startActivity(intent);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(MainActivity.this, herosinfo.class);
                startActivity(intent);
                return false;
            }
        });

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
        Intent intent=new Intent(MainActivity.this, MusicServer.class);
        startService(intent);

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



        //Tencent
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
//
//                Tencent tencent = Tencent.createInstance("tencent1106545218", getApplicationContext());
//                Bundle params = new Bundle();
//                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");
//                params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "我是应用程序名称");
//                params.putString("title", "我是标题");
//                params.putString("summary", "我是简介");
//
//                ArrayList<String> images = new ArrayList<String>();
//                images.add("@mipmap/jietu");
//                //params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
//                tencent.shareToQzone(mActivity, params, new IUiListener() {
//                    @Override
//                    public void onComplete(Object o) {
//                        Toast toast = Toast.makeText(MainActivity.this, "complete", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
//                        toast.show();
//
//                    }
//
//                    @Override
//                    public void onError(UiError uiError) {
//                        Toast toast = Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
//                        toast.show();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Toast toast = Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
//                        toast.show();
//                    }
//                });
//            }
//        });
//        button.setOnLongClickListener(new View.OnLongClickListener(){
//            @Override
//            public boolean onLongClick(View v) {
//                makeText(MainActivity.this, "long click", Toast.LENGTH_SHORT).show();
//
//                Tencent tencent = Tencent.createInstance("tencent1106545218", getApplicationContext());
//                Bundle params = new Bundle();
//                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");
//                params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "我是应用程序名称");
//                params.putString("title", "我是标题");
//                params.putString("summary", "我是简介");
//
//                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                //params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, "@mipmap/jietu");
//                tencent.shareToQQ(mActivity, params, new IUiListener(){
//                    @Override
//                    public void onComplete(Object o) {
//                        Toast toast = Toast.makeText(MainActivity.this, "complete", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
//                        toast.show();
//
//                    }
//
//                    @Override
//                    public void onError(UiError uiError) {
//                        Toast toast = Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
//                        toast.show();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Toast toast = Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
//                        toast.show();
//                    }
//                });
//                return false;
//            }
//        });
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

        Tencent.onActivityResultData(requestCode, resultCode, data, mUiListener);
        if(requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE){
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.handleResultData(data, mUiListener);
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
//        mediaPlayer.setLooping(false);
//        mediaPlayer.stop();
        Intent intent=new Intent(MainActivity.this, MusicServer.class);
        stopService(intent);
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

//class MyIUiListener implements IUiListener {
//@Override
//public void onComplete(Object o) {
//// 操作成功
//}
//@Override
//public void onError(UiError uiError) {
//// 分享异常
//}
//@Override
//public void onCancel() {
////// 取消分享
//}
//}