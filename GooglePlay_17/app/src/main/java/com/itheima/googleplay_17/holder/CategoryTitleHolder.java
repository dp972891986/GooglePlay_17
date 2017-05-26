package com.itheima.googleplay_17.holder;

import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.bean.CategoryInfoBean;
import com.itheima.googleplay_17.utils.UIUtils;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 15:28
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 15:42:34 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class CategoryTitleHolder extends BaseHolder<CategoryInfoBean> {

    private TextView mTv;

    @Override
    public View initHolderView() {
        mTv = new TextView(UIUtils.getContext());
        int padding = UIUtils.dip2Px(5);
        mTv.setPadding(padding, padding, padding, padding);
        return mTv;
    }

    @Override
    public void refreshHolderView(CategoryInfoBean data) {
        mTv.setText(data.title);
    }
}
