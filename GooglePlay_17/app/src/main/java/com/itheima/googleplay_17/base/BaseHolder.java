package com.itheima.googleplay_17.base;

import android.view.View;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 17:06
 * 看成Data+View,或者看成应用里面的模块
 * 描述	      1.提供视图(经过了数据绑定)
 * 描述	      2.接收数据
 * 描述	      3.数据的绑定
 * <p/>
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 08:59:41 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public abstract class BaseHolder<HOLDERBEANTYPE> {
    public View mHolderView;//提供的视图(是经过了数据绑定)

    public HOLDERBEANTYPE mData;

    public BaseHolder() {
        //初始化持有的视图
        mHolderView = initHolderView();

        //convertView去找一个类的一个对象作为holder
        mHolderView.setTag(this);
    }


    /**
     * @param data
     * @des 接收数据, 绑定数据
     * @called 外界需要传递数据, 让我进行数据绑定的时候调用
     */
    public void setDataAndRefreshHolderView(HOLDERBEANTYPE data) {
        //保存数据到成员变量
        mData = data;
        refreshHolderView(data);
    }


    /**
     * @return
     * @des 持有的根视图
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called BaesHolder的子类一旦初始化的时候会被调用
     */
    public abstract View initHolderView();

    /**
     * @param data
     * @des 绑定数据
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called 外界需要传递数据, 让我进行数据绑定的时候调用
     */
    public abstract void refreshHolderView(HOLDERBEANTYPE data);
}
