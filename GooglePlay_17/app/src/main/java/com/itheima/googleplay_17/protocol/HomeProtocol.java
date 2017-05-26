package com.itheima.googleplay_17.protocol;

import com.itheima.googleplay_17.base.BaseProtocol;
import com.itheima.googleplay_17.bean.HomeInfoBean;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/30 14:04
 * 描述	      负责HomeFragment里面的网络请求
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 15:25:41 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class HomeProtocol extends BaseProtocol<HomeInfoBean> {

    /**
     * @des 返回协议的协议关键字
     * @des
     */
    @Override
    public String getInterfaceKey() {
        return "home";
    }

    /**
     * @param resultJsonString
     * @return
     * @des 解析网络返回具体的内容
     */
 /*   @Override
    public HomeInfoBean parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString, HomeInfoBean.class);
    }*/
}
