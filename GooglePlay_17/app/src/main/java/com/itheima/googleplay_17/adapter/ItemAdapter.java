package com.itheima.googleplay_17.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.itheima.googleplay_17.activity.DetailActivity;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.base.SuperBaseAdapter;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.holder.ItemHolder;
import com.itheima.googleplay_17.manager.DownLoadManager;
import com.itheima.googleplay_17.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 10:11
 * 描述	      HomeFragment,AppFragment,GameFragment里面Adapter的基类
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 14:53:34 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class ItemAdapter extends SuperBaseAdapter<ItemInfoBean> {
    //使用集合保存adapter中的ItemHolder(观察者)

    public List<ItemHolder> mItemHolders = new ArrayList<>();

    public ItemAdapter(AbsListView absListView, List datas) {
        super(absListView, datas);
    }

    @Override
    public BaseHolder getSpecialHolder(int position) {
        ItemHolder itemHolder = new ItemHolder();

        //添加观察者到集合中
        mItemHolders.add(itemHolder);


        //添加观察者到观察者集合中
        DownLoadManager.getInstance().addObserver(itemHolder);

        return itemHolder;
    }

    @Override
    public boolean hasLoadMore() {
        return true;
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(UIUtils.getContext(), ((ItemInfoBean) mDataSet.get(position)).packageName, Toast.LENGTH_SHORT)
                .show();

        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.putExtra("packageName", ((ItemInfoBean) mDataSet.get(position)).packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
        super.onNormalItemClick(parent, view, position, id);
    }
}
