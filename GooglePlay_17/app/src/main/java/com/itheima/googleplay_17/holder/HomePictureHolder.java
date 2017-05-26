package com.itheima.googleplay_17.holder;

import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.BaseHolder;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.utils.UIUtils;
import com.itheima.googleplay_17.views.ChildViewPager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 09:06
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 11:24:51 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class HomePictureHolder extends BaseHolder<List<String>> implements com.itheima.googleplay_17.views.ViewPager
        .OnPageChangeListener {
    @Bind(R.id.item_home_picture_pager)
    ChildViewPager mItemHomePicturePager;

    @Bind(R.id.item_home_picture_container_indicator)
    LinearLayout mItemHomePictureContainerIndicator;
    private List<String> mPictures;

    /**
     * @return
     * @des 告知你所能提供的视图/持有的视图是啥
     */
    @Override
    public View initHolderView() {
        View rootView = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
        //找孩子
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * @param pictures
     * @des 进行数据和视图的绑定
     */
    @Override
    public void refreshHolderView(List<String> pictures) {
        //保存数据为成员变量
        mPictures = pictures;
        //data-->局部变量
        //view-->成员变量
        mItemHomePicturePager.setAdapter(new HomePictureAdapter());

        //处理indicator的内容
        for (int i = 0; i < mPictures.size(); i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            if (i == 0) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
            int width = UIUtils.dip2Px(5);
            int height = UIUtils.dip2Px(5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = UIUtils.dip2Px(5);
            params.bottomMargin = UIUtils.dip2Px(5);
            mItemHomePictureContainerIndicator.addView(ivIndicator, params);
        }

        //滑动的时候切换indicator
        mItemHomePicturePager.setOnPageChangeListener(this);

        //无限轮播
        int diff = Integer.MAX_VALUE / 2 % mPictures.size();//7
        int index = Integer.MAX_VALUE / 2 - diff;
        mItemHomePicturePager.setCurrentItem(index);
        //自动轮播
        final AutoScrollTask autoScrollTask = new AutoScrollTask();
        autoScrollTask.start();

        //按下去的时候停止轮播
        mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        autoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        autoScrollTask.start();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    class AutoScrollTask implements Runnable {
        public void start() {
            UIUtils.getHandler().postDelayed(this, 3000);
        }

        public void stop() {
            UIUtils.getHandler().removeCallbacks(this);
        }

        @Override
        public void run() {
            int currentItem = mItemHomePicturePager.getCurrentItem();
            currentItem++;
            mItemHomePicturePager.setCurrentItem(currentItem);
            start();
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        position = position % mPictures.size();


        //1.还原所有的
        //2.选中特定的
        for (int j = 0; j < mPictures.size(); j++) {
            ImageView ivIndicator = (ImageView) mItemHomePictureContainerIndicator.getChildAt(j);
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            if (position == j) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class HomePictureAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPictures != null) {
                //                return mPictures.size();
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            position = position % mPictures.size();
            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            //data
            String url = mPictures.get(position);
            //加载图片
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + url).into(iv);

            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
