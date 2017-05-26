package com.itheima.googleplay_17.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_17.adapter.ItemAdapter;
import com.itheima.googleplay_17.base.BaseFragment;
import com.itheima.googleplay_17.base.LoadingPager;
import com.itheima.googleplay_17.bean.GameProtocol;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.holder.ItemHolder;
import com.itheima.googleplay_17.manager.DownLoadInfo;
import com.itheima.googleplay_17.manager.DownLoadManager;

import java.io.IOException;
import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 15:34
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 14:58:05 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class GameFragment extends BaseFragment {

    private GameProtocol       mProtocol;
    private List<ItemInfoBean> mDatas;
    private GameAdpater mAdapter;

    /**
     * @return
     * @des 真正的在子线程中加载数据, 得到数据
     * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    @Override
    public LoadingPager.LoadedResultState initData() {
        mProtocol = new GameProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            return checkResData(mDatas);
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadedResultState.ERROR;
        }
    }

    /**
     * @return
     * @des 初始化具体的成功视图
     * @called 触发加载数据, 数据加载完成后, 并且数据加载成功后
     */
    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();

        mAdapter = new GameAdpater(listView, mDatas);
        listView.setAdapter(mAdapter);
        return listView;
    }

    class GameAdpater extends ItemAdapter {

        public GameAdpater(AbsListView absListView, List datas) {
            super(absListView, datas);
        }

        @Override
        public List<ItemInfoBean> initLoadMoreData() throws Exception {
            SystemClock.sleep(2000);
            List<ItemInfoBean> itemInfoBeans = mProtocol.loadData(mDatas.size());
            return itemInfoBeans;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        //移除所有的观察者
        if (mAdapter != null) {
            List<ItemHolder> itemHolders = mAdapter.mItemHolders;
            for (ItemHolder itemHolder : itemHolders) {
                DownLoadManager.getInstance().deleteObserver(itemHolder);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //添加所有的观察者
        //移除所有的观察者
        if (mAdapter != null) {
            List<ItemHolder> itemHolders = mAdapter.mItemHolders;
            for (ItemHolder itemHolder : itemHolders) {
                DownLoadManager.getInstance().addObserver(itemHolder);

                //根据itemInfoBean得到一个downLoadInfo信息
                DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(itemHolder.mData);
                //手动发布最新的状态
                DownLoadManager.getInstance().notifyObservers(downLoadInfo);
            }
        }
    }
}