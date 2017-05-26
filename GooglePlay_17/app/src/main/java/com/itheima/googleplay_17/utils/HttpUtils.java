package com.itheima.googleplay_17.utils;

import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/30 09:14
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-30 09:23:54 +0800 (星期三, 30 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class HttpUtils {
    /**
     * 传递get参数对应的map集合,返回拼接之后的字符串信息
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
