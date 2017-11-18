package com.yangb66.layoutstudy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageButton mImageButton;
    private MediaPlayer mediaPlayer;
    private ToggleButton musicChange;
    private static final int PHOTO_GRAPH = 2;// 拍照
    private static final int PHOTO_ZOOM = 3; // 缩放
    private static final int PHOTO_RESULT = 4;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动音乐服务
//        Intent intent = new Intent(MainActivity.this,MusicServer.class);
//        startService(intent);
//        musicServer = new MusicServer();
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.yinyu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        musicChange = (ToggleButton)findViewById(R.id.musicChange);
        musicChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(musicChange.getText().equals("On")){
                    musicChange.setText("Off");
                    mediaPlayer.setLooping(false);
                    mediaPlayer.stop();
                }
                else if(musicChange.getText().equals("Off")){
                    musicChange.setText("On");
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            }
        });

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
        Intent intent = new Intent(MainActivity.this,MusicServer.class);
        stopService(intent);
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
