package com.itheima.googleplay_17.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.itheima.googleplay_17.R;
import com.itheima.googleplay_17.base.LoadingPager;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.holder.DetailBottomHolder;
import com.itheima.googleplay_17.holder.DetailDesHolder;
import com.itheima.googleplay_17.holder.DetailInfoHolder;
import com.itheima.googleplay_17.holder.DetailPicHolder;
import com.itheima.googleplay_17.holder.DetailSafeHolder;
import com.itheima.googleplay_17.manager.DownLoadInfo;
import com.itheima.googleplay_17.manager.DownLoadManager;
import com.itheima.googleplay_17.protocol.DetailProtocol;
import com.itheima.googleplay_17.utils.UIUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private String       mPackageName;
    private ItemInfoBean mItemInfoBean;

    @Bind(R.id.detail_fl_bottom)
    FrameLayout mFlBottom;
    @Bind(R.id.detail_fl_des)
    FrameLayout mFlDes;
    @Bind(R.id.detail_fl_info)
    FrameLayout mFlInfo;
    @Bind(R.id.detail_fl_pic)
    FrameLayout mFlPic;
    @Bind(R.id.detail_fl_safe)
    FrameLayout mFlSafe;
    private DetailBottomHolder mDetailBottomHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initActionBar();
        initView();
    }

    private void initView() {
        LoadingPager loadingPager = new LoadingPager(UIUtils.getContext()) {
            @Override
            public LoadedResultState initData() {
                //这个里面就是DetailActivity具体加载数据
                return DetailActivity.this.initData();
            }

            @Override
            public View initSuccessView() {
                //假设成功视图是一个textView
                View view = View.inflate(UIUtils.getContext(), R.layout.item_deital, null);
                ButterKnife.bind(DetailActivity.this, view);

                //往应用的信息部分对应FrameLayout里面加入具体内容
                DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
                mFlInfo.addView(detailInfoHolder.mHolderView);
                detailInfoHolder.setDataAndRefreshHolderView(mItemInfoBean);


                //往应用的安全部分对应FrameLayout里面加入具体内容
                DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
                mFlSafe.addView(detailSafeHolder.mHolderView);
                detailSafeHolder.setDataAndRefreshHolderView(mItemInfoBean);

                //往应用的截图部分对应FrameLayout里面加入具体内容
                DetailPicHolder detailPicHolder = new DetailPicHolder();
                mFlPic.addView(detailPicHolder.mHolderView);
                detailPicHolder.setDataAndRefreshHolderView(mItemInfoBean);


                //往应用的描述部分对应FrameLayout里面加入具体内容
                DetailDesHolder detailDesHolder = new DetailDesHolder();
                mFlDes.addView(detailDesHolder.mHolderView);
                detailDesHolder.setDataAndRefreshHolderView(mItemInfoBean);

                //往应用的下载部分对应FrameLayout里面加入具体内容
                mDetailBottomHolder = new DetailBottomHolder();
                //添加观察者到观察者集合中
                DownLoadManager.getInstance().addObserver(mDetailBottomHolder);


                mFlBottom.addView(mDetailBottomHolder.mHolderView);
                mDetailBottomHolder.setDataAndRefreshHolderView(mItemInfoBean);


                return view;
            }
        };
        //activity都是通过setContentView告知其具体展示的视图
        setContentView(loadingPager);

        //1.触发加载数据
        loadingPager.triggerLoadData();
    }

    /**
     * @return
     * @des 2.就是DetailActivity里面真正的加载数据
     */
    private LoadingPager.LoadedResultState initData() {
        DetailProtocol detailProtocol = new DetailProtocol(mPackageName);
        try {
            mItemInfoBean = detailProtocol.loadData(0);
            if (mItemInfoBean == null) {
                return LoadingPager.LoadedResultState.EMPTY;
            }
            return LoadingPager.LoadedResultState.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadedResultState.ERROR;
        }
    }

    public void init() {
        mPackageName = getIntent().getStringExtra("packageName");
    }

    private void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("GooglePlay");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*--------------- 持有的观察者,需要和Activity生命周期绑定 ---------------*/

    @Override
    protected void onPause() {
        super.onPause();
        //移除观察者
        if (mDetailBottomHolder != null) {
            DownLoadManager.getInstance().deleteObserver(mDetailBottomHolder);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加观察者
        if (mDetailBottomHolder != null) {
            DownLoadManager.getInstance().addObserver(mDetailBottomHolder);
            
            //手动发布最新状态
            DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mItemInfoBean);
            DownLoadManager.getInstance().notifyObservers(downLoadInfo);
        }
    }
    /**
     安装-->移除观察者
     完成-->添加了观察者
     1.现在的黑马程序员-->已安装
     */
}

