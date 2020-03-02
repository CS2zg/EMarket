package com.example.e_market.beans;

import java.util.ArrayList;
import java.util.LinkedList;
/*
    购物车类
*/
public class CartBean {
    private ArrayList<StatusGoodsBean> goods;//商品清单
    private int quality;//商品数量
    private double total_price;//总价
    //构造方法
    public CartBean(ArrayList<StatusGoodsBean> goods){
        this.goods=goods;
        this.quality = calculate_quantity(goods);
        this.total_price=calculate_price(goods);
    }
    public ArrayList<StatusGoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<StatusGoodsBean> goods) {
        this.goods = goods;
    }

    public int getQuality() {
        return quality;
    }

//    public void setQuality(int quality) {
//        this.quality = quality;
//    }

//    public void setTotal_price(float total_price) {
//        this.total_price = total_price;
//    }

    public double getTotal_price() {
        return calculate_price(this.goods);
    }

    //添加购物车商品，返回商品数量
    public int addGoods(StatusGoodsBean goodsBean){
        this.goods.add(goodsBean);
        this.total_price=calculate_price(this.goods);//重置总价
        this.quality=getQuality();//重置数量
        return this.quality;
    }
    //删除购物车商品，返回商品数量
    public int delGoods(StatusGoodsBean goodsBean){
        this.goods.remove(goodsBean);
        this.total_price=calculate_price(this.goods);//重置总价
        this.quality=getQuality();//重置数量
        return this.quality;
    }
    //计算总商品数
    public int calculate_quantity(ArrayList<StatusGoodsBean> goods){
        int quan=0;
        for (StatusGoodsBean g:goods
        ) {
            quan+=g.getGoodsBean().getG_num();
        }
        return quan;
    }
    //计算总价
    public double calculate_price(ArrayList<StatusGoodsBean> goods){
        double total_price=0;
        for (StatusGoodsBean g:goods) {
            if(g.isChecked()){
                total_price+=g.getGoodsBean().getG_price()*g.getGoodsBean().getG_num();
            }
        }
        return total_price;
    }
}
