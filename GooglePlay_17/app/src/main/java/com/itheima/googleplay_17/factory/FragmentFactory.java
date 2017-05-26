package com.itheima.googleplay_17.factory;

import com.itheima.googleplay_17.base.BaseFragment;
import com.itheima.googleplay_17.fragment.AppFragment;
import com.itheima.googleplay_17.fragment.CategoryFragment;
import com.itheima.googleplay_17.fragment.GameFragment;
import com.itheima.googleplay_17.fragment.HomeFragment;
import com.itheima.googleplay_17.fragment.HotFragment;
import com.itheima.googleplay_17.fragment.RecommendFragment;
import com.itheima.googleplay_17.fragment.SubjectFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 15:30
 * 描述	      帮我们生产出Fragment
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-28 15:47:41 +0800 (星期一, 28 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class FragmentFactory {
    public static final int FRAGMENT_HOME      = 0;//首页
    public static final int FRAGMENT_APP       = 1;//应用
    public static final int FRAGMENT_GAME      = 2;//游戏
    public static final int FRAGMENT_SUBJECT   = 3;//专题
    public static final int FRAGMENT_RECOMMEND = 4;//推荐
    public static final int FRAGMENT_CATEGORY  = 5;//分类
    public static final int FRAGMENT_HOT       = 6;//排行

    private static Map<Integer, BaseFragment> mFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment frament = null;
        if (mFragmentMap.containsKey(position)) {
            frament = mFragmentMap.get(position);
            return frament;
        }
        switch (position) {

            case FRAGMENT_HOME:
                frament = new HomeFragment();

                break;
            case FRAGMENT_APP:
                frament = new AppFragment();
                break;
            case FRAGMENT_GAME:
                frament = new GameFragment();
                break;
            case FRAGMENT_SUBJECT:
                frament = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND:
                frament = new RecommendFragment();
                break;
            case FRAGMENT_CATEGORY:
                frament = new CategoryFragment();
                break;
            case FRAGMENT_HOT:
                frament = new HotFragment();
                break;

            default:
                break;
        }
        //加入集合中
        mFragmentMap.put(position,frament);

        return frament;
    }
}
