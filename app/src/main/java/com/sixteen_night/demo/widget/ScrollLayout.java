package com.sixteen_night.demo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Lee on 2019/10/29 10:38.
 */
public class ScrollLayout extends ViewGroup {
    private Scroller mScroller;
    //当前设备滑动的最小距离
    private int mTouchSlop;

    private int leftBorder;//布局内容的左边界
    private int rightBorder;//布局内容的右边界

    private float mRawXDown;
    private float mRawXMove;
    private float mRawXLastMove;
    private int childCount;

    public ScrollLayout(Context context) {
        super(context);
        initView(context);
    }

    public ScrollLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //为ScrollLayout中的某一个子View给出一个建议的测量大小和测量模式
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.layout(i * view.getMeasuredWidth(), 0, (i + 1) * view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(getChildCount() - 1).getRight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRawXDown = ev.getRawX();
                mRawXLastMove = mRawXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mRawXMove = ev.getRawX();
                mRawXLastMove = mRawXMove;
                float distance = Math.abs(mRawXMove - mRawXDown);
                //左右滑动时，拦截子view的触摸事件
                if (distance > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mRawXMove = event.getRawX();
                int distanceX = (int) (mRawXLastMove - mRawXMove);
                //对边界异常情况的处理
                if (getScrollX() + distanceX < leftBorder) {
                    scrollBy(leftBorder, 0);
                }
                //防止超出子View个数还能继续滑动
                if (getScrollX() > rightBorder) {
                    scrollBy(rightBorder, 0);
                }
                scrollBy(distanceX, 0);
                mRawXLastMove = mRawXMove;
                break;
            case MotionEvent.ACTION_UP:
                //当前所在的page页面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                if (targetIndex == childCount) {
                    targetIndex--;
                }
                int dx = targetIndex * getWidth() - getScrollX();
                Log.i("TAG", "dx: " + dx);
                Log.i("TAG", "getScrollX: " + getScrollX());
                Log.i("TAG", "getWidth: " + getWidth());
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
