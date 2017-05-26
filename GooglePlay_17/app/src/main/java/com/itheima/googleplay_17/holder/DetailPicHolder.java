package com.itheima.googleplay_17.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.utils.UIUtils;
import com.itheima.googleplay_17.views.RatioLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 14:16
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 15:36:42 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class DetailPicHolder extends BaseHolder<ItemInfoBean> {


    @Bind(R.id.app_detail_pic_iv_container)
    LinearLayout mAppDetailPicIvContainer;

    @Override
    public View initHolderView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_detail_pic, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void refreshHolderView(ItemInfoBean data) {
        List<String> screenList = data.screen;
        for (int i = 0; i < screenList.size(); i++) {
            String screen = screenList.get(i);

            ImageView iv = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + screen).into(iv);

            RatioLayout rl = new RatioLayout(UIUtils.getContext());
            rl.setRelative(RatioLayout.RELATIVE_WIDTH);//已知宽度,动态计算高度
            rl.setPicRatio((float) 150 / 250);
            rl.addView(iv);


            //加载图片

            //得到屏幕的宽度

            int width_ = UIUtils.getResources().getDisplayMetrics().widthPixels;
            width_ = width_ - UIUtils.dip2Px(12);
            width_ = width_ / 3;

            int width = width_;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            if (i != 0) {
                params.leftMargin = UIUtils.dip2Px(3);
            }


            mAppDetailPicIvContainer.addView(rl, params);
        }
    }
}
