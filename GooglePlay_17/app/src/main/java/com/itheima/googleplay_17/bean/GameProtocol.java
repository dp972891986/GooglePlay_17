package com.itheima.googleplay_17.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_17.base.BaseProtocol;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 09:00
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 09:04:40 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class GameProtocol extends BaseProtocol<List<ItemInfoBean>> {
    @Override
    public String getInterfaceKey() {
        return "game";
    }

    @Override
    public List<ItemInfoBean> parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString, new TypeToken<List<ItemInfoBean>>() {
        }.getType());
    }
}
