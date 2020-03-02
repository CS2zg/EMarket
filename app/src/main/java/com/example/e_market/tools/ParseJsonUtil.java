package com.example.e_market.tools;

import android.util.Log;

import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.SortBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ParseJsonUtil {
    //把json字符串解析成GoodsBean类，主要用于热门商品的解析
    public ArrayList<GoodsBean> parseJsonToGoods(String data,String type) {
        //Gson gson = new Gson();
        ArrayList<GoodsBean> goodsList = new ArrayList<GoodsBean>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                GoodsBean goodsBean=null;
                if(type.equals("hot")){
                     goodsBean= new GoodsBean(
                            jsonObject.optString("title"), jsonObject.getDouble("price"),
                            null, "http:"+jsonObject.getString("src"));
                }else if(type.equals("sort")){
                    String name=jsonObject.optString("title");
                    String src=jsonObject.getString("src").substring(1);//存储数据库时操作失误，只能在这里删除第一个字符
                    goodsBean= new GoodsBean(
                            name, jsonObject.getDouble("price"),
                            null, "http://134.175.242.22/zg/android"+src+"/"+name+".jpg");
                }

                goodsList.add(goodsBean);
            }
//            for (GoodsBean goods : goodsList) {
//                Log.d("HomeFragment", "name is " + goods.getG_src());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsList;
    }
    //解析sort.json，提取键，生成列表
    public ArrayList<String> getSortClassifies(String data){
        ArrayList<String> sort_classifies_list=new ArrayList<String>();
        try {

            JSONObject sortJson=new JSONObject(data);
            Iterator<String> it=sortJson.keys();
            while (it.hasNext()){
                //Log.d("SortFragment", it.next());
                sort_classifies_list.add(it.next());
            }

            //
        }catch (Exception e) {
            e.printStackTrace();
        }
        return sort_classifies_list;
    }
    //把某一分类下的item封装成SortBean类
    public ArrayList<SortBean> parseJsonToSort(String classify,String sortData) throws JSONException {
        JSONObject sortJson=new JSONObject(sortData);//解析成json对象
        ArrayList<SortBean> sortBeanList=new ArrayList<SortBean>();
        if(sortJson!=null){
            JSONObject classifyJson=sortJson.optJSONObject(classify);//得到该分类下的所有item
            Iterator<String> it=classifyJson.keys();
            //通过遍历二级标题，获取数据
            while (it.hasNext()){
                String label=it.next();

                JSONArray items=classifyJson.getJSONArray(label);

                for (int i=0;i<items.length();i++) {
                   //Log.d("SortFragment",items.get(i).toString() );
                    //构造item并设置它的属性
                    SortBean itemBean=new SortBean();
                    itemBean.setS_title(items.get(i).toString());
                    itemBean.setS_src("http://134.175.242.22/zg/android/sortIcons/"
                            +classify+"/"+label+"/"+items.get(i).toString()+".jpg");
                    itemBean.setS_classify(classify);
                    itemBean.setS_label(label);
                    if(i==0){
                        itemBean.setIsfirst(true);//标志位：是否为第一个，留待动态生成label使用
                    }else {
                        itemBean.setIsfirst(false);
                    }
                    sortBeanList.add(itemBean);
                }
            }
        }else {
            return null;
        }
        return sortBeanList;
    }
}
