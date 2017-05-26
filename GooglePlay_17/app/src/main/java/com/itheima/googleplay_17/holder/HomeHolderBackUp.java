package com.itheima.googleplay_17.holder;

import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.utils.UIUtils;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 16:56
 * 描述	      1.提供视图
 * 描述	      2.接收数据
 * 描述	      3.数据和视图的绑定
 * <p/>
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-28 17:15:39 +0800 (星期一, 28 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class HomeHolderBackUp {
    public View mHolderView;//提供的视图(是经过了数据绑定)

    private final TextView mTvTmp1;
    private final TextView mTvTmp2;

    private String mData;

    public HomeHolderBackUp() {
        //初始化持有的视图
        mHolderView = View.inflate(UIUtils.getContext(), R.layout.item_tmp, null);

        //初始化孩子对象
        mTvTmp1 = (TextView) mHolderView.findViewById(R.id.tmp_tv_1);
        mTvTmp2 = (TextView) mHolderView.findViewById(R.id.tmp_tv_2);

        //convertView去找一个类的一个对象作为holder
        mHolderView.setTag(this);
    }

    /**
     * @param data
     * @des 接收数据, 绑定数据
     * @called 外界需要传递数据, 让我进行数据绑定的时候调用
     */
    public void setDataAndRefreshHolderView(String data) {
        //保存数据到成员变量
        mData = data;
        refreshHolderView(data);
    }

    /**
     * @param data
     * @des 绑定数据
     * @called 外界需要传递数据, 让我进行数据绑定的时候调用
     */
    private void refreshHolderView(String data) {
        //data
        //view
        mTvTmp1.setText("我是头-" + data);
        mTvTmp2.setText("我是尾巴-" + data);
    }
}
