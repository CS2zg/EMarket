package com.example.e_market.tools;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.e_market.beans.CartBean;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.StatusGoodsBean;
import com.example.e_market.beans.User;

import java.util.ArrayList;
import java.util.Map;

public class AccountUtils{
    private DatabaseHelper dbHelper;
    //五个数据表的名称
    private static final String TABLE_USER="user";
    private static final String TABLE_CART="cart";
    private static final String TABLE_RECORD="record";
    private static final String TABLE_COLLECTION="collection";
    private static final String TABLE_LOGININFO="login_info";
    //构造方法
    public AccountUtils(Context context){
        dbHelper=new DatabaseHelper(context);
    }
    //保存用户信息到数据库
    public boolean saveAccountInfo(User user){
        SQLiteDatabase db=dbHelper.getWritableDatabase();//获得一个可读写的数据库对象

        String sql="insert into "+TABLE_USER+"(account,name,password,headimg,address)" +
                "values(?,?,?,?,?)";
        //保存基本数据
        Object values[]=new Object[]{user.getAccount(),user.getUsername(),user.getPassword(),user.getHeadimg(),user.getAddress()};
        db.execSQL(sql,values);//执行sql
        db.close();//一定要关闭数据库
        return true;
    }
    //获得用户信息
    public  User getAccountInfo(String account){
        User user=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_USER + " where account=?" ;
        Cursor res=db.rawQuery(sql,new String[]{account});
        if(res.moveToFirst()){
            //从结果集中取出数据，并封装成User类
            String username=res.getString(res.getColumnIndex("name"));
            String password=res.getString(res.getColumnIndex("password"));
            //String headimg=res.getString(3);
            String address=res.getString(res.getColumnIndex("address"));
            user=new User(username,account,password,address);
            Log.d("AccountUtils","用户名啊啊啊啊啊"+username);
        }
        //关闭游标，数据库
        res.close();
        db.close();
        return user;
    }
    //获得最近登录的账号作为登录账号
    public User getLastAccountInfo(){
        User user=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        //String sql="SELECT * FROM "+TABLE_LOGININFO + " where account=?";
        Cursor res=db.query(TABLE_LOGININFO,null,null,null,null,null,null);
        //获取最后一条信息
        if(res.moveToLast()){
            String account=res.getString(res.getColumnIndex("account"));//获取用户账号
            Log.d("AccountUtils","账号啊啊啊啊啊"+account);
            user=this.getAccountInfo(account);//得到用户信息
        }
        db.close();
        return user;
    }
    //获取足迹列表
    public ArrayList<GoodsBean> getRecord(String account){
        ArrayList<GoodsBean> goodsList=new ArrayList<GoodsBean>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_RECORD + " where account=?" ;
        Cursor res=db.rawQuery(sql,new String[]{account});
        while (res.moveToNext()){
            GoodsBean goodsBean = new GoodsBean();
            goodsBean.setG_name(res.getString(res.getColumnIndex("c_name")));
            goodsBean.setG_price(res.getDouble(res.getColumnIndex("c_price")));
            goodsBean.setG_describe(null);
            goodsBean.setG_src(res.getString(res.getColumnIndex("c_src")));
            //加入足迹列表
            goodsList.add(goodsBean);
        }
        db.close();
        return goodsList;

    }
    //获取收藏列表
    public ArrayList<GoodsBean> getCollections(String account){
        ArrayList<GoodsBean> goodsList=new ArrayList<GoodsBean>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_COLLECTION + " where account=?" ;
        Cursor res=db.rawQuery(sql,new String[]{account});
        while (res.moveToNext()){
            GoodsBean goodsBean = new GoodsBean();
            goodsBean.setG_name(res.getString(res.getColumnIndex("c_name")));
            goodsBean.setG_price(res.getDouble(res.getColumnIndex("c_price")));
            goodsBean.setG_describe(null);
            goodsBean.setG_src(res.getString(res.getColumnIndex("c_src")));
            goodsBean.setG_num(1);
            //加入收藏列表
            goodsList.add(goodsBean);
        }
        db.close();
        return goodsList;

    }
    //获取购物车
    public CartBean getCart(String account){
        ArrayList<StatusGoodsBean> goodsList=new ArrayList<StatusGoodsBean>();//含状态商品类
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CART + " where account=?" ;
        Cursor res=db.rawQuery(sql,new String[]{account});
        while (res.moveToNext()){
            GoodsBean goodsBean = new GoodsBean(res.getString(res.getColumnIndex("c_name")),
                    res.getDouble(res.getColumnIndex("c_price")),
                    null,
                    res.getString(res.getColumnIndex("c_src")),
                    res.getInt(res.getColumnIndex("c_quantity")));
            StatusGoodsBean statusGoodsBean=new StatusGoodsBean(goodsBean);
            //加入列表
            goodsList.add(statusGoodsBean);
        }
        CartBean cartBean=new CartBean(goodsList);
        return cartBean;
    }
    //添加进购物车
    public void addCart(String account,GoodsBean goodsBean){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //用来装载要存进的数据 Map<列名，值>
        ContentValues values=new ContentValues();
        values.put("account",account);
        values.put("c_name",goodsBean.getG_name());
        values.put("c_src",goodsBean.getG_src());
        values.put("c_price",goodsBean.getG_price());
        values.put("c_quantity",goodsBean.getG_num());
        long id=db.insert(TABLE_CART,null,values);
        db.close();
    }
    public void updateCart(String account,GoodsBean goodsBean,String type){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        if(type.equals("add")){
            values.put("c_quantity",goodsBean.getG_num()+1);
        }
        else if(type.equals("sub")){
            values.put("c_quantity",goodsBean.getG_num()-1);
        }
        db.update("cart",values,"account=? and c_name=?",new String[]{account,goodsBean.getG_name()});
        db.close();
    }
    //在登录成功之时，把用户账号写入lofin_info 表中，更新记录
    public int updateLoginInfo(String account){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //用来装载要存进的数据 Map<列名，值>
        ContentValues values=new ContentValues();
        values.put("account",account);
        long id=db.insert(TABLE_LOGININFO,null,values);
        db.close();

        return (int)id;
    }
    //修改用户名
    public void updateUsername(String account,String new_name){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",new_name);
        db.update("user",values,"account=?",new String[]{account});
        db.close();
    }
    //修改用户密码
    public void updatePassword(String account , String new_pwd){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("password",new_pwd);
        db.update("user",values,"account=?",new String[]{account});
        db.close();
    }
    //保存足迹
    public void updateRecord(String account, GoodsBean goodsBean){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //用来装载要存进的数据 Map<列名，值>
        ContentValues values=new ContentValues();
        values.put("account",account);
        values.put("c_name",goodsBean.getG_name());
        values.put("c_src",goodsBean.getG_src());
        values.put("c_price",goodsBean.getG_price());
        long id=db.insert(TABLE_RECORD,null,values);
        db.close();
    }
    //保存收藏
    public void updateCollections(String account, GoodsBean goodsBean){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //用来装载要存进的数据 Map<列名，值>
        ContentValues values=new ContentValues();
        values.put("account",account);
        values.put("c_name",goodsBean.getG_name());
        values.put("c_src",goodsBean.getG_src());
        values.put("c_price",goodsBean.getG_price());
        long id=db.insert(TABLE_COLLECTION,null,values);
        db.close();
    }

    //删除足迹、收藏记录//删除购物车商品
    public void deleteData(String table,String account,String c_name){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(table,"account=? and c_name=?",new String[]{account,c_name});
        db.close();
    }


}