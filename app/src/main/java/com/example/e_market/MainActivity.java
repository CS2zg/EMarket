package com.example.e_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.e_market.activities.LoginActivity;
import com.example.e_market.activities.SearchActivity;
import com.example.e_market.beans.User;
import com.example.e_market.fragments.CartFragment;
import com.example.e_market.fragments.HomeFragment;
import com.example.e_market.fragments.MineFragment;
import com.example.e_market.fragments.SortFragment;
import com.example.e_market.fragments.SortTestFragment;
import com.example.e_market.tools.AccountUtils;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   //意图
    Intent intent;
    //用户
    public User user;
    //初始化4个Fragment
    private Fragment hfragment;
    private Fragment sfragment;
    private Fragment cfragment;
    private Fragment mfragment;
    //导航栏组件
    private LinearLayout nav_home;
    private LinearLayout nav_sort;
    private LinearLayout nav_cart;
    private LinearLayout nav_mine;
    private ImageView nav_home_icon;
    private ImageView nav_sort_icon;
    private ImageView nav_cart_icon;
    private ImageView nav_mine_icon;

    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //如果用户信息为空，则表明未登陆过，跳转到登录界面
        if(user==null){
            intent.setClass(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent,1);//开启登录activity
        }
        setSelect(0);//初始为fragment_home

    }

    //重写onActivityResult方法，获得其他activity的反馈值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //状态码1是登录activity
        if(resultCode==1){
            user=(User)data.getSerializableExtra("user");//取回登录信息
            Log.d("MainActivity", "我是取自登录的账号啊啊 " + user.getAccount());
            //user.toString();
        }
    }
    @Override
    public void onResume() {
        if(user==null){
            AccountUtils accountUtils=new AccountUtils(MainActivity.this);
            user=accountUtils.getLastAccountInfo();//重新获取用户信息
        }
        super.onResume();
    }
    //初始化导航栏并注册点击监听
    private void init() {
        intent=new Intent();//初始化意图
        AccountUtils au=new AccountUtils(MainActivity.this);//获得账户操作工具类实例
        user= au.getLastAccountInfo();//取得用户信息，可暂定此账号AccountInfo("201641402510")

        if(user!=null){
            Log.d("MainActivity", "我是账号啊啊啊啊 " + user.getAccount());
        }else{
            Log.d("MainActivity", "账号是空的 ");
        }

        //初始化导航栏组件
        nav_home=(LinearLayout)findViewById(R.id.navigation_home);
        nav_sort=(LinearLayout)findViewById(R.id.navigation_sort);
        nav_cart=(LinearLayout)findViewById(R.id.navigation_car);
        nav_mine=(LinearLayout)findViewById(R.id.navigation_mine);
        nav_home_icon =(ImageView)findViewById(R.id.nav_home_icon);
        nav_sort_icon =(ImageView)findViewById(R.id.nav_sort_icon);
        nav_cart_icon =(ImageView)findViewById(R.id.nav_cart_icon);
        nav_mine_icon =(ImageView)findViewById(R.id.nav_mine_icon);


        //注册监听事件
        nav_home.setOnClickListener(this);
        nav_sort.setOnClickListener(this);
        nav_cart.setOnClickListener(this);
        nav_mine.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.navigation_home:
                setSelect(0);
                updateIcons(0);
                break;
            case R.id.navigation_sort:
                setSelect(1);
                updateIcons(1);
                //Log.d("MainActivity", "我是用户信息 " + user.toString());
                break;
            case R.id.navigation_car:
                setSelect(2);
                updateIcons(2);
                break;
            case R.id.navigation_mine:
                setSelect(3);
                updateIcons(3);
                break;
            default:
                break;
        }
    }
    //隐藏Fragment
    private void hideFragment(FragmentTransaction transaction){
        if (hfragment != null) {
            transaction.hide(hfragment);
            //transaction.remove(hfragment);
        }
        if (sfragment != null) {
            transaction.hide(sfragment);
        }
        if(cfragment!=null){
            transaction.hide(cfragment);
        }
        if (mfragment != null) {
            transaction.hide(mfragment);
        }
    }
    //选择Fragment
    private void setSelect(int i){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        hideFragment(transaction);
        switch (i){
            case 0:
                if(hfragment==null){
                    hfragment=new HomeFragment();
                    transaction.add(R.id.settingcontent,hfragment);
                    //transaction.show(hfragment);
                }else {
                    transaction.show(hfragment);
                }
                break;
            case 1:
                if(sfragment==null){
                    sfragment=new SortTestFragment();//修改此处
                    transaction.add(R.id.settingcontent,sfragment);
                }else {
                    transaction.show(sfragment);
                }
                break;
            case 2:
                if(cfragment==null){
                    cfragment=new CartFragment();
                    transaction.add(R.id.settingcontent,cfragment);
                }else {
                    transaction.show(cfragment);
                }
                break;
            case 3:
                if(mfragment==null){
                    mfragment=new MineFragment();
                    transaction.add(R.id.settingcontent,mfragment);
                }else {
                    transaction.show(mfragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();//提交事务
    }
    //改变导航栏图标
    private void updateIcons(int i){
        switch(i){
            case 0:
                nav_home_icon.setImageResource(R.drawable.home_big);
                nav_sort_icon.setImageResource(R.drawable.sort);
                nav_cart_icon.setImageResource(R.drawable.cart);
                nav_mine_icon.setImageResource(R.drawable.mine);
                break;
            case 1:
                nav_home_icon.setImageResource(R.drawable.home);
                nav_sort_icon.setImageResource(R.drawable.sort_big);
                nav_cart_icon.setImageResource(R.drawable.cart);
                nav_mine_icon.setImageResource(R.drawable.mine);
                break;
            case 2:
                nav_home_icon.setImageResource(R.drawable.home);
                nav_sort_icon.setImageResource(R.drawable.sort);
                nav_cart_icon.setImageResource(R.drawable.cart_big);
                nav_mine_icon.setImageResource(R.drawable.mine);
                break;
            case 3:
                nav_home_icon.setImageResource(R.drawable.home);
                nav_sort_icon.setImageResource(R.drawable.sort);
                nav_cart_icon.setImageResource(R.drawable.cart);
                nav_mine_icon.setImageResource(R.drawable.mine_big);
                break;
            default:
                break;
        }
    }
}
