package com.example.lenovo.myapplication5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.graphics.Color.convert;

/**
 * Created by lenovo on 2017/10/26.
 */

//public abstract class CommonAdapter extends RecyclerView.Adapter{
//    private Context mContext;
//    private int mLayoutId;
//    List mDatas;
//    public CommonAdapter(Context context, int layoutId, List datas){
//        mContext = context;
//        mLayoutId = layoutId;
//        mDatas = datas;
//    }
//    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
//        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
//        return viewHolder;
//    }
//
//    public void convert (ViewHolder holder, Map < String, Object > s) {
//        TextView name = holder.getView(R.id.name);
//        name.setText(s.get("name").toString());
//        TextView first = holder.getView(R.id.firstletter);
//        first.setText(s.get("firstletter").toString());
//    }

//    commonAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.shoppinglist, data)
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

//    public int getItamCount(){
//        return mDatas.size();
//    }
//
//    public interface OnItemClickLisitener{
//        void onClick(int position);
//        void onLongClick(int position);
//    }
//    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
//        this.mOnItemClickListener = onItemClickListener;
//    }
//}



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;
import java.util.Map;
import java.util.Objects;


public class CommonAdapter extends RecyclerView.Adapter <CommonAdapter.ViewHolder> {
    private OnItemClickListener monItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        monItemClickListener = onItemClickListener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView img,message;
        public ViewHolder(View view){
            super(view);
            img = (TextView)view.findViewById(R.id.firstletter);
            message = (TextView)view.findViewById(R.id.name);
        }
    }

    private List<Goods> mshopping;
    public  CommonAdapter(List<Goods> shop){
        mshopping = shop;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglist,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Goods shopping = mshopping.get(position);
        holder.img.setText(shopping.name.substring(0, 1));
        holder.message.setText(shopping.name);
        if(monItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    monItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mshopping.size();
    }

}
