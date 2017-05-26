package com.itheima.googleplay_17.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.itheima.googleplay_17.utils.UIUtils;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 08:50
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 08:59:41 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class ListViewFactory {
    public static ListView createListView() {
        ListView listView = new ListView(UIUtils.getContext());
        //常规的设置
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
        listView.setCacheColorHint(Color.TRANSPARENT);
        return listView;
    }
}
