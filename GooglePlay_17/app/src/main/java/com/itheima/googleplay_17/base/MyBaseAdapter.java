package com.itheima.googleplay_17.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 16:37
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-28 17:23:50 +0800 (星期一, 28 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class MyBaseAdapter<ITEMBEANTYPE> extends BaseAdapter {

    public List<ITEMBEANTYPE> mDataSet = new ArrayList<>();

    public MyBaseAdapter(List<ITEMBEANTYPE> datas) {
        mDataSet = datas;
    }

    @Override
    public int getCount() {
        if (mDataSet != null) {
            return mDataSet.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDataSet != null) {
            return mDataSet.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
