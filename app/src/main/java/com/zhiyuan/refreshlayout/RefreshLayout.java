package com.zhiyuan.refreshlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.Toast;

import static android.R.attr.y;

/**
 * Created by admin on 2017/7/27.
 */

public class RefreshLayout extends LinearLayout {

    private boolean inrtercept;
    private int lastY;
    private Scroller scroller = new Scroller(getContext());
    private boolean isRefresh;
    private int mY;
    private int yMove;
    private int i;

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
        mY = (int) ev.getRawY();
        switch (ev.getAction()){
            case  MotionEvent.ACTION_DOWN:
                inrtercept =false;
                break;

            case  MotionEvent.ACTION_MOVE:
                if(mY - lastY >2){
                    inrtercept =true;
                }else {
                    inrtercept=false;
                }
                break;

            case  MotionEvent.ACTION_UP:
                  inrtercept=false;
                break;
        }
                   lastY = mY;
            return  inrtercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //动画还没结束的时候，直接消耗掉事件,不处理。
        if (!scroller.isFinished()|| isRefresh) {
            return true;
        }
        mY = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y - lastY;
                if (yMove >= 0) {
                    scrollBy(0, -yMove / 3);   //  /3为了让下拉有感觉
                    i += yMove / 3;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(i>=getChildAt(0).getHeight()){
                    smoothToScroll(i-getChildAt(0).getHeight());
                    i = getChildAt(0).getHeight();
                    isRefresh = true;
                    Toast.makeText(getContext(),"正在刷新中",Toast.LENGTH_SHORT).show();
                }else {
                    endRefresh();
                }
                break;
        }
        lastY = y;
        return true;
    }


    public void endRefresh() {
        isRefresh = false;
        smoothToScroll(i);
        i = 0;
    }
    private void smoothToScroll(int destaY) {
        scroller.startScroll(0, getScrollY(), 0, destaY, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }

}
