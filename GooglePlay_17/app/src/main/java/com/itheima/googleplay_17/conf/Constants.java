package com.itheima.googleplay_17.conf;

import com.itheima.googleplay_17.utils.LogUtils;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 11:14
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 18:36:55 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class Constants {
    /**
     * LEVEL_ALL:打开应用程序里面所有输入的日志 7
     * LEVEL_OFF:关闭应用程序里面所有输入的日志 0
     */
    public static final int  DEBUGLEVEL   = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIME = 5 * 60 * 1000;//5分钟

    public static final class URLS {
        public static final String BASEURL    = "http://192.168.2.110:8080/GooglePlayServer/";
        public static final String IMGBASEURL = BASEURL + "image?name=";
        //http://localhost:8080/GooglePlayServer/download
        public static final String DOWNLOADURL = BASEURL + "download?";
    }
}
