package com.zhiyuan.refreshlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by admin on 2017/7/27.
 */

public class RefreshLayout extends LinearLayout {

    private boolean inrtercept;
    private double lastY;

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
  //  重写onInterfuptTouchEvent()方法，判断下滑时候拦截事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float y = ev.getRawY();
        switch (ev.getAction()){
            case  MotionEvent.ACTION_DOWN:
                inrtercept =false;
                break;

            case  MotionEvent.ACTION_MOVE:
                if(y- lastY >2){
                    inrtercept =true;
                }else {
                    inrtercept=false;
                }
                break;

            case  MotionEvent.ACTION_UP:
                  inrtercept=false;
                break;
        }
                   lastY = y;
            return  inrtercept;
    }
}
