package com.example.e_market.beans;

import java.io.Serializable;
import java.util.ArrayList;

/*
 *   用户类
 */
public class User implements Serializable {
    private String username;//用户名
    private String account;//账号
    private String password;//密码
    private String headimg;//头像，暂时不做头像上传，所有头像为初始头像
    private String address;//收货地址

    private ArrayList<GoodsBean> record;//足迹列表
    private ArrayList<GoodsBean> collection;//收藏列表
    private CartBean cart;//购物车

    //三参构造函数
    //主要为了注册，注册时只需要输入账号，密码，用户名即可，其他信息待以后完善
    public User(String username,String account,String password){
        this.username=username;
        this.account=account;
        this.password=password;
        this.address="";
        this.headimg="";
        this.record=null;
        this.cart=null;
        this.collection=null;
    }
    //用户名、账号、密码、地址 四参构造函数
    public User(String username,String account,String password,String address){
        this.username=username;
        this.account=account;
        this.password=password;
        this.address=address;
        this.headimg="";
        this.record=null;
        this.cart=null;
        this.collection=null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRecord(ArrayList<GoodsBean> record) {
        this.record = record;
    }

    public void setCollection(ArrayList<GoodsBean> collection) {
        this.collection = collection;
    }

    public void setCart(CartBean cart) {
        this.cart = cart;
    }

    public String getUsername() {
        return username;
    }

    public String getHeadimg() {
        return headimg;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<GoodsBean> getRecord() {
        return record;
    }

    public ArrayList<GoodsBean> getCollection() {
        return collection;
    }

    public CartBean getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "账号："+this.getAccount()+" 密码："+this.getPassword();
    }
}
