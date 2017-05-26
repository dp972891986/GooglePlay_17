package com.itheima.googleplay_17.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_17.base.BaseFragment;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.base.LoadingPager;
import com.itheima.googleplay_17.base.SuperBaseAdapter;
import com.itheima.googleplay_17.bean.SubjectInfoBean;
import com.itheima.googleplay_17.holder.SubjectHolder;
import com.itheima.googleplay_17.protocol.SubjectProtocol;

import java.io.IOException;
import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 15:34
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 15:32:29 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class SubjectFragment extends BaseFragment {

    private List<SubjectInfoBean> mDatas;
    private SubjectProtocol mProtocol;

    /**
     * @return
     * @des 真正的在子线程中加载数据, 得到数据
     * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    @Override
    public LoadingPager.LoadedResultState initData() {
        mProtocol = new SubjectProtocol();
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
        listView.setAdapter(new SubjectAdapter(listView,mDatas));
        return listView;
    }
    class SubjectAdapter extends SuperBaseAdapter<SubjectInfoBean> {

        public SubjectAdapter(AbsListView absListView, List datas) {
            super(absListView, datas);
        }

        @Override
        public BaseHolder getSpecialHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List<SubjectInfoBean> initLoadMoreData() throws Exception {
            List<SubjectInfoBean> subjectInfoBeans = mProtocol.loadData(mDatas.size());
            return subjectInfoBeans;
        }
    }
}