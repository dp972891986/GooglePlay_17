package com.itheima.googleplay_17.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.itheima.googleplay_17.conf.Constants;
import com.itheima.googleplay_17.utils.FileUtils;
import com.itheima.googleplay_17.utils.HttpUtils;
import com.itheima.googleplay_17.utils.IOUtils;
import com.itheima.googleplay_17.utils.LogUtils;
import com.itheima.googleplay_17.utils.UIUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/30 14:10
 * 描述	      对谷歌市场里面所有的网络请求做封装
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-03 15:25:41 +0800 (星期日, 03 一月 2016) $
 * 更新描述   ${TODO}
 */
public abstract class BaseProtocol<T> {


    /**
     * @param index 分页请求的索引
     * @return
     * @throws IOException
     */
    public T loadData(int index) throws IOException {

        //1.从内存
        MyApplication app = (MyApplication) UIUtils.getContext();
        Map<String, String> protocolCacheMap = app.getProtocolCacheMap();

        //得到缓存的关键字
        String key = generateKey(index);

        T t = null;
        if (protocolCacheMap.containsKey(key)) {
            LogUtils.i("BaseProtocol", "从内存里面加载数据-->" + key);
            String memJsonString = protocolCacheMap.get(key);
            t = parseJsonString(memJsonString);

            return t;
        }

        t = loadDataFromLocal(key);
        if (t != null) {//本地有数据,在返回
            LogUtils.i("BaseProtocol", "从本地加载数据-->" + getCacheFile(key).getAbsolutePath());
            return t;
        }

        t = lodateDataFromNet(index);
        if (t != null) {
            return t;
        }
        return null;
    }

    /**
     * 从本地加载协议内容
     *
     * @param key
     * @return
     */
    private T loadDataFromLocal(String key) {
        //文件是否存在
        File cacheFile = getCacheFile(key);

        if (cacheFile.exists()) {//有缓存
            //读取第一行,判断是否过期
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String insertTimeStr = reader.readLine();
                long insertTime = Long.parseLong(insertTimeStr);

                if (System.currentTimeMillis() - insertTime < Constants.PROTOCOLTIME) {
                    //读取缓存内容
                    String cacheJsonString = reader.readLine();

                    //存内存
                    MyApplication app = (MyApplication) UIUtils.getContext();
                    app.getProtocolCacheMap().put(key, cacheJsonString);

                    //解析返回
                    return parseJsonString(cacheJsonString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }

        return null;
    }

    /**
     * 得到缓存的文件
     *
     * @param key
     * @return
     */
    @NonNull
    private File getCacheFile(String key) {
        String dir = FileUtils.getDir("json");//sdcard/Android/data/包目录/json
        return new File(dir, key);
    }

    /**
     * 缓存的关键字 interface+"."+index
     *
     * @param index
     * @return
     */
    private String generateKey(int index) {
        Map<String, Object> parmasHashMap = getParmasHashMap(index);
        //tab-->index,0 index,20 index,40
        //detail-->pacakgeName,包名1 pacakgeName,包名2 pacakgeName,包名3

        for (Map.Entry<String, Object> info : parmasHashMap.entrySet()) {
            String key = info.getKey();//"index","packageName"
            Object value = info.getValue();//0  20 40   包名1 包名2 包名1
            return getInterfaceKey() + "." + value;
        }
        return "";
    }

    /**
     * 从网络加载数据
     *
     * @param index
     * @return
     * @throws IOException
     */
    private T lodateDataFromNet(int index) throws IOException {
    /*--------------- 1.得到网络请求的jsonString ---------------*/
        //通过okHttp发起网络请求
        //        创建okHttpClient实例对象
        OkHttpClient okHttpClient = new OkHttpClient();


        //http://localhost:8080/GooglePlayServer/home?index=0
        String url = Constants.URLS.BASEURL + getInterfaceKey() + "?";

        //参数
        Map<String, Object> parmasMap = getParmasHashMap(index);
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(parmasMap);

        //拼接之后的结果
        url = url + urlParamsByMap;

        //创建一个请求对象
        Request request = new Request.Builder().get().url(url).build();

        //发起请求
        Response response = okHttpClient.newCall(request).execute();

        if (response.isSuccessful()) {//响应成功
            String resultJsonString = response.body().string();

            /*--------------- 2.json解析的过程 ---------------*/
            //jsonString-->bean
            //jsonString-->List Map
            //            Gson gson = new Gson();
            //            HomeInfoBean homeInfoBean = gson.fromJson(resultJsonString, HomeInfoBean.class);
            /*--------------- 存内存,存本地 ---------------*/
            LogUtils.i("BaseProtocol", "写入缓存到内存");
            MyApplication app = (MyApplication) UIUtils.getContext();
            String key = generateKey(index);
            app.getProtocolCacheMap().put(key, resultJsonString);

            File cacheFile = getCacheFile(key);
            BufferedWriter writer = null;
            try {
                LogUtils.i("BaseProtocol", "写入缓存到本地");
                writer = new BufferedWriter(new FileWriter(cacheFile));
                //写入第一行-->插入时间
                writer.write(System.currentTimeMillis() + "");
                //换行
                writer.newLine();

                /**
                 Reader reader = new InputStreamReader(instream, charset);
                 CharArrayBuffer buffer = new CharArrayBuffer(i);
                 try {
                 char[] tmp = new char[1024];
                 int l;
                 while((l = reader.read(tmp)) != -1) {
                 buffer.append(tmp, 0, l);
                 }
                 } finally {
                 reader.close();
                 }

                 */
                if (resultJsonString.contains("\r\n")) {
                    resultJsonString = resultJsonString.replace("\r\n", "");
                }
                if (resultJsonString.contains(" ")) {
                    resultJsonString = resultJsonString.replace(" ", "");
                }


                //写入第二行
                writer.write(resultJsonString);
            } finally {
                IOUtils.close(writer);
            }


            T t = parseJsonString(resultJsonString);

            return t;
        }
        return null;
    }

    /**
     * @return
     * @des 请求参数的封装
     */
    public Map<String, Object> getParmasHashMap(int index) {
        Map<String, Object> defaultParamsHashMap = new HashMap<>();
        defaultParamsHashMap.put("index", index);
        return defaultParamsHashMap;//默认传递index参数
    }


    /**
     * @des 返回协议的协议关键字
     * @des
     */
    public abstract String getInterfaceKey();

    /**
     * @param resultJsonString
     * @return
     * @des 解析网络返回具体的内容
     */
    public T parseJsonString(String resultJsonString) {
        Gson gson = new Gson();
    /*    Class c = this.getClass();// 得到子类的类型
        Type type = c.getGenericSuperclass();//得到子类传递的完整参数化类型
        ParameterizedType pType = (ParameterizedType) type;//还需要强转成参数化类型
        Type[] types = pType.getActualTypeArguments();//得到真实的类型参数们
        Type actuaType = types[0];//得到第一个类型参数*/

        Type actualType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Object o = gson.fromJson(resultJsonString, actualType);

        return (T) o;
    }
}
