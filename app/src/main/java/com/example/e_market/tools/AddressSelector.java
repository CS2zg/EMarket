package com.example.e_market.tools;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import com.example.e_market.beans.ProvinceBean;
import com.example.e_market.beans.ProvinceBean1;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddressSelector {
    String tx;
    //  省
    private List<ProvinceBean1> provinceItems = new ArrayList<ProvinceBean1>();
    //  市
    private ArrayList<ArrayList<String>> cityItems = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> countyItems = new ArrayList<>();
    //解析数据
    public void parseData(Context context){
        String jsonStr = new GetJsonDataUtil().getJson(context, "province.json");//获取assets目录下的json文件数据
        //jsonStr已经读取出来了，但是Gson解析失败
        //解析json
        Gson gson =new Gson();
        java.lang.reflect.Type type =new TypeToken<List<ProvinceBean1>>(){}.getType();
        List<ProvinceBean1>provinceList=gson.fromJson(jsonStr, type);
        if(provinceList!=null){
            provinceItems=provinceList;
            Log.d("DetailActivity","省:"+provinceList.get(2).getProvinceName());
            Log.d("DetailActivity",jsonStr.substring(1,10));
        }else{
            Log.d("DetailActivity","内容是空");
        }

        //遍历省
        /*
        for(int i = 0; i <provinceList.size() ; i++) {
            ArrayList<String> cityList = new ArrayList<>();//存放市
            ArrayList<ArrayList<String>> countyList = new ArrayList<>();//存放区
            //遍历市
            for(int c = 0; c <provinceList.get(i).getCity().size() ; c++) {
                // 得到城市名称
                String cityName = provinceList.get(i).getCity().get(c).getCity_name();
                //添加城市
                cityList.add(cityName);
                ArrayList<String> areaList = new ArrayList<>();
                if (provinceList.get(i).getCity().get(c).getCounty() == null ||
                        provinceList.get(i).getCity().get(c).getCounty().size() == 0) {
                    areaList.add("");
                }else {
                    //把所有信息存进来
                    areaList.addAll(provinceList.get(i).getCity().get(c).getCounty());
                }
                countyList.add(areaList);
            }
            //添加市县数据
            cityItems.add(cityList);
            countyItems.add(countyList);

        }*/
    }
    //展示选择器
    /*
    public String showPickerView(Context context) {

        OptionsPickerView pvOptions=new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tx=provinceItems.get(options1).province_name+
                        cityItems.get(options1).get(options2)+
                        countyItems.get(options1).get(options2).get(options3);
                Log.d("DetailActivity","地区："+tx);
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(provinceItems, cityItems, countyItems);//三级选择器
        pvOptions.show();
        return tx;
    }*/
}
