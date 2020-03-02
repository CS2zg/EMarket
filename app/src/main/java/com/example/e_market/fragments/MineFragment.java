package com.example.e_market.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.MainActivity;
import com.example.e_market.R;
import com.example.e_market.activities.DetailActivity;
import com.example.e_market.activities.GoodsListActivity;
import com.example.e_market.activities.LoginActivity;
import com.example.e_market.activities.SingupActivity;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.User;
import com.example.e_market.tools.AccountUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MineFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Intent intent;
    private User user;
    private MainActivity mainActivity;//用于fragment和activity通信
    //用户栏组件
    private RoundedImageView head_img;
    private TextView user_name;
    private TextView user_account;
    private ImageView user_message;
    //我的组建栏
    private LinearLayout my_collections;
    private LinearLayout my_order;
    private LinearLayout my_wallet;
    private LinearLayout my_record;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将布局文件解析出来
        view =inflater.inflate(R.layout.fragment_mine,container,false);
        init();//初始化组件和相关数据
        initEvent();//初始化监听事件
        setUserInfo();//设置用户信息
        return view;
    }

    @Override
    public void onResume() {
        if(user==null){ }
            AccountUtils accountUtils=new AccountUtils(getActivity());
            user=accountUtils.getLastAccountInfo();//重新获取用户信息
            setUserInfo();

        super.onResume();
    }

    private void init(){
        intent = new Intent();
        //初始化用户
        //获取activity的时候不能直接new，而是需要getActivity（）再强制转换类型
        mainActivity= (MainActivity)getActivity();
        user = mainActivity.user;//获得用户实例
        if(user!=null){
            ArrayList<GoodsBean> goods_record= new AccountUtils(getActivity()).getRecord(user.getAccount());//调用数据库工具读取足迹列表
            ArrayList<GoodsBean> goods_collection= new AccountUtils(getActivity()).getCollections(user.getAccount());//调用数据库工具读取收藏列表
            user.setRecord(goods_record);
            user.setCollection(goods_collection);
        }

        //用户信息相关组件
        head_img=(RoundedImageView)view.findViewById(R.id.head_img);
        user_name=(TextView)view.findViewById(R.id.user_name);
        user_account=(TextView)view.findViewById(R.id.user_account);
        user_message=(ImageView)view.findViewById(R.id.user_message);
        //我的组件栏实例化
        my_collections=(LinearLayout)view.findViewById(R.id.my_collections);
        my_order=(LinearLayout)view.findViewById(R.id.my_order);
        my_wallet=(LinearLayout)view.findViewById(R.id.my_wallet);
        my_record=(LinearLayout)view.findViewById(R.id.my_record);



    }
    private void initEvent(){
        head_img.setOnClickListener(this);
        my_collections.setOnClickListener(this);
        my_order.setOnClickListener(this);
        my_wallet.setOnClickListener(this);
        my_record.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img:
                if(user==null){
                    //未登录状态点击，进入登录界面
                    Toast.makeText(getActivity(),"您未登录呢" , Toast.LENGTH_SHORT).show();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    //跳转到用户详情页，并将用户信息传递过去
                    intent.setClass(getActivity(), DetailActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                break;
            case R.id.my_collections:
                //收藏
                if(user==null){
                    //未登录状态点击
                    Toast.makeText(getActivity(),"您未登录呢" , Toast.LENGTH_SHORT).show();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getActivity(), GoodsListActivity.class);
                    intent.putExtra("type","collection");
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                break;
            case R.id.my_order:
                Toast.makeText(getActivity(),"(*^_^*)订单现在还不能看0" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_wallet:
                Toast.makeText(getActivity(),"(。・∀・)ノ钱包空空如也呢" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_record:
                //足迹
                if(user==null){
                    //未登录状态点击
                    Toast.makeText(getActivity(),"您未登录呢" , Toast.LENGTH_SHORT).show();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getActivity(), GoodsListActivity.class);
                    intent.putExtra("type", "record");
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                //Toast.makeText(getActivity(),user.getRecord().get(0).getG_name() , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setUserInfo(){

        if(user!=null){
            user_name.setVisibility(View.VISIBLE);
            //设置信息
            user_name.setText(user.getUsername());
            user_account.setText(user.getAccount());
            Log.d("MineFragment", "用户信息 " + user.toString());
        }else {
            Log.d("MineFragment", "没有用户信息 " );
        }
    }
}
