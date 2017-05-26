package com.itheima.googleplay_17.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_17.base.BaseFragment;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.base.LoadingPager;
import com.itheima.googleplay_17.base.SuperBaseAdapter;
import com.itheima.googleplay_17.bean.CategoryInfoBean;
import com.itheima.googleplay_17.holder.CategoryNormalHolder;
import com.itheima.googleplay_17.holder.CategoryTitleHolder;
import com.itheima.googleplay_17.protocol.CategoryProtocol;
import com.itheima.googleplay_17.utils.LogUtils;

import java.io.IOException;
import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 15:34
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 16:33:36 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class CategoryFragment extends BaseFragment {

    private List<CategoryInfoBean> mDatas;

    /**
     * @return
     * @des 真正的在子线程中加载数据, 得到数据
     * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    @Override
    public LoadingPager.LoadedResultState initData() {
        CategoryProtocol protocol = new CategoryProtocol();
        try {
            mDatas = protocol.loadData(0);
            LogUtils.printList(mDatas);
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
        listView.setAdapter(new CategoryAdapter(listView, mDatas));
        return listView;
    }

    class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean> {

        public CategoryAdapter(AbsListView absListView, List datas) {
            super(absListView, datas);
        }

        @Override
        public BaseHolder getSpecialHolder(int position) {//data+View

            //data
            CategoryInfoBean categoryInfoBean = mDatas.get(position);

            if (categoryInfoBean.isTitle) {
                return new CategoryTitleHolder();
            } else {
                return new CategoryNormalHolder();
            }
        }

        @Override
        public int getViewTypeCount() {
            return 3;//2+1 = 3
        }

        @Override
        public int getNormItemViewType(int position) {
            CategoryInfoBean categoryInfoBean = mDatas.get(position);
            if (categoryInfoBean.isTitle) {
                return 2;
            } else {
                return 1;
            }
            //            return super.getNormItemViewType(position);//默认是返回1
        }
    }

}