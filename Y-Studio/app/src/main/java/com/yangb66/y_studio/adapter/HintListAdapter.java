package com.yangb66.y_studio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yangb66.y_studio.R;
import com.yangb66.y_studio.model.UrlHint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2017/12/15.
 */

public class HintListAdapter extends BaseAdapter {

    private Context mContext;
    // 存放内容列表
    private List<UrlHint> mList = new ArrayList<>();
    // 删除按钮的点击监听者
    private OnItemChildClickListener mOnItemChildClickListener;

    public HintListAdapter(Context context, List<UrlHint> list){
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        // 获得的View为空则新建
        if(convertView == null){
            viewHolder = new ViewHolder();
            // 获取布局资源
            convertView = LayoutInflater.from(mContext).inflate(R.layout.url_hint_item, null);
            // 获取子view
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.url_hint);
            viewHolder.mButton = (Button) convertView.findViewById(R.id.remove);
            // 设置tag
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        // 设置文本控件内容
        viewHolder.mTextView.setText(mList.get(position).getTitle());
        // 文本控件的点击效果
        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemChildClickListener.onHintClick(position);
            }
        });
        // 核心 设置按钮被点击
        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemChildClickListener.onRemoveClick(position);
            }
        });
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //自定义ViewHolde类,内含两个控件
    private class ViewHolder{
        TextView mTextView;
        Button mButton;
    }

    // 定义删除的函数接口以供外调
    public interface OnItemChildClickListener{
        void onRemoveClick(int position);
        void onHintClick(int position);
    }

    // 设置适配器的接口设置函数
    public void setOnItemRemoveClick(OnItemChildClickListener OnItemChildClickListener){
        this.mOnItemChildClickListener = OnItemChildClickListener;
    }
}
