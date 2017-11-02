package com.yangb66.myapplication41;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;

public class GoodsList extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);

        final GoodsInfo goodsInfo = (GoodsInfo)this.getApplication();
        final String [] Name = new String[goodsInfo.goodsContent.size()];

        for(int i=0; i<goodsInfo.goodsContent.size(); i++) {
            Name[i] = goodsInfo.goodsContent.get(i).name;
        }

        mRecyclerView=(RecyclerView)findViewById(R.id.goodslist);
 /*       mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        CommonAdapter commonAdapter = new CommonAdapter(this,mRecyclerView.getId(),goodsInfo.goodsContent);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(commonAdapter);
        ScaleAnimationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());*/
    }
}
