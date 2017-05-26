package com.itheima.googleplay_17.protocol;

import com.itheima.googleplay_17.base.BaseProtocol;
import com.itheima.googleplay_17.bean.CategoryInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     伍碧林
 * 创建时间   2015/12/31 15:06
 * 描述	      ${TODO}
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2015-12-31 15:32:29 +0800 (星期四, 31 十二月 2015) $
 * 更新描述   ${TODO}
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>> {
    @Override
    public String getInterfaceKey() {
        return "category";
    }

    @Override
    public List<CategoryInfoBean> parseJsonString(String resultJsonString) {
        //现在展示是listview-->dateSet.size==Item的条目总数 12条
        /**
         1.bean解析
         2.泛型解析
         3.结点解析-->android sdk自带解析方式解析
         */

        try {
            List<CategoryInfoBean> categoryInfoBeans = new ArrayList<>();

            JSONArray rootJsonArray = new JSONArray(resultJsonString);
            //遍历集合
            for (int i = 0; i < rootJsonArray.length(); i++) {
                JSONObject itemJsonObject = rootJsonArray.getJSONObject(i);
                String title = itemJsonObject.getString("title");
                CategoryInfoBean titleBean = new CategoryInfoBean();
                titleBean.title = title;
                titleBean.isTitle = true;

                //加入集合中
                categoryInfoBeans.add(titleBean);

                JSONArray infosJsonArr = itemJsonObject.getJSONArray("infos");
                for (int j = 0; j < infosJsonArr.length(); j++) {
                    JSONObject infoJsonObjecct = infosJsonArr.getJSONObject(j);

                    String name1 = infoJsonObjecct.getString("name1");
                    String name2 = infoJsonObjecct.getString("name2");
                    String name3 = infoJsonObjecct.getString("name3");
                    String url1 = infoJsonObjecct.getString("url1");
                    String url2 = infoJsonObjecct.getString("url2");
                    String url3 = infoJsonObjecct.getString("url3");

                    CategoryInfoBean infoBean = new CategoryInfoBean();
                    infoBean.name1 = name1;
                    infoBean.name2 = name2;
                    infoBean.name3 = name3;
                    infoBean.url1 = url1;
                    infoBean.url2 = url2;
                    infoBean.url3 = url3;

                    categoryInfoBeans.add(infoBean);


                }
            }

            return categoryInfoBeans;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
