package com.itheima.googleplay_17.factory;

import com.itheima.googleplay_17.manager.ThreadPoolProxy;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/28 16:23
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-28 16:27:43 +0800 (星期一, 28 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class ThreadPoolProxyFactory {
    static ThreadPoolProxy mNormalThreadPoolProxy;
    static ThreadPoolProxy mDownLoadThreadPoolProxy;

    /**
     * 返回普通线程池代理
     *
     * @return
     */
    public static ThreadPoolProxy createNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5, 3000);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 返回下载线程池代理
     *
     * @return
     */
    public static ThreadPoolProxy createDownLoadThreadPoolProxy() {
        if (mDownLoadThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownLoadThreadPoolProxy == null) {
                    mDownLoadThreadPoolProxy = new ThreadPoolProxy(3, 3, 3000);
                }
            }
        }
        return mDownLoadThreadPoolProxy;
    }
}
