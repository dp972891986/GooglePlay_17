package com.itheima.googleplay_17.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.factory.ThreadPoolProxyFactory;
import com.itheima.googleplay_17.utils.LogUtils;
import com.itheima.googleplay_17.utils.UIUtils;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 09:18
 * 描述	     1.提供视图(4种视图类型中的一种)
 * 描述	     2.触发加载数据(绑定具体的视图)
 * 描述	     3.定义了一个initData的抽象方法-->在子线程中真正的加载数据
 * 描述	     4.定义了一个initSuccessview的抽象方法-->初始化成功视图
 * <p/>
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-28 16:36:44 +0800 (星期一, 28 十二月 2015) $
 * 更新描述   ${TODO}
 */
public abstract class LoadingPager extends FrameLayout {
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public static final int STATE_LOADING = 0;                // 展示加载中视图
    public static final int STATE_EMPTY   = 1;                // 展示空视图
    public static final int STATE_ERROR   = 2;                // 展示错误视图
    public static final int STATE_SUCCESS = 3;                // 展示成功视图

    public int mCurState = STATE_LOADING;    // 默认展示加载中的视图
    private LoadDataTask mLoadDataTask;

    /**
     * 任何应用其实就只有4种页面类型
     * ① 加载页面
     * ② 错误页面
     * ③ 空页面
     * ④ 成功页面
     * ①②③三种页面一个应用基本是固定的
     * 每一个fragment对应的页面④就不一样
     * 进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
     */

    public LoadingPager(Context context) {
        super(context);
        initCommonView();
    }

    /**
     * @des 初始化常规视图(加载页面, 错误页面, 空页面)
     */
    private void initCommonView() {
        // ① 加载页面
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        this.addView(mLoadingView);

        // ② 错误页面
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        this.addView(mErrorView);

        // ③ 空页面
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        this.addView(mEmptyView);

        refreshViewByState();

    }

    /**
     * @des 根据当前的类型, 展示不同的视图类型
     * @called 1.LoadingPager初始化调用了一次
     * @called 2.数据开始加载的时候会调用一次
     * @called 3.触发加载数据完成时候调用了一次
     */
    private void refreshViewByState() {

        // 控制 加载中视图 的显示/隐藏
        mLoadingView.setVisibility((mCurState == STATE_LOADING) ? View.VISIBLE : View.GONE);

        // 控制 错误视图 的显示/隐藏
        mErrorView.setVisibility((mCurState == STATE_ERROR) ? View.VISIBLE : View.GONE);

        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新触发加载数据
                triggerLoadData();
            }
        });


        // 控制 空视图 的显示/隐藏
        mEmptyView.setVisibility((mCurState == STATE_EMPTY) ? View.VISIBLE : View.GONE);

        // 数据加载成功&&当前的成功视图还是空
        if (mCurState == STATE_SUCCESS && mSuccessView == null) {
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }

        // 控制 空视图 的显示/隐藏
        if (mSuccessView != null) {
            mSuccessView.setVisibility((mCurState == STATE_SUCCESS) ? View.VISIBLE : View.GONE);
        }

    }

    // 数据加载的流程
    /**
     ① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
     ② 异步加载数据  -->显示加载视图
     ③ 处理加载结果
     ① 成功-->显示成功视图
     ② 失败
     ① 数据为空-->显示空视图
     ② 数据加载失败-->显示加载失败的视图
     */
    /**
     * @des ① 触发加载
     * @called 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    public void triggerLoadData() {
        if (mCurState != STATE_SUCCESS && mLoadDataTask == null) {//数据没有加载成功&&没有正在加载的时候
            LogUtils.s("###触发加载数据");
            //一开始加载,需要重置当前的状态
            mCurState = STATE_LOADING;
            refreshViewByState();


            // ② 异步加载数据
            mLoadDataTask = new LoadDataTask();

//            new Thread(mLoadDataTask).start();

            ThreadPoolProxyFactory.createNormalThreadPoolProxy().execute(mLoadDataTask);
        }
    }

    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            // 真正的在子线程中加载数据,得到数据
            LoadedResultState loadedResultState = initData();

            // 处理数据-->更新状态
            mCurState = loadedResultState.getState();

            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    // 更新ui
                    refreshViewByState();
                }
            });

            //走到run方法体的最后.一个任务就执行完成
            mLoadDataTask = null;
        }
    }

    /**
     * @return
     * @des 真正的在子线程中加载数据, 得到数据
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    public abstract LoadedResultState initData();

    /**
     * @return
     * @des 初始化具体的成功视图
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called 触发加载数据, 数据加载完成后, 并且数据加载成功后
     */
    public abstract View initSuccessView();

    public enum LoadedResultState {

        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);

        // LoadedResultState.SUCCESS-->state-->STATE_SUCCESS
        // LoadedResultState.ERROR-->state-->STATE_ERROR
        // LoadedResultState.EMPTY-->state-->STATE_EMPTY
        int state;

        public int getState() {
            return state;
        }

        private LoadedResultState(int state) {
            this.state = state;
        }
    }
}
