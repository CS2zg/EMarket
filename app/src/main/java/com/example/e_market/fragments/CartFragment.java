package com.example.e_market.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_market.MainActivity;
import com.example.e_market.R;
import com.example.e_market.activities.GoodsActivity;
import com.example.e_market.activities.GoodsListActivity;
import com.example.e_market.adapters.CartAdapter;
import com.example.e_market.beans.CartBean;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.StatusGoodsBean;
import com.example.e_market.beans.User;
import com.example.e_market.tools.AccountUtils;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private View view;
    private Intent intent;
    private User user;
    private MainActivity mainActivity;//用于fragment和activity通信
    private AccountUtils accountUtils;//数据库操作工具
    private CartAdapter cartAdapter;//适配器

    public CartBean mcart;

    private ListView mlist;
    private CheckBox check_all;
    private ImageView bgImg;//购物车空时的背景图
    private TextView total_price;
    private Button but_acc;//结算按钮
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将布局文件解析出来
        view =inflater.inflate(R.layout.fragment_cart,container,false);
        //初始化控件
        init();
        //初始化购物车
        initCart();
        //
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        if(user!=null){
            mcart=accountUtils.getCart(user.getAccount());

            if(mcart.getQuality()!=0 || mcart!=null){
                mlist.setAdapter(new CartAdapter(getActivity(),user,mcart.getGoods()));//重置适配器
                cartAdapter.notifyDataSetChanged();
            }
        }

        //Log.d("CartFragment", "Resume");
        super.onResume();
    }

    //初始化控件
    public void init(){
        bgImg=(ImageView)view.findViewById(R.id.bg_cart);//初始化背景图
        mlist=(ListView) view.findViewById(R.id.cart_items);//初始化ListView
        total_price=(TextView)view.findViewById(R.id.total_price);//总价
        but_acc=(Button)view.findViewById(R.id.button_account);
        check_all=(CheckBox)view.findViewById(R.id.select_all);

        intent=new Intent();
        //获取activity的时候不能直接new，而是需要getActivity（）再强制转换类型
        mainActivity= (MainActivity)getActivity();
        accountUtils=new AccountUtils(mainActivity);
        user = mainActivity.user;//获得用户实例
        //Log.d("CartFragment", "用户信息 " + user.toString());

    }
    //
    private void initEvent(){
        check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //购物车不为空
                if (mcart.getGoods().size()!=0){
                    //全选
                    for (int i=0;i< mcart.getGoods().size();i++) {
                        mcart.getGoods().get(i).setChecked(isChecked);

                    }
                    Log.d("CartFragment", ""+mcart.getGoods().get(0).isChecked() );
                    mlist.setAdapter(new CartAdapter(getActivity(),user,mcart.getGoods()));//重置适配器
                    cartAdapter.notifyDataSetChanged();
                    total_price.setText("￥"+String.valueOf(mcart.getTotal_price()));//设置总价
                }
            }
        });
        but_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mcart=accountUtils.getCart(user.getAccount());
                mcart.setGoods(CartAdapter.c.goodsBeanArrayList);
                if(mcart.getQuality()!=0 || mcart!=null){
                    //Log.d("CartFragment", "总价："+);
                    total_price.setText("￥"+String.valueOf(mcart.getTotal_price()));//设置总价

                }
            }
        });
        //ListView中item的监听
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("CartFragment", "跳啊");
                //获取当前点击的商品
                StatusGoodsBean sgoods=(StatusGoodsBean) parent.getAdapter().getItem(position);
                GoodsBean goods=sgoods.getGoodsBean();
                //设置需要跳转的activity：GoodsActivity
                intent.setClass(getActivity(), GoodsActivity.class);
                //把商品实例传递过去
                intent.putExtra("goods",goods);
                //跳转
                startActivity(intent);
            }
        });
    }

    //初始化购物车
    public void initCart(){
        bgImg=(ImageView)view.findViewById(R.id.bg_cart);
        if(user!=null){
            mcart=accountUtils.getCart(user.getAccount());
            cartAdapter=new CartAdapter(getActivity(),user,mcart.getGoods());//初始化适配器
            if(mcart.getQuality()==0 || mcart==null){

                bgImg.setImageResource(R.drawable.bg_cart);
            }else {
                mlist.setAdapter(cartAdapter);//BaseAdapter适配器
            }
        }else {
            bgImg.setImageResource(R.drawable.bg_cart);
        }


    }
}
