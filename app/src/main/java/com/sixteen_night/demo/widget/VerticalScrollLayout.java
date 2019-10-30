package com.sixteen_night.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Lee on 2019/10/29 10:54.
 */
public class VerticalScrollLayout extends ViewGroup {

    private Scroller mScroller;
    //设备默认认为滑动的最小距离
    private int mTouchSlop;
    private int mTopBorder;//上边界
    private int mBottomBorder;//下边界
    //按下时坐标
    private float mRawYDown;
    //移动
    private float mRawYMove;
    //上一次
    private float mRawYLastMove;
    //子view数
    private int childCount;

    public VerticalScrollLayout(Context context) {
        this(context, null);
    }

    public VerticalScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //子view
            View view = getChildAt(i);
            view.layout(0, i * view.getMeasuredHeight(), view.getMeasuredWidth(), (i + 1) * view.getMeasuredHeight());
        }
        mTopBorder = getChildAt(0).getTop();
        mBottomBorder = getChildAt(getChildCount() - 1).getBottom();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRawYDown = ev.getRawY();
                mRawYLastMove = mRawYDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mRawYMove = ev.getRawY();
                mRawYLastMove = mRawYMove;
                float distance = Math.abs(mRawYMove - mRawYDown);
                if (distance > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mRawYMove = event.getRawY();
                int distance = (int) (mRawYLastMove - mRawYMove);
                System.out.println("[TopBorder]" + mTopBorder + "-----[BottomBorder]" + mBottomBorder + "[distance]" + distance + "||||" + mRawYMove + "@@@@@" + mRawYLastMove + "[getSY]" + getScrollY());
                if (getScrollY() + distance < mTopBorder) {
                    scrollBy(0, mTopBorder);
                }
                if (getScrollY() + distance > mBottomBorder) {
                    scrollBy(0, mBottomBorder);
                }
                scrollBy(0, distance);
                mRawYLastMove = mRawYMove;
                break;
            case MotionEvent.ACTION_UP:
//                int viewIndex = (getScrollY() + getHeight() / 2) / getHeight();
//                if (viewIndex >= childCount) {
//                    viewIndex--;
//                }
                int dy = - getScrollY();
//                System.out.println("dy++" + dy + "[index]" + viewIndex + "getScrollY" + getScrollY() + "[index*height]" + viewIndex * getHeight() + "[height]" + getHeight());
                mScroller.startScroll(0, getScrollY(), 0, dy);
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
