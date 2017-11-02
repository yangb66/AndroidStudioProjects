package com.example.lenovo.myapplication5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by lenovo on 2017/10/26.
 */

public abstract class CommonAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private int mLayoutId;
    List mDatas;
    public CommonAdapter(Context context, int layoutId, List datas){
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }
//    commonAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.shoppinglist, data)
//
//    {
//        @Override
//        public void convert (ViewHolder holder, Map < String, Object > s){
//        TextView name = holder.getView(R.id.name);
//        name.setText(s.get("name").toString());
//        TextView first = holder.getView(R.id.first);
//        first.setText(s.get("firstletter").toString());
//         }
//    }
//    public void onBindViewHolder(final ViewHolder holder, int position){
//        convert(holder, mDatas.get(position));
//        if(mOnItemClickListener != null){
//            holder.itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                    mOnItemClickListener.onClick(holder.getAdapterPosition());
//                }
//            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
//                public boolean onLongClick(View v){
//                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
//                    return false;
//                }
//            });
//        }
//    }

    public int getItamCount(){
        return mDatas.size();
    }

    public interface OnItemClickLisitener{
        void onClick(int position);
        void onLongClick(int position);
    }
//    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
//        this.mOnItemClickListener = onItemClickListener;
//    }
}
