package com.itheima.googleplay_17.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.googleplay_17.base.BaseFragment;
import com.itheima.googleplay_17.base.LoadingPager;
import com.itheima.googleplay_17.protocol.HotProtocol;
import com.itheima.googleplay_17.utils.UIUtils;
import com.itheima.googleplay_17.views.FlowLayout;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 15:34
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 10:10:28 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class HotFragment extends BaseFragment {

    private List<String> mDatas;

    /**
     * @return
     * @des 真正的在子线程中加载数据, 得到数据
     * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    @Override
    public LoadingPager.LoadedResultState initData() {
        HotProtocol protocol = new HotProtocol();
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
        ScrollView sv = new ScrollView(UIUtils.getContext());
        FlowLayout fl = new FlowLayout(UIUtils.getContext());


        for (final String data : mDatas) {
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(data);
            //设置padding
            int padding = UIUtils.dip2Px(4);
            tv.setPadding(padding, padding, padding, padding);
            //设置居中
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.WHITE);

            //通过代码得方式创建一个shape图片
            GradientDrawable normalBg = new GradientDrawable();
            normalBg.setCornerRadius(8);


            Random random = new Random();
            int alpha = 255;
            int red = random.nextInt(190) + 30;//30--220
            int green = random.nextInt(190) + 30;//30--220
            int bule = random.nextInt(190) + 30;//30--220
            int argb = Color.argb(alpha, red, green, bule);
            normalBg.setColor(argb);


            GradientDrawable pressBg = new GradientDrawable();
            pressBg.setCornerRadius(8);
            pressBg.setColor(Color.DKGRAY);

            //通过代码得方式创建一个selector

            StateListDrawable selectorBg = new StateListDrawable();
            selectorBg.addState(new int[]{android.R.attr.state_pressed}, pressBg);
            selectorBg.addState(new int[]{}, normalBg);
            tv.setBackgroundDrawable(selectorBg);

            //设置tv可以点击
            tv.setClickable(true);
            fl.addView(tv);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), data, Toast.LENGTH_SHORT).show();
                }
            });
        }

        sv.addView(fl);
        return sv;
    }
}