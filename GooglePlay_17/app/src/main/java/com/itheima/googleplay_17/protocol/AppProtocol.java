package com.itheima.googleplay_17.protocol;

import com.itheima.googleplay_17.base.BaseProtocol;
import com.itheima.googleplay_17.bean.ItemInfoBean;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 08:44
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 15:25:41 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class AppProtocol extends BaseProtocol<List<ItemInfoBean>> {
    @Override
    public String getInterfaceKey() {
        return "app";
    }

/*    @Override
    public List<ItemInfoBean> parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        //jsonString-->bean-->bean解析
        //jsonString-->List/map-->泛型解析
        return gson.fromJson(resultJsonString, new TypeToken<List<ItemInfoBean>>() {
        }.getType());
    }*/
}
