package com.itheima.googleplay_17.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay_17.base.BaseFragment;
import com.itheima.googleplay_17.base.LoadingPager;
import com.itheima.googleplay_17.protocol.RecommendProtocol;
import com.itheima.googleplay_17.utils.UIUtils;
import com.itheima.googleplay_17.views.flyinout.ShakeListener;
import com.itheima.googleplay_17.views.flyinout.StellarMap;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 15:34
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 09:52:00 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class RecommendFragment extends BaseFragment {

    private List<String>  mDatas;
    private ShakeListener mShakeListener;

    /**
     * @return
     * @des 真正的在子线程中加载数据, 得到数据
     * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    @Override
    public LoadingPager.LoadedResultState initData() {
        RecommendProtocol protocol = new RecommendProtocol();
        try {
            mDatas = protocol.loadData(0);
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
        //view
        final StellarMap map = new StellarMap(UIUtils.getContext());
        //拆分屏幕
        map.setRegularity(15, 20);

        final RecommendAdapter adapter = new RecommendAdapter();
        map.setAdapter(adapter);

        //第一页没有展示
        map.setGroup(0, true);


        //摇一摇切换
        mShakeListener = new ShakeListener(UIUtils.getContext());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                //切换到下一页
                //得到当前页
                int currentGroup = map.getCurrentGroup();
                if (currentGroup == adapter.getGroupCount() - 1) {
                    currentGroup = 0;
                } else {
                    currentGroup++;
                }
                //设置
                map.setGroup(currentGroup, true);
            }
        });
        return map;
    }

    @Override
    public void onResume() {
        if (mShakeListener != null) {
            mShakeListener.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mShakeListener != null) {
            mShakeListener.pause();
        }
        super.onPause();
    }

    class RecommendAdapter implements StellarMap.Adapter {
        public static final int PAGESIZE = 15;

        @Override
        public int getGroupCount() {//一共有多少组
            if (mDatas.size() % PAGESIZE == 0) {
                return mDatas.size() / PAGESIZE;//32/15= 2
            } else {
                return mDatas.size() / PAGESIZE + 1;//32/15= 2
            }

        }

        @Override
        public int getCount(int group) {//每组多少个
            if (mDatas.size() % PAGESIZE == 0) {
                return PAGESIZE;
            } else {
                //如果是最后一组的
                if (group == getGroupCount() - 1) {
                    return mDatas.size() % PAGESIZE;
                } else {
                    return PAGESIZE;
                }
            }
        }

        @Override
        public View getView(int group, int position, View convertView) {
            //view
            TextView tv = new TextView(UIUtils.getContext());
            //data
            int index = group * PAGESIZE + position;
            String data = mDatas.get(index);

            Random random = new Random();
            //随机大小
            tv.setTextSize(random.nextInt(4) + 12);//12-16
            //随机颜色
            int alpah = 255;
            int red = random.nextInt(190) + 30;//30-220
            int green = random.nextInt(190) + 30;//30-220
            int blue = random.nextInt(190) + 30;//30-220
            int argb = Color.argb(alpah, red, green, blue);
            tv.setTextColor(argb);

            tv.setText(data);
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}