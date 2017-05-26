package com.itheima.googleplay_17;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.astuetz.PagerSlidingTabStripExtends;
import com.itheima.googleplay_17.base.BaseFragment;
import com.itheima.googleplay_17.factory.FragmentFactory;
import com.itheima.googleplay_17.holder.MenuLeftHolder;
import com.itheima.googleplay_17.utils.LogUtils;
import com.itheima.googleplay_17.utils.UIUtils;

public class MainActivity extends AppCompatActivity {

    private ActionBar                   mActionBar;
    private DrawerLayout                mDrawerLayout;
    private ActionBarDrawerToggle       mToggle;
    private PagerSlidingTabStripExtends mTabs;
    private ViewPager                   mViewPager;
    private String[]                    mMainTitlteArr;
    private FrameLayout                 mMain_menu_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initView();
        initData();
    }

    private void initActionBar() {
        /**
         mActionBar.setIcon(R.mipmap.ic_launcher);// 设置应用图标
         mActionBar.setLogo(R.drawable.ic_action_call);
         mActionBar.setDisplayShowHomeEnabled(false);// 设置应用图标是否,logo和icon都看不到
         mActionBar.setDisplayUseLogoEnabled(true);// 设置是否显示Logo优先,默认是false,默认就是显示icon
         mActionBar.setDisplayShowTitleEnabled(true);// 设置菜单 标题是否可见
         //		 mActionBar.setSubtitle("SubTitle");// 设置子title部分
         */
        // 获取ActionBar
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("GooglePlay");// 设置主title部分
        mActionBar.setDisplayHomeAsUpEnabled(true);// 设置back按钮是否可见
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mTabs = (PagerSlidingTabStripExtends) findViewById(R.id.main_tabs);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);

        mMain_menu_left = (FrameLayout) findViewById(R.id.main_menu_left);
        MenuLeftHolder menuLeftHolder = new MenuLeftHolder();
        mMain_menu_left.addView(menuLeftHolder.mHolderView);
        menuLeftHolder.setDataAndRefreshHolderView(null);


        // 1.同步状态
        mToggle.syncState();

        // 2.对mDrawerLayout 设置监听
        mDrawerLayout.setDrawerListener(mToggle);
    }

    private void initData() {
        // 模拟dataset
        mMainTitlteArr = UIUtils.getStringArr(R.array.main_titles);

        // 给viewpager设置adapter
        //        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        //viewPager和PagerSlidingTabStr进行绑定
        mTabs.setViewPager(mViewPager);

        //设置ViewPager切换的监听
        final MyOnPageChangeListener listener = new MyOnPageChangeListener();
        mTabs.setOnPageChangeListener(listener);


        //手动触发选中第一页
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onPageSelected(0);
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });


    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //Fragment-->LoadingPager-->触发加载数据
            BaseFragment baseFragment = FragmentFactory.createFragment(position);
            //触发加载数据
            baseFragment.mLoadingPager.triggerLoadData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 希望就是打开或者关闭drawlayout
                mToggle.onOptionsItemSelected(item);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * PagerAdapter-->view
     * FragmentPagerAdapter--->fragment
     * FragmentStatePagerAdapter-->fragment
     */
    class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            if (mMainTitlteArr != null) {
                return mMainTitlteArr.length;
            }
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            LogUtils.sf("初始化 " + mMainTitlteArr[position]);
            Fragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        /**
         * 必须要覆写getPageTitle
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitlteArr[position];
        }
    }

    class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public MainFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            if (mMainTitlteArr != null) {
                return mMainTitlteArr.length;
            }
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            LogUtils.sf("初始化 " + mMainTitlteArr[position]);
            Fragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        /**
         * 必须要覆写getPageTitle
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mMainTitlteArr[position];
        }
    }
}
