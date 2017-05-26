package com.itheima.googleplay_17.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itheima.googleplay_17.factory.ThreadPoolProxyFactory;
import com.itheima.googleplay_17.holder.LoadMoreHolder;
import com.itheima.googleplay_17.utils.UIUtils;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 17:15
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 10:19:09 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public abstract class SuperBaseAdapter<ITEMBEANTYPE> extends MyBaseAdapter implements AdapterView.OnItemClickListener {

    public static final int VIEWTYPE_LOADMORE = 0;//加载更多的条目类型
    public static final int VIEWTYPE_NORMAL   = 1;//普通条目类型
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask   mLoadMoreTask;
    private AbsListView    mAbsListView;
    private int            mLoadResultState;

    public SuperBaseAdapter(AbsListView absListView, List datas) {
        super(datas);
        mAbsListView = absListView;
        mAbsListView.setOnItemClickListener(this);
    }


    /**
     ListView中显示几种ViewType类型
     1.覆写2个方法
     2.在getView中分别处理
     */
    /**
     * get(得到) ViewType(ViewType类型) Count(的总数)
     */
    @Override
    public int getViewTypeCount() {
        //        默认值1
        return super.getViewTypeCount() + 1;//1(普通的类型)+1(加载更多的类型) = 2
    }

    /**
     * get(得到)Item(指定条目)ViewType(ViewType类型)(int position)
     */
    /**
     * 0
     * to
     * getViewTypeCount - 1
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;//0
        } else {
            return getNormItemViewType(position);//1 2
        }

    }

    /**
     * @param position
     * @return
     * @des 得到普通条目的ViewType类型,默认是1
     * @des 子类可以覆写该方法,返回通过的普通条目的ViewType类型
     */
    public int getNormItemViewType(int position) {
        return VIEWTYPE_NORMAL;//默认返回1
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        /**
         系统返回convertView之前,优先会判断,判断即将展示的rootView和已经缓存好的convertView的ViewType类型
         是否一致
         */

         /*--------------- 决定根视图 ---------------*/
        BaseHolder baseHolder = null;
        if (convertView == null) {
            //分情况返回BaseHolder的子类
            if (getItemViewType(position) == VIEWTYPE_LOADMORE) {//item是加载更多的类型
                baseHolder = getLoadMoreHolder();
            } else {//item是普通类型
                baseHolder = getSpecialHolder(position);
            }
        } else {
            baseHolder = (BaseHolder) convertView.getTag();
        }
            /*--------------- 接收数据,绑定数据 ---------------*/

        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {//加载更多的情况
            //            baseHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_LOADING);
            if (hasLoadMore()) {
                triggerLoadMoreData();
            } else {
                baseHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_NONE);
            }
        } else {
            baseHolder.setDataAndRefreshHolderView(mDataSet.get(position));
        }

        return baseHolder.mHolderView;
    }

    /**
     * @return
     * @des 决定是否有加载更多
     * @des 子类可以覆写该方法, 修改默认行为
     */
    public boolean hasLoadMore() {
        return false;//默认是没有加载更多
    }



    /*--------------- 加载更多 begin ---------------*/

    /**
     * @return
     * @des 返回BaseHolder的子类对象, 其实也是加载更多的Holder(data+view)
     */
    private LoadMoreHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }

        return mLoadMoreHolder;
    }

    /**
     * 触发加载更多的数据
     */
    private void triggerLoadMoreData() {
        if (mLoadMoreTask == null) {
            //处理loadmoreholder的ui
            int state = LoadMoreHolder.LOADMORE_LOADING;
            mLoadMoreHolder.setDataAndRefreshHolderView(state);

            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolProxyFactory.createNormalThreadPoolProxy().execute(mLoadMoreTask);
        }
    }

    class LoadMoreTask implements Runnable {
        private static final int PAGERSIZE = 20;//每页请求的是20条数据

        @Override
        public void run() {
            //真正的开始加载更多
            /*--------------- 定义刷新ui需要的初始值 ---------------*/
            List<ITEMBEANTYPE> loadMoreList = null;
            mLoadResultState = LoadMoreHolder.LOADMORE_LOADING;

            /*--------------- 加载数据,得到数据,处理数据 ---------------*/
            //得到加载更多数据
            try {
                loadMoreList = initLoadMoreData();
                //根据加载更多数据,得到状态
                if (loadMoreList == null) {
                    mLoadResultState = LoadMoreHolder.LOADMORE_NONE;//没有加载更多
                } else {
                    if (loadMoreList.size() == PAGERSIZE) {//回来的数据长度==请求的分页条目长度
                        mLoadResultState = LoadMoreHolder.LOADMORE_LOADING;//还有可能有加载更多-->用户看到就是loading状态
                    } else {//回来的数据长度<请求的分页条目长度
                        mLoadResultState = LoadMoreHolder.LOADMORE_NONE;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mLoadResultState = LoadMoreHolder.LOADMORE_ERROR;
            }

            /*--------------- 定义两个临时变量 ---------------*/
            final List<ITEMBEANTYPE> finalLoadMoreList = loadMoreList;
            final int finalState = mLoadResultState;

            /*--------------- 根据数据,刷新ui ---------------*/
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    //刷新ui--Listview--得到list追加到已有的dataSet-->notifyDataSetChanged==>List
                    if (finalLoadMoreList != null) {
                        mDataSet.addAll(finalLoadMoreList);
                        notifyDataSetChanged();

                    }

                    //刷新ui--loadMoreHolder--只需要给它最新的状态==>Int
                    mLoadMoreHolder.setDataAndRefreshHolderView(finalState);
                }
            });

            //置空加载更多的任务
            mLoadMoreTask = null;
        }
    }


    /**
     * 1.方法抛出的异常,抛到哪里去了?
     * 抛到方法调用出
     * 2.什么时候需要在定义方法的时候抛出异常?
     * 方法调用出,会根据抛出的异常处理不同的逻辑的时候
     */
    /**
     * @return
     * @des 开始在子线程中加载更多的数据
     * @called listView滑动底部的时候
     */
    public List<ITEMBEANTYPE> initLoadMoreData() throws Exception {

        return null;
    }
    /*--------------- 加载更多 end ---------------*/

    /**
     * @param position
     * @return
     * @des 返回一个BaseHolder的子类对象
     * @called 走到getView方法, 并且(convertView == null)的时候被调用
     */
    public abstract BaseHolder getSpecialHolder(int position);

    /*--------------- 处理点击重试操作 ---------------*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // 处理position
        if(mAbsListView instanceof ListView){
            position = position - ((ListView) mAbsListView).getHeaderViewsCount();
        }

        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
            //触发加载更多的数据
            if (mLoadResultState == LoadMoreHolder.LOADMORE_ERROR) {
                triggerLoadMoreData();
            }
        } else {
            //在这里能不能知道,点击了普通条目应该做啥
            onNormalItemClick(parent, view, position, id);
        }
    }

    /**
     * @param parent
     * @param view
     * @param position
     * @param id
     * @des 点击了普通的条目
     */
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
