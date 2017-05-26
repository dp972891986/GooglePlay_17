package com.itheima.googleplay_17.protocol;

import com.google.gson.Gson;
import com.itheima.googleplay_17.bean.HomeInfoBean;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.utils.HttpUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/30 14:04
 * 描述	      负责HomeFragment里面的网络请求
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-30 14:21:02 +0800 (星期三, 30 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class HomeProtocolBackUp {
    /**
     * @param index 分页请求的索引
     * @return
     * @throws IOException
     */
    public HomeInfoBean loadData(int index) throws IOException {
        //通过okHttp发起网络请求
        //        创建okHttpClient实例对象
        OkHttpClient okHttpClient = new OkHttpClient();


        //http://localhost:8080/GooglePlayServer/home?index=0
        String url = Constants.URLS.BASEURL + "home?";

        //参数
        Map<String, Object> parmasMap = new HashMap<>();
        parmasMap.put("index", "" + index);
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(parmasMap);

        //拼接之后的结果
        url = url + urlParamsByMap;

        //创建一个请求对象
        Request request = new Request.Builder().get().url(url).build();

        //发起请求
        Response response = okHttpClient.newCall(request).execute();

        if (response.isSuccessful()) {//响应成功
            String resultJsonString = response.body().string();

            Gson gson = new Gson();
            HomeInfoBean homeInfoBean = gson.fromJson(resultJsonString, HomeInfoBean.class);

            return homeInfoBean;
        }
        return null;
    }

}
