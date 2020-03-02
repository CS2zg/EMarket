package com.example.e_market.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.R;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.User;
import com.example.e_market.fragments.CartFragment;
import com.example.e_market.tools.AccountUtils;
import com.loopj.android.image.SmartImageView;
/*
* 本类为可复用商品详情展示页
* 所有途径需要展示商品，都将有本Activity渲染
* */
public class GoodsActivity extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;
    private User user;
    private AccountUtils accountUtils;
    //商品类
    private GoodsBean goodsBean;
    //商品组件
    private SmartImageView goods_img;
    private TextView goods_price;
    private TextView goods_name;
    //其他组件
    private ImageView go_back;
    private TextView add_cart;
    private TextView go_buy;
    private Button go_shop;
    private Button add_collections;
    private Button go_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        init();//初始化组件和相关数据
        initEvent();//初始化监听事件
        setGoodsInfo();//设置商品信息
        //进入此页面，即会被记为足迹
        //只有登录后才会被记为足迹
        if(user!=null){
            accountUtils.updateRecord(user.getAccount(),goodsBean);
        }
    }
    @Override
    public void onResume() {
        if(user==null){
            user=accountUtils.getLastAccountInfo();//重新获取用户信息
        }
        super.onResume();
    }
    //初始化
    private void init(){
        intent=getIntent();
        accountUtils=new AccountUtils(GoodsActivity.this);//实例化账号工具
        user=accountUtils.getLastAccountInfo();//获取用户信息
        //Log.d("GoodsActivity","username"+user.getAccount());

        goods_img=(SmartImageView)findViewById(R.id.goods_img);
        goods_img.setMinimumHeight(goods_img.getMeasuredWidth());
        goods_price=(TextView)findViewById(R.id.goods_price);
        goods_name=(TextView)findViewById(R.id.goods_name);
        //其他组件
        go_back=(ImageView)findViewById(R.id.go_back);
        add_cart=(TextView) findViewById(R.id.add_cart);
        go_buy=(TextView)findViewById(R.id.go_buy);
        go_shop=(Button) findViewById(R.id.go_shop);
        add_collections=(Button) findViewById(R.id.add_collections);
        go_cart=(Button) findViewById(R.id.go_cart);

        //获取从activity传递的数据，商品实例
        goodsBean =(GoodsBean) intent.getSerializableExtra("goods");
    }
    //初始化事件
    private void initEvent(){
        go_back.setOnClickListener(this);
        add_cart.setOnClickListener(this);
        go_buy.setOnClickListener(this);
        go_shop.setOnClickListener(this);
        add_collections.setOnClickListener(this);
        go_cart.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                finish();
                break;
            case R.id.add_cart:
                if(user==null){
                    //未登录状态点击，进入登录界面
                    Toast.makeText(GoodsActivity.this,"您未登录呢" , Toast.LENGTH_SHORT).show();
                    intent.setClass(GoodsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    accountUtils.addCart(user.getAccount(), goodsBean);
                    Toast.makeText(this, "已加入购物车", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_buy:
                Toast.makeText(this,"(　o=^•ェ•)o　┏━┓现在还不能购买哦" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.go_shop:
                Toast.makeText(this,"_〆(´Д｀ )不能进店呢" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_collections:
                if(user==null){
                    //未登录状态点击，进入登录界面
                    Toast.makeText(GoodsActivity.this,"您未登录呢" , Toast.LENGTH_SHORT).show();
                    intent.setClass(GoodsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    //把商品加入收藏列表
                    add_collections.setBackgroundResource(R.drawable.fond_red);
                    accountUtils.updateCollections(user.getAccount(), goodsBean);
                    Toast.makeText(this, "商品已加入我的收藏", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_cart:
                //跳转到购物车
                intent.setClass(this, CartFragment.class);
                startActivity(intent);
                break;
        }
    }
    //设置商品信息
    private void setGoodsInfo(){
        goods_img.setImageUrl(goodsBean.getG_src().replace(" ","%20"),R.drawable.error_img);//图片
        Log.d("GoodsActivity","src"+goodsBean.getG_src());
        goods_price.setText(String.valueOf(goodsBean.getG_price()));//价格
        goods_name.setText(goodsBean.getG_name());//名字
    }
}
