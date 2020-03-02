package com.example.e_market.beans;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class ProvinceBean implements IPickerViewData {

    public String province_name;//省名
    private List<City> city;
    //内部类，市
    public static class City{
        private String city_name;
        public class county{//区、市、县
            private String county_name;
            private List<String> town;
            public List<String> getTown() {
                return town;
            }
            public void setCounty(List<String> county) {
                this.town = town;
            }
        }
        public String getCity_name() {
            return city_name;
        }



        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }


    }
    @Override
    public String getPickerViewText() {
        //返回省名
        return this.province_name;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "我是"+this.province_name+",我有"+this.getCity().size()+"个下辖";
    }
}
