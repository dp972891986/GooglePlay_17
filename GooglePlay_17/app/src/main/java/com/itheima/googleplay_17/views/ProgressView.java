package com.itheima.googleplay_17.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay_17.R;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 17:53
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 14:09:20 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class ProgressView extends LinearLayout {

    private ImageView mIvIcon;
    private TextView  mTvNote;

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
        //重绘
        invalidate();
    }

    /**
     * 设置是否允许进度
     */
    public void setIsProgressEnable(boolean isProgressEnable) {
        this.isProgressEnable = isProgressEnable;
    }

    /**
     * 设置图标
     */
    public void setIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    /**
     * 设置文本
     */
    public void setNote(String note) {
        mTvNote.setText(note);
    }

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //挂载定义的布局
        View view = ProgressView.inflate(context, R.layout.infalte_progressview, this);

        //找到孩子对
        mIvIcon = (ImageView) view.findViewById(R.id.progressview_iv_icon);
        mTvNote = (TextView) view.findViewById(R.id.progressview_tv_note);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);//绘制背景
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);//绘制图标和文字
        if (isProgressEnable) {
            //绘制圆弧
            RectF oval = new RectF(mIvIcon.getLeft(), mIvIcon.getTop(), mIvIcon.getRight(), mIvIcon.getBottom());
            float startAngle = -90;
            float sweepAngle = mProgress * 1.0f / mMax * 360;
            boolean useCenter = false;
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            //消除锯齿
            paint.setAntiAlias(true);
            canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
        }
    }
}
