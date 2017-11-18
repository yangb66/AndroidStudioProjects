package com.yangb66.layoutstudy;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;
//import android.support.v7.widget.AppCompatTextView;


/**
 * Created by 彪 on 2017/11/18.
 */

public class MyTextView extends AppCompatTextView {
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    /**
     * 初始化字体
     * @param context
     */
    private void init(Context context) {
        //设置字体样式
        setTypeface(FontCustom.setFont(context));
        //setTypeface(new Typeface.createFromAsset(context.getAssets(),"sunsatsen.ttf"))
    }
}