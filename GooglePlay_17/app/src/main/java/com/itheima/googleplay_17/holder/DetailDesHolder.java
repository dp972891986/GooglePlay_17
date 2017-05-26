package com.itheima.googleplay_17.holder;

import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.utils.UIUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 14:16
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 16:23:26 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class DetailDesHolder extends BaseHolder<ItemInfoBean> implements View.OnClickListener {


    @Bind(R.id.app_detail_des_tv_des)
    TextView mAppDetailDesTvDes;

    @Bind(R.id.app_detail_des_tv_author)
    TextView mAppDetailDesTvAuthor;


    @Bind(R.id.app_detail_des_iv_arrow)
    ImageView mAppDetailDesIvArrow;

    private boolean isOpen = true;
    private int          mAppDetailDesMeasuredHeight;
    private ItemInfoBean mItemInfoBean;

    @Override
    public View initHolderView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_detail_des, null);
        ButterKnife.bind(this, rootView);
        rootView.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean itemInfoBean) {
        //保存数据为成员变量
        mItemInfoBean = itemInfoBean;


        mAppDetailDesTvAuthor.setText(itemInfoBean.author);
        mAppDetailDesTvDes.setText(itemInfoBean.des);

        //监听mAppDetailDesTvDes布局完成
        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mAppDetailDesMeasuredHeight = mAppDetailDesTvDes.getMeasuredHeight();
                mAppDetailDesTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //默认折叠
                toggleAnimation(false);
            }
        });

    }

    @Override
    public void onClick(View v) {
        toggleAnimation(true);
    }

    /**
     * @param isAnimation 是否带上动画
     */
    private void toggleAnimation(boolean isAnimation) {
        if (isOpen) {//折叠
            //折叠:mAppDetailDesTvDes高度变化  从 应有的高度-->7行的高度
            //            mAppDetailDesTvDes.measure(0, 0);
            //            int measuredHeight = mAppDetailDesTvDes.getMeasuredHeight();

            Toast.makeText(UIUtils.getContext(), mAppDetailDesMeasuredHeight + "", Toast.LENGTH_SHORT).show();
            int start = mAppDetailDesMeasuredHeight;
            int end = getLineHeight(7, mItemInfoBean.des);//7行高度

            //            mAppDetailDesTvDes.setHeight();
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                mAppDetailDesTvDes.setHeight(end);
            }
        } else {//展开

            int end = mAppDetailDesMeasuredHeight;
            int start = getLineHeight(7, mItemInfoBean.des);//7行高度

            if (isAnimation) {
                doAnimation(start, end);
            } else {
                mAppDetailDesTvDes.setHeight(end);
            }
        }
        isOpen = !isOpen;
    }

    private void doAnimation(int start, int end) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mAppDetailDesTvDes, "height", start, end);
        animator.start();
        //监听动画的执行过程
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {//动画的开始

            }

            @Override
            public void onAnimationEnd(Animator animator) {//动画的结束
                //找到scrollView,然后进行滚动
                ViewParent parent = mAppDetailDesTvDes.getParent();//第一级父容器
                while (true) {
                    parent = parent.getParent();//第二级父容器
                    if (parent instanceof ScrollView) {//找到了
                        //开始滚动效果
                        ((ScrollView) parent).fullScroll(View.FOCUS_DOWN);
                        break;
                    }
                    if (parent == null) {//找到最上面了
                        break;
                    }
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {//动画的去爱惜

            }

            @Override
            public void onAnimationRepeat(Animator animator) {//动画的重复

            }
        });

        if (isOpen) {
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 180, 0).start();
        } else {
            ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 0, 180).start();
        }
    }

    /**
     * 返回具体的textview应有的高度
     *
     * @param line    textView行高
     * @param content textview里面展示的内容
     * @return
     */
    private int getLineHeight(int line, String content) {
        TextView tempTextView = new TextView(UIUtils.getContext());
        tempTextView.setText(content);
        tempTextView.setLines(line);

        tempTextView.measure(0, 0);
        return tempTextView.getMeasuredHeight();
    }
}
