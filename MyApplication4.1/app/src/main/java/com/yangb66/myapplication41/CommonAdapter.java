package com.yangb66.myapplication41;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

import java.util.List;

/**
 * Created by 彪 on 2017/10/31.
 */

public abstract class CommonAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private int mLayoutID;
    List mDatas;
    OnItemClickLisitener mOnItemClickListener;

    public CommonAdapter(Context context, int layoutID, List datas){
        mContext=context;
        mLayoutID=layoutID;
        mDatas=datas;
    }
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutID);
        return viewHolder;
    }
    public void onBindViewHolder(final ViewHolder holder, int position){
        //convert(holder, mDatas.get(position));
        if(mOnItemClickListener != null){
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
    public int getItemCount(){return mDatas.size();}
    public interface OnItemClickLisitener{
        void onClick(int position);
        void onLongClick(int position);
    }
    public void setOnItemClickListener(OnItemClickLisitener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
}
