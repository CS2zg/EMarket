package com.example.e_market.beans;

import java.io.Serializable;

/*
*   商品类
*/
public class GoodsBean implements Serializable {
    private String g_name;//商品名称
    private double g_price;//商品单价
    private String g_describe;//商品描述
    private String g_src;//商品图片
    private int g_num;//仅用于购物车时代表该商品被添加的个数
    //构造方法
    public GoodsBean(String g_name,double g_price,String g_describe,String g_src){
        this.g_name=g_name;
        this.g_price=g_price;
        this.g_describe=g_describe;
        this.g_src=g_src;
        this.g_num=1;//由于在添加进购物车时没有选择参数，所以默认构造时数量为1
        //后来才添加，祈求都使用该构造函数
    }
    //专属于购物车的构造方法，含商品个数
    public GoodsBean(String g_name,double g_price,String g_describe,String g_src,int g_num){
        this.g_name=g_name;
        this.g_price=g_price;
        this.g_describe=g_describe;
        this.g_src=g_src;
        this.g_num=g_num;
    }
    public GoodsBean(){
        super();
    }

    public void setG_describe(String g_describe) {
        this.g_describe = g_describe;
    }

    public String getG_describe() {
        return g_describe;
    }

    public void setG_price(double g_price) {
        this.g_price = g_price;
    }

    public double getG_price() {
        return g_price;
    }

    public void setG_src(String g_src) {
        this.g_src = g_src;
    }

    public String getG_src() {
        return g_src;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_num(int g_num) {
        this.g_num = g_num;
    }

    public int getG_num() {
        return g_num;
    }
}
