package com.itheima.googleplay_17.holder;

import android.view.View;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.utils.UIUtils;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/3 15:26
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 15:30:57 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class MenuLeftHolder extends BaseHolder<Object> {
    @Override
    public View initHolderView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_menu_left, null);
        return view;
    }

    @Override
    public void refreshHolderView(Object data) {

    }
}
