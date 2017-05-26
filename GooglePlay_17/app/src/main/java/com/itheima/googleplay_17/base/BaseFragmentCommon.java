package com.itheima.googleplay_17.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 09:04
 * 描述	     Fragment常规的抽取
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-28 09:06:35 +0800 (星期一, 28 十二月 2015) $
 * 更新描述   ${TODO}
 */
public abstract class BaseFragmentCommon extends Fragment {
	/**
	 * 1.放置共有的属性和共有的方法
	 * 2.不用关心Fragment相关的生命周期方法
	 * 3.还可以控制哪一个方法是必须实现,哪一个方法是选择性实现
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		init();
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		initData();
		initListener();
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 初始化
	 */
	public void init() {

	}

	/**
	 * 初始化view
	 */
	public abstract View initView();

	/**
	 * 初始化数据
	 */
	public void initData() {

	}

	/**
	 * 初始化监听
	 */
	public void initListener() {

	}
}
