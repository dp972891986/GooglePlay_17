package com.itheima.googleplay_17.manager;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 18:15
 * 描述	     封装/存放/组合 和下载相关的参数
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 11:26:23 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class DownLoadInfo {

    public String name;//下载的具体路径
    public String savePath;//保存的具体路径
    public long   max;//文件的最大长度
    public int curState = DownLoadManager.STATE_UNDOWNLOAD;//当前的状态,默认是未下载
    public String packageName;//应用程序的包名
    public long   progress;//记录最新的进度

    public Runnable downLoadTask;//下载的任务
}
