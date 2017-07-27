package com.zhiyuan.refreshlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by admin on 2017/7/27.
 */

public class RefreshLayout extends LinearLayout {
    public RefreshLayout(Context context) {
        super(context);
        init();
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addHeadRefresh();
    }
//添加progressbar
    private void addHeadRefresh() {
        setOrientation(VERTICAL);
        ProgressBar progressBar=new ProgressBar(getContext());
        addView(progressBar,0);
    }
//通过scroll隐藏progerssbar
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(0,getChildAt(0).getHeight());
    }
}
