package com.itheima.googleplay_17.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_17.adapter.ItemAdapter;
import com.itheima.googleplay_17.base.BaseFragment;
import com.itheima.googleplay_17.base.LoadingPager;
import com.itheima.googleplay_17.bean.HomeInfoBean;
import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.holder.HomePictureHolder;
import com.itheima.googleplay_17.holder.ItemHolder;
import com.itheima.googleplay_17.manager.DownLoadInfo;
import com.itheima.googleplay_17.manager.DownLoadManager;
import com.itheima.googleplay_17.protocol.HomeProtocol;

import java.io.IOException;
import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 15:34
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 14:53:34 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class HomeFragment extends BaseFragment {

    private List<ItemInfoBean> mItemInfoBeans;
    private List<String>       mPictures;
    private HomeProtocol       mProtocol;
    private HomeAdapter        mAdapter;

    /**
     * @return
     * @des 真正的在子线程中加载数据, 得到数据
     * @called 触发加载数据, 外界希望通过loadingPager加载数据的时候, 调用这个方法
     */
    @Override
    public LoadingPager.LoadedResultState initData() {
       /* try {

            //通过okHttp发起网络请求
            //        创建okHttpClient实例对象
            OkHttpClient okHttpClient = new OkHttpClient();


            //http://localhost:8080/GooglePlayServer/home?index=0
            String url = Constants.URLS.BASEURL + "home?";

            //参数
            Map<String, Object> parmasMap = new HashMap<>();
            parmasMap.put("index", "" + 0);
            String urlParamsByMap = HttpUtils.getUrlParamsByMap(parmasMap);

            //拼接之后的结果
            url = url + urlParamsByMap;

            //创建一个请求对象
            Request request = new Request.Builder().get().url(url).build();

            //发起请求
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {//响应成功
                String resultJsonString = response.body().string();

                Gson gson = new Gson();
                HomeInfoBean homeInfoBean = gson.fromJson(resultJsonString, HomeInfoBean.class);

                LoadingPager.LoadedResultState state = checkResData(homeInfoBean);
                if (state != LoadingPager.LoadedResultState.SUCCESS) {//homeInfoBean不成功-->homeInfoBean==null
                    return state;
                }
                //校验homeInfoBean.list
                state = checkResData(homeInfoBean.list);
                if (state != LoadingPager.LoadedResultState.SUCCESS) {//homeInfoBean.list不成功-->homeInfoBean.list.size==0
                    return state;
                }

                //如果走到这里来了.说明一切顺利
                //保存数据到成员变量
                mItemInfoBeans = homeInfoBean.list;
                mPictures = homeInfoBean.picture;

                return state;
            } else {//响应失败
                return LoadingPager.LoadedResultState.ERROR;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return LoadingPager.LoadedResultState.ERROR;
        }*/
        /*--------------- 协议简单封装以后 ---------------*/
        mProtocol = new HomeProtocol();
        try {
            HomeInfoBean homeInfoBean = mProtocol.loadData(0);
            LoadingPager.LoadedResultState state = checkResData(homeInfoBean);
            if (state != LoadingPager.LoadedResultState.SUCCESS) {//homeInfoBean不成功-->homeInfoBean==null
                return state;
            }
            //校验homeInfoBean.list
            state = checkResData(homeInfoBean.list);
            if (state != LoadingPager.LoadedResultState.SUCCESS) {//homeInfoBean.list不成功-->homeInfoBean.list.size==0
                return state;
            }

            //如果走到这里来了.说明一切顺利
            //保存数据到成员变量
            mItemInfoBeans = homeInfoBean.list;
            mPictures = homeInfoBean.picture;

            return state;
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
        ListView listView = ListViewFactory.createListView();

        //在设置adapter之前,添加一个轮播图
        HomePictureHolder homePictureHolder = new HomePictureHolder();
        listView.addHeaderView(homePictureHolder.mHolderView);//view经过数据绑定的一个View
        //传递数据给HomePictureHolder,让其刷新
        homePictureHolder.setDataAndRefreshHolderView(mPictures);


        mAdapter = new HomeAdapter(listView, mItemInfoBeans);
        listView.setAdapter(mAdapter);
        return listView;
    }

    class HomeAdapter extends ItemAdapter {
        public HomeAdapter(AbsListView absListView, List datas) {
            super(absListView, datas);
        }

        /**
         * @return
         * @des 开始在子线程中加载更多的数据
         * @called listView滑动底部的时候
         */
        @Override
        public List<ItemInfoBean> initLoadMoreData() throws Exception {
            SystemClock.sleep(3000);
            HomeInfoBean homeInfoBean = mProtocol.loadData(mItemInfoBeans.size());
            if (homeInfoBean != null) {
                return homeInfoBean.list;
            }
            return null;
        }


    }


    /*class HomeAdapter extends MyBaseAdapter<String> {

        public HomeAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            *//*--------------- 决定根视图 ---------------*//*
            ItemHolder homeHolder = null;
            if (convertView == null) {
                homeHolder = new ItemHolder();
            } else {
                homeHolder = (ItemHolder) convertView.getTag();
            }
            *//*--------------- 接收数据,绑定数据 ---------------*//*
            homeHolder.setDataAndRefreshHolderView(mDatas.get(position));

            return homeHolder.mHolderView;
        }
    }*/

    /*class HomeAdapter extends MyBaseAdapter<String> {

        public HomeAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            *//*--------------- 得到根布局 ---------------*//*
            ViewHolder holder = null;
            if (convertView == null) {
                //初始化holder
                holder = new ViewHolder();

                //初始化根视图
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_tmp, null);

                //初始化孩子对象
                holder.tvTmp1 = (TextView) convertView.findViewById(R.id.tmp_tv_1);
                holder.tvTmp2 = (TextView) convertView.findViewById(R.id.tmp_tv_2);

                //convertView去找一个类的一个对象作为holder
                convertView.setTag(holder);
            } else {
                //从convertView身上得到对应的holder
                holder = (ViewHolder) convertView.getTag();
            }
            *//*--------------- 得到数据,绑定数据 ---------------*//*
            //得到data
            String data = mDatas.get(position);
            //数据和视图的绑定
            holder.tvTmp1.setText("我是头-" + data);
            holder.tvTmp2.setText("我是尾巴-" + data);

            return convertView;
        }

        */

    /**
     * ViewHolder就是一个类,但是不是普通的类
     * 做viewHolder需要有条件
     * 1.持有根布局的孩子对象
     * 2.持有根布局-->findViewById
     *//*
        class ViewHolder {
            TextView tvTmp1;
            TextView tvTmp2;
        }
    }*/
    @Override
    public void onPause() {
        super.onPause();
        //移除所有的观察者
        if (mAdapter != null) {
            List<ItemHolder> itemHolders = mAdapter.mItemHolders;
            for (ItemHolder itemHolder : itemHolders) {
                DownLoadManager.getInstance().deleteObserver(itemHolder);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //添加所有的观察者
        //移除所有的观察者
        if (mAdapter != null) {
            List<ItemHolder> itemHolders = mAdapter.mItemHolders;
            for (ItemHolder itemHolder : itemHolders) {
                DownLoadManager.getInstance().addObserver(itemHolder);

                //根据itemInfoBean得到一个downLoadInfo信息
                DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(itemHolder.mData);
                //手动发布最新的状态
                DownLoadManager.getInstance().notifyObservers(downLoadInfo);
            }
        }
    }
}