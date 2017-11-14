package com.yangb66.midtermpro;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.List;
import java.util.Map;

/**
 * Created by 彪 on 2017/11/7.
 */

public class CommonAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private int mLayoutId;
    private List mData;


    public CommonAdapter(Context context, int layoutId, List data){
        mContext=context;
        mLayoutId=layoutId;
        mData=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder=ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    //添加接口OnItemClickListener和函数，是的commonAdapter具有OnItemClickListener方法
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
    //设置接口的监听对象，实例化
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }


    //获取List的大小
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
