package com.itheima.googleplay_17.bean;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 15:11
 * 描述	     此定义的结构和协议返回的JsonString的结构不一致
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 15:42:34 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class CategoryInfoBean {

    public String name1;//休闲
    public String name2;//棋牌
    public String name3;//益智
    public String url1;//image/category_game_0.jpg
    public String url2;//image/category_game_1.jpg
    public String url3;//image/category_game_2.jpg

    public String title;//游戏

    //添加一个属性
    public boolean isTitle;//标明是否是title

    @Override
    public String toString() {
        return "CategoryInfoBean{" +
                "name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                ", url3='" + url3 + '\'' +
                ", title='" + title + '\'' +
                ", isTitle=" + isTitle +
                '}';
    }
}
