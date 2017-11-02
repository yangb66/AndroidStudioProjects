package com.yangb66.myapplication41;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 彪 on 2017/10/31.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews; //存储list_item里面的子view
    private View mConvertView;//存储list_item
    public ViewHolder(Context context, View itemView, ViewGroup parent){
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }
    public static ViewHolder get(Context context, ViewGroup parent, int layoutID){
        View itemView = LayoutInflater.from(context).inflate(layoutID,parent,false);
        ViewHolder holder = new ViewHolder(context, itemView, parent);
        return holder;
    }
    public <T extends View> T getView(int viewID){
        View view = mViews.get(viewID);
        if(view == null) {
            view = mConvertView.findViewById(viewID);
            mViews.put(viewID, view);
        }
        return (T) view;
    }
}
