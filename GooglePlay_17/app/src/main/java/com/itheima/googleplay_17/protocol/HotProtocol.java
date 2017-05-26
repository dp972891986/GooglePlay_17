package com.itheima.googleplay_17.protocol;

import com.itheima.googleplay_17.base.BaseProtocol;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2016/1/2 09:35
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 15:25:41 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class HotProtocol extends BaseProtocol<List<String>> {
    @Override
    public String getInterfaceKey() {
        return "hot";
    }

/*    @Override
    public List<String> parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString, new TypeToken<List<String>>() {
        }.getType());
    }*/
}
