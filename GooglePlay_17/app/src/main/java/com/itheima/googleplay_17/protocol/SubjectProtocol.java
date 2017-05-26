package com.itheima.googleplay_17.protocol;

import com.itheima.googleplay_17.base.BaseProtocol;
import com.itheima.googleplay_17.bean.SubjectInfoBean;

import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 11:10
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 15:25:41 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfoBean>> {
    @Override
    public String getInterfaceKey() {
        return "subject";
    }

   /* @Override
    public List<SubjectInfoBean> parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(resultJsonString, new TypeToken<List<SubjectInfoBean>>() {
        }.getType());
    }*/
}
