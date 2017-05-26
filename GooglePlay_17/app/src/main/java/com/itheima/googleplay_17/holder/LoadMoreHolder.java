package com.itheima.googleplay_17.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/30 14:41
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-30 14:53:32 +0800 (星期三, 30 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class LoadMoreHolder extends BaseHolder<Integer> {
    public static final int LOADMORE_LOADING = 0;//正在加载更多
    public static final int LOADMORE_ERROR   = 1;//加载更多失败
    public static final int LOADMORE_NONE    = 2;//没有加载更多


    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;

    //泛型里面定义的数据类型干嘛用的?
    //决定ui的展现
    //因为加载更多的视图,只有3种情况.所以这里传递一个int即可
    @Override
    public View initHolderView() {
        //持有视图
        View view = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);
        //找到孩子
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void refreshHolderView(Integer state) {
        //隐藏所有的视图
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (state) {
            case LOADMORE_LOADING:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_NONE:

                break;

            default:
                break;
        }
    }

}
