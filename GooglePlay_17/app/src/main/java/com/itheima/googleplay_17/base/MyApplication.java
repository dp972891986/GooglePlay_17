package com.itheima.googleplay_17.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 10:23
 * 描述	     全局,单例,放置一些全局的方法,属性
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-30 16:52:46 +0800 (星期三, 30 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class MyApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static long    mMainThreadId;

    private Map<String, String> mProtocolCacheMap = new HashMap<>();

    public Map<String, String> getProtocolCacheMap() {
        return mProtocolCacheMap;
    }

    /**
     * 得到上下文
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 得到主线程的handler
     */
    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * 得到主线程的线程id
     */
    public static long getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {// 程序的入口方法

		/*--------------- 创建应用里面需要用到的一些共有的属性 ---------------*/
        // 1.上下文
        mContext = getApplicationContext();

        // 2.主线程handler
        mHandler = new Handler();

        // 3.主线程的id
        mMainThreadId = android.os.Process.myTid();
        /**
         * Tid: thread
         * Pid: process
         * Uid: user
         */
        super.onCreate();
    }
}
