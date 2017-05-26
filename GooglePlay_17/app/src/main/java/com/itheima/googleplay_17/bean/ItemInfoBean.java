package com.itheima.googleplay_17.bean;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 08:45
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 11:15:19 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${TODO}
 */
public class ItemInfoBean {
    public int    id;//应用的id
    public String des;//应用的描述
    public String packageName;//应用的包名
    public float  stars;//应用的评分
    public String iconUrl;//应用的图标地址
    public String name;//应用的名字
    public String downloadUrl;//应用的下载地址
    public long   size;//应用的大小

    /*--------------- 详情里面额外添加的字段 ---------------*/
    public String                 author;// 黑马程序员
    public String                 date;// 2015-06-10
    public String                 downloadNum;// 40万+
    public String                 version;//     1.1.0605.0
    public List<ItemInfoSafeBean> safe;//      Array
    public List<String>           screen;//Array

    public class ItemInfoSafeBean {
        public String safeDes;// 已通过安智市场安全检测，请放心使用
        public int    safeDesColor;//	0
        public String safeDesUrl;//	app/com.itheima.www/safeDesUrl0.jpg
        public String safeUrl;// app/com.itheima.www/safeIcon0.jpg
    }

    @Override
    public String toString() {
        return "ItemInfoBean{" +
                "id=" + id +
                ", des='" + des + '\'' +
                ", packageName='" + packageName + '\'' +
                ", stars=" + stars +
                ", iconUrl='" + iconUrl + '\'' +
                ", name='" + name + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", size=" + size +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", version='" + version + '\'' +
                ", safe=" + safe +
                ", screen=" + screen +
                '}';
    }
}
