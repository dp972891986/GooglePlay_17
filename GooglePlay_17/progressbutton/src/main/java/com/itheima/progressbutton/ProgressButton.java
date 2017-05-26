package com.itheima.progressbutton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 16:32
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 16:47:17 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class ProgressButton extends Button {
    private long mMax = 100;
    private long mProgress;
    private boolean isProgressEnable = true;

    /**
     * 设置进度的最大值
     */
    public void setMax(long max) {
        mMax = max;
    }

    /**
     * 设置进度的当前值
     */
    public void setProgress(long progress) {
        mProgress = progress;
        //重绘操作
        invalidate();
    }

    /**
     * 设置是否允许progress
     */
    public void setIsProgressEnable(boolean isProgressEnable) {
        this.isProgressEnable = isProgressEnable;
    }

    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isProgressEnable) {
            //        canvas.drawText("haha", 30, 30, getPaint());
            Drawable drawable = new ColorDrawable(Color.BLUE);
            int left = 0;
            int top = 0;
            int right = (int) (mProgress * 1.0f / mMax * getMeasuredWidth() + .5f);
            int bottom = getBottom();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);

        }

        super.onDraw(canvas);
    }

}
