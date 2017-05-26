package com.itheima.googleplay_17.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.utils.UIUtils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 14:16
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 15:23:36 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class DetailSafeHolder extends BaseHolder<ItemInfoBean> implements View.OnClickListener {


    @Bind(R.id.app_detail_safe_iv_arrow)
    ImageView mAppDetailSafeIvArrow;

    @Bind(R.id.app_detail_safe_pic_container)
    LinearLayout mAppDetailSafePicContainer;

    @Bind(R.id.app_detail_safe_des_container)
    LinearLayout mAppDetailSafeDesContainer;

    private boolean isOpen = true;

    @Override
    public View initHolderView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_detail_safe, null);//没有具体的数据
        //找孩子,转换成成员变量
        ButterKnife.bind(this, rootView);

        //设置点击事件
        rootView.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {//绑定具体的数据
        //data
        //view
        List<ItemInfoBean.ItemInfoSafeBean> safe = data.safe;
        for (ItemInfoBean.ItemInfoSafeBean info : safe) {

            String safeUrl = info.safeUrl;

            ImageView ivIcon = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + safeUrl).into(ivIcon);


            mAppDetailSafePicContainer.addView(ivIcon);


            LinearLayout line = new LinearLayout(UIUtils.getContext());

            ImageView ivDesIcon = new ImageView(UIUtils.getContext());
            //图片的加载
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + info.safeDesUrl).into(ivDesIcon);

            TextView tvDes = new TextView(UIUtils.getContext());
            //文本设置
            tvDes.setText(info.safeDes);

            //文本颜色
            if (info.safeDesColor == 0) {//灰色
                tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            } else {//警告色
                tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
            }

            int padding = UIUtils.dip2Px(4);
            line.setPadding(padding, padding, padding, padding);

            line.addView(ivDesIcon);
            line.addView(tvDes);


            mAppDetailSafeDesContainer.addView(line);
        }

        //默认折叠mAppDetailSafeDesContainer,而且不带动画
        toggleAnimation(false);
    }

    @Override
    public void onClick(View v) {
        toggleAnimation(true);
    }

    private void toggleAnimation(boolean isAnimation) {
        if (isOpen) {//折叠
            //折叠mAppDetailSafeDesContainer 高度 从 应有的高度-->0
            mAppDetailSafeDesContainer.measure(0, 0);
            int measuredHeight = mAppDetailSafeDesContainer.getMeasuredHeight();

            int start = measuredHeight;
            int end = 0;
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                //直接修改高度
                ViewGroup.LayoutParams params = mAppDetailSafeDesContainer.getLayoutParams();
                params.height = end;
                mAppDetailSafeDesContainer.setLayoutParams(params);
            }
        } else {//展开
            //展开 mAppDetailSafeDesContainer 高度 从 0-->应有的高度
            mAppDetailSafeDesContainer.measure(0, 0);
            int measuredHeight = mAppDetailSafeDesContainer.getMeasuredHeight();

            int start = 0;
            int end = measuredHeight;

            if (isAnimation) {
                doAnimation(start, end);
            } else {
                //直接修改高度
                ViewGroup.LayoutParams params = mAppDetailSafeDesContainer.getLayoutParams();
                params.height = end;
                mAppDetailSafeDesContainer.setLayoutParams(params);
            }
        }
        isOpen = !isOpen;
    }

    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.start();

        //得到动画执行过程中的渐变值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int) valueAnimator.getAnimatedValue();

                //得到mAppDetailSafeDesContainer当前的layoutParams信息
                ViewGroup.LayoutParams layoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                layoutParams.height = height;

                //设置最新的layoutParams信息
                mAppDetailSafeDesContainer.setLayoutParams(layoutParams);
            }
        });
        //箭头跟着动
        if (isOpen) {
            //            mAppDetailSafeIvArrow.setRotation();
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 180, 0).start();
        } else {
            ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 0, 180).start();
        }
    }
}
