package com.yangb66.midtermpro;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 彪 on 2017/11/7.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mSubViews;  //存储list_item的子view
    private View mListView;         //存储list_item
    public ViewHolder(Context context, View listView, ViewGroup parent){
        super(listView);
        mListView=listView;
        mSubViews=new SparseArray<View>();
    }
    // listView需要从上下文获得，根据视图组和Id获取
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId){
        View listView= LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder=new ViewHolder(context, listView, parent);
        return holder;
    }
    //根据viewId获取mListView的子view,如果已经在mViews里面，则直接返回，提高效率，否则，
    //就往mSubViews中添加这个要获取的view
    public <T extends View> T getView(int viewId){
        View view=mSubViews.get(viewId);
        if(view==null){
            view=mListView.findViewById(viewId);
            mSubViews.put(viewId, view);
        }
        return (T)view;
    }
}
