package com.itheima.googleplay_17.manager;

import android.app.DownloadManager;

import com.itheima.googleplay_17.bean.ItemInfoBean;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.factory.ThreadPoolProxyFactory;
import com.itheima.googleplay_17.utils.CommonUtils;
import com.itheima.googleplay_17.utils.FileUtils;
import com.itheima.googleplay_17.utils.HttpUtils;
import com.itheima.googleplay_17.utils.IOUtils;
import com.itheima.googleplay_17.utils.UIUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 18:12
 * 描述	      谷歌市场里面的下载管理器,负责下载模块,封装所有和下载相关的逻辑
 * 描述	      1.需要`时刻记录`当前的状态
 * 描述	      2.根据传递过来的ItemInfoBean信息返回一个DownLoadInfo对象
 * 描述	      3.当DownLoadInfo里面的状态发生改变的时候,发布消息-->被观察者
 * <p/>
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 14:45:31 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class DownLoadManager {
    private static DownLoadManager instance;

    public static final int STATE_UNDOWNLOAD      = 0;//未下载
    public static final int STATE_DOWNLOADING     = 1;//下载中
    public static final int STATE_PAUSEDOWNLOAD   = 2;//暂停下载
    public static final int STATE_WAITINGDOWNLOAD = 3;//等待下载
    public static final int STATE_DOWNLOADFAILED  = 4;//下载失败
    public static final int STATE_DOWNLOADED      = 5;//下载完成
    public static final int STATE_INSTALLED       = 6;//已安装

    /**
     * 保存所有的,用户点击下载按钮后,开始下载的一些任务
     */
    private Map<String, DownLoadInfo> mCacheDownLoadInfoMap = new HashMap<>();

    private DownLoadManager() {
    }

    public static DownLoadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownLoadManager();
                }
            }
        }
        return instance;
    }

    /**
     * @param downLoadInfo
     * @des 触发加载
     * @called 用户点击了下载按钮的时候
     */
    public void downLoad(DownLoadInfo downLoadInfo) {
        //保存到集合中
        mCacheDownLoadInfoMap.put(downLoadInfo.packageName, downLoadInfo);

        /*############### 当前状态:未下载 ###############*/
        downLoadInfo.curState = STATE_UNDOWNLOAD;

        //downLoadInfo里面的状态发生改变了.通知所有的观察者
        notifyObservers(downLoadInfo);
        /*#######################################*/


        /*############### 当前状态:等待状态 ###############*/
        downLoadInfo.curState = STATE_WAITINGDOWNLOAD;

        //downLoadInfo里面的状态发生改变了.通知所有的观察者
        notifyObservers(downLoadInfo);
        /*#######################################*/
        /**
         预先把状态设置为等待状态
         1.假如任务立马执行-->立马切换到-->下载中的状态
         2.假如任务没有执行-->保持预先设置的等待状态
         */

        //异步下载
        DownLoadTask downLoadTask = new DownLoadTask(downLoadInfo);

        //downLoadInfo中记录对应的下载任务
        downLoadInfo.downLoadTask = downLoadTask;

        ThreadPoolProxyFactory.createDownLoadThreadPoolProxy().execute(downLoadTask);
    }

    /**
     * @param downLoadInfo
     * @des 暂停下载
     * @called 如果现在正在下载, 用户点击了下载按钮
     */
    public void pauseDownLoad(DownLoadInfo downLoadInfo) {
        
        /*############### 当前状态:暂停下载 ###############*/
        downLoadInfo.curState = STATE_PAUSEDOWNLOAD;

        //downLoadInfo里面的状态发生改变了.通知所有的观察者
        notifyObservers(downLoadInfo);
        /*#######################################*/
    }

    /**
     * @param downLoadInfo
     * @des 取消下载
     * @called 当前任务在缓存队列中的时候, 用户点击了下载按钮的时候
     */
    public void cancelDownLoad(DownLoadInfo downLoadInfo) {
        /*############### 当前状态:未下载 ###############*/
        downLoadInfo.curState = STATE_UNDOWNLOAD;

        //downLoadInfo里面的状态发生改变了.通知所有的观察者
        notifyObservers(downLoadInfo);
        /*#######################################*/
        ThreadPoolProxyFactory.createDownLoadThreadPoolProxy().remove(downLoadInfo.downLoadTask);
    }


    class DownLoadTask implements Runnable {
        private final DownLoadInfo downLoadInfo;

        public DownLoadTask(DownLoadInfo downLoadInfo) {
            this.downLoadInfo = downLoadInfo;
        }

        @Override
        public void run() {
            /*############### 当前状态:下载中 ###############*/
            downLoadInfo.curState = STATE_DOWNLOADING;

            //downLoadInfo里面的状态发生改变了.通知所有的观察者
            notifyObservers(downLoadInfo);
            /*#######################################*/
            long initRange = 0;
            File saveApk = new File(downLoadInfo.savePath);
            //等于部分apk的大小
            if (saveApk.exists()) {
                initRange = saveApk.length();
            }

            //②:处理上一次的ui进度
            downLoadInfo.progress = initRange;


            InputStream in = null;
            FileOutputStream out = null;
            try {
                //真正的开始下载数据
                //发起请求
                //1.创建okHttpClient对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //http://localhost:8080/GooglePlayServer/download?
                //name=app/com.itheima.www/com.itheima.www.apk & range=0


                String url = Constants.URLS.DOWNLOADURL;

                //组装参数
                Map<String, Object> params = new HashMap<>();
                params.put("name", downLoadInfo.name);
                params.put("range", initRange + "");//①:发起请求的时候参数变化

                url = url + HttpUtils.getUrlParamsByMap(params);

                //2.创建请求
                Request request = new Request.Builder().get().url(url).build();

                //3.发起请求
                Response response = okHttpClient.newCall(request).execute();

                if (response.isSuccessful()) {
                    boolean isPause;
                    try {
                        in = response.body().byteStream();
                        //InputStream-->File
                        //放到哪里-->sdcard/Android/data/包目录/apk
                        //文件的命名-->packageName.apk
                        out = new FileOutputStream(saveApk, true);//③:写文件的时候已追加的方式进行写入

                        byte[] buffer = new byte[1024];

                        int len = -1;
                        isPause = false;
                        /**
                         at com.squareup.okhttp.internal.http.HttpConnection$
                         FixedLengthSource.read(HttpConnection.java:421)
                         01-03 14:32:39.700 30646-30764/com.itheima.googleplay_17 W/System.err:
                         at okio.RealBufferedSource$1.read(RealBufferedSource.java:371)
                         */
                        while ((len = in.read(buffer)) != -1) {
                            if (downLoadInfo.curState == STATE_PAUSEDOWNLOAD) {//说明用户点击了暂停操作
                                //停止文件的写操作
                                isPause = true;
                                break;
                            }

                            /*############### 当前状态:下载中 ###############*/
                            downLoadInfo.curState = STATE_DOWNLOADING;
                            downLoadInfo.progress += len;
                            //downLoadInfo里面的状态发生改变了.通知所有的观察者
                            notifyObservers(downLoadInfo);
                           /*#######################################*/

                            out.write(buffer, 0, len);
                            //使用okHttpClient进行文件的读写的时候加上如下代码
                            if (saveApk.length() == downLoadInfo.max) {//写完了,主动跳出while循环
                                break;
                            }
                        }
                    } finally {
                        IOUtils.close(out);
                        IOUtils.close(in);
                    }
                    if (isPause) {//说明暂停之后走到这里

                    } else {//下载完成走到这里的
                    /*############### 当前状态:下载完成 ###############*/
                        downLoadInfo.curState = STATE_DOWNLOADED;

                        //downLoadInfo里面的状态发生改变了.通知所有的观察者
                        notifyObservers(downLoadInfo);
                   /*#######################################*/
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                /*############### 当前状态:下载失败 ###############*/
                downLoadInfo.curState = STATE_DOWNLOADFAILED;

                //downLoadInfo里面的状态发生改变了.通知所有的观察者
                notifyObservers(downLoadInfo);
                /*#######################################*/
            }
        }
    }

    /**
     * @param itemInfoBean 限制条件
     * @return
     * @des 根据传递过来的ItemInfoBean信息返回一个DownLoadInfo对象
     */
    public DownLoadInfo getDownLoadInfo(ItemInfoBean itemInfoBean) {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        //赋值-->常规的赋值-->其他字段赋值
        String dir = FileUtils.getDir("apk");//sdcard/Android/data/包目录/apk
        String fileName = itemInfoBean.packageName + ".apk";
        File saveFile = new File(dir, fileName);


        downLoadInfo.max = itemInfoBean.size;
        downLoadInfo.name = itemInfoBean.downloadUrl;
        downLoadInfo.savePath = saveFile.getAbsolutePath();
        downLoadInfo.packageName = itemInfoBean.packageName;


        //赋值-->重要的赋值,我就需要的赋值-->curState-->7种情况
        //已安装
        if (CommonUtils.isInstalled(UIUtils.getContext(), downLoadInfo.packageName)) {
            downLoadInfo.curState = STATE_INSTALLED;//已安装
            return downLoadInfo;
        }

        //下载完成
        File saveApk = new File(downLoadInfo.savePath);
        if (saveApk.exists() && saveApk.length() == downLoadInfo.max) {
            downLoadInfo.curState = STATE_DOWNLOADED;//已下载
        }


        /**
         下载中
         暂停下载
         等待下载
         下载失败
         */
        //说明,itemInfoBean对应的DownLoadInfo用户肯定点击了下载按钮进行了触发下载
        if (mCacheDownLoadInfoMap.containsKey(itemInfoBean.packageName)) {
            downLoadInfo = mCacheDownLoadInfoMap.get(itemInfoBean.packageName);
            return downLoadInfo;
        }

        //未下载
        downLoadInfo.curState = STATE_UNDOWNLOAD;
        return downLoadInfo;
    }

    /*=============== 自己实现观察者设计模式,发布最新的状态(downLoadInfo对象) begin ===============*/
    //1.定义接口和接口方法
    public interface DownLoadInfoObserver {
        void onDownLoadInfoChanged(DownLoadInfo downLoadInfo);
    }

    //2.定义接口对象的集合,也就是所有的观察者
    private List<DownLoadInfoObserver> observers = new ArrayList<>();

    //3.添加观察者
    public synchronized void addObserver(DownLoadInfoObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    //4.移除观察者
    public synchronized void deleteObserver(DownLoadInfoObserver o) {
        observers.remove(o);
    }

    //5.通知所有的观察者-->发布消息
    public void notifyObservers(DownLoadInfo downLoadInfo) {
        for (DownLoadInfoObserver o : observers) {
            o.onDownLoadInfoChanged(downLoadInfo);
        }
    }
    /*=============== 自己实现观察者设计模式,发布最新的状态(downLoadInfo对象) end ===============*/
}
