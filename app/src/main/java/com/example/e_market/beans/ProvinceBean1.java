package com.example.e_market.beans;

import java.util.List;

/**
 * Auto-generated: 2019-05-31 13:11:46
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 *
 * 省级类
 */
public class ProvinceBean1 {

    private int provinceId;
    private String provinceName;
    private String provinceShortName;
    private List<City> city;//下辖市级


    /*
     *市级类
     */
    public class City {

        private int cityId;
        private String cityName;
        private String cityShortName;
        private List<County> county;//下辖县级
        /**
         * 县级类
         */
        public class County {

            private int countyId;
            private String countyName;
            private String countyShortName;
            private String town;//下辖镇级
            //get、set类
            public void setCountyId(int countyId) {
                this.countyId = countyId;
            }
            public int getCountyId() {
                return countyId;
            }

            public void setCountyName(String countyName) {
                this.countyName = countyName;
            }
            public String getCountyName() {
                return countyName;
            }

            public void setCountyShortName(String countyShortName) {
                this.countyShortName = countyShortName;
            }
            public String getCountyShortName() {
                return countyShortName;
            }

            public void setTown(String town) {
                this.town = town;
            }
            public String getTown() {
                return town;
            }
        }

        //City get、set方法
        public void setCityId(int cityId) {
            this.cityId = cityId;
        }
        public int getCityId() {
            return cityId;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
        public String getCityName() {
            return cityName;
        }

        public void setCityShortName(String cityShortName) {
            this.cityShortName = cityShortName;
        }
        public String getCityShortName() {
            return cityShortName;
        }

        public void setCounty(List<County> county) {
            this.county = county;
        }
        public List<County> getCounty() {
            return county;
        }
    }

    //get、set方法
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceShortName(String provinceShortName) {
        this.provinceShortName = provinceShortName;
    }
    public String getProvinceShortName() {
        return provinceShortName;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }
    public List<City> getCity() {
        return city;
    }
}
