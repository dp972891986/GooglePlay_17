package com.itheima.googleplay_17.protocol;

import com.itheima.googleplay_17.base.BaseProtocol;
import com.itheima.googleplay_17.bean.ItemInfoBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 10:59
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 15:25:41 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class DetailProtocol extends BaseProtocol<ItemInfoBean> {

    private String mPackageName;

    public DetailProtocol(String packageName) {
        mPackageName = packageName;
    }

    @Override
    public String getInterfaceKey() {
        return "detail";
    }

  /*  @Override
    public ItemInfoBean parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString, ItemInfoBean.class);
    }*/
    //http://localhost:8080/GooglePlayServer/detail?packageName=com.itheima.www
    //http://localhost:8080/GooglePlayServer/detail?index=

    @Override
    public Map<String, Object> getParmasHashMap(int index) {
        Map<String, Object> paramsHashMap = new HashMap<>();
        paramsHashMap.put("packageName", mPackageName);
        return paramsHashMap;
    }
}
