package com.itheima.googleplay_17.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 10:02
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 11:24:51 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class ChildViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 1.
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 2.
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 3
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();

                int diffX = (int) (moveX - mDownX + .5f);
                int diffY = (int) (moveY - mDownY + .5f);

                if (Math.abs(diffX) > Math.abs(diffY)) {//左右滑动-->自己处理
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
