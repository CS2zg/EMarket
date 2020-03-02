package com.example.e_market.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    //全参构造函数，必不可少
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version)
    {
        super(context, name, cursorFactory, version);
    }
    //构造方法，直接定义数据库名称，数据库查询结果集，数据库版本
    public DatabaseHelper(Context context){
        super(context,"userInfo.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        /*
        * 购物车、足迹、收藏表都是通过account归类用户
        * 商品名、商品标签、商品分类确定商品
        *
        * 注：设计时考虑不周，导致收货地址只能有一个
        *     商品主键难确定，暂时只能用商品名作主键（但愿没有重名）
        */
        String sql="create table user(" +
                "account varchar(20) primary key,name varchar(10),password varchar(10)," +
                "headimg varchar(20),address varchar(30))";
        String sql1="create table cart(account varchar(20), c_name varchar(100),c_src varchar(100),c_price double,c_quantity int)";
        String sql2="create table record(account varchar(20),c_name varchar(100),c_src varchar(100),c_price double)";
        String sql3="create table collection(account varchar(20),c_name varchar(100),c_src varchar(100),c_price double)";
        /*
        * 用户登录记录表
        * 用于登录时获取最后一条信息为登录账号
        * */
        String sql4="create table login_info(account varchar(20))";

        db.execSQL(sql);//创建用户表
        db.execSQL(sql1);//创建购物车表
        db.execSQL(sql2);//创建足迹表
        db.execSQL(sql3);//创建收藏表
        db.execSQL(sql4);//创建用户登录记录表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        System.out.println("SQLite onUpdate!");

    }
}
