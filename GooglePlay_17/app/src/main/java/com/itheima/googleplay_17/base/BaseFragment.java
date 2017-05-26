package com.itheima.googleplay_17.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.itheima.googleplay_17.utils.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 09:07
 * 描述	     谷歌市场里面对Fragment的抽取封装
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-30 10:21:22 +0800 (星期三, 30 十二月 2015) $
 * 更新描述   ${TODO}
 */
public abstract class BaseFragment extends Fragment {

    public LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /**
         1.提供视图_(加载视图,成功,空,失败)
         2.加载数据/接收数据
         1.触发
         2.异步
         3.处理结果
         成功-->成功视图
         失败
         空:
         加载失败:
         3.绑定数据
         */
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {

                /**
                 * @return
                 * @des 真正的在子线程中加载数据, 得到数据
                 * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
                 */
                @Override
                public LoadedResultState initData() {
                    // 找BaseFragment,真正的在子线程中加载数据, 得到数据
                    return BaseFragment.this.initData();
                }

                /**
                 * @return
                 * @des 初始化具体的成功视图
                 * @called 触发加载数据, 数据加载完成后, 并且数据加载成功后
                 */
                @Override
                public View initSuccessView() {
                    // 找BaseFragment,初始化具体的成功视图
                    return BaseFragment.this.initSuccessView();
                }
            };
        } else {
            //使用eclipse的同学因为v4包版本的问题,需要加上这段代码
            ViewParent parent = mLoadingPager.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mLoadingPager);
            }
        }

        return mLoadingPager;
    }

    // 页面显示分析
    // Fragment/Acitivyt共性-->页面共性-->视图的展示
    /**
     任何应用其实就只有4种页面类型
     ① 加载页面
     ② 错误页面
     ③ 空页面
     ④ 成功页面
     ①②③三种页面一个应用基本是固定的
     每一个fragment对应的页面④就不一样
     进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
     */

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
     * @return
     * @des 真正的在子线程中加载数据, 得到数据
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    public abstract LoadingPager.LoadedResultState initData();

    /**
     * @return
     * @des 初始化具体的成功视图
     * @des 必须实现, 但是不知道具体实现, 定义成为抽象方法, 交给子类具体实现
     * @called 触发加载数据, 数据加载完成后, 并且数据加载成功后
     */
    public abstract View initSuccessView();

    /**
     * @param resObj
     * @des 根据返回回来的数据, 返回具体的状态
     */
    public LoadingPager.LoadedResultState checkResData(Object resObj) {
        if (resObj == null) {
            return LoadingPager.LoadedResultState.EMPTY;
        }
        if (resObj instanceof List) {
            if (((List) resObj).size() == 0) {
                return LoadingPager.LoadedResultState.EMPTY;
            }
        }
        if (resObj instanceof Map) {
            if (((Map) resObj).size() == 0) {
                return LoadingPager.LoadedResultState.EMPTY;
            }
        }
        return LoadingPager.LoadedResultState.SUCCESS;
    }
}
