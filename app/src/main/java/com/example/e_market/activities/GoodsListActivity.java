package com.example.e_market.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.R;
import com.example.e_market.adapters.GoodsListAdapter;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.User;
import com.example.e_market.tools.AccountUtils;

import java.util.ArrayList;

public class GoodsListActivity extends AppCompatActivity {
    private ListView goods_list;
    private Intent intent;
    private User user;//用户实例
    private ArrayList<GoodsBean> goodsBeanArrayList;//商品列表
    private AccountUtils accountUtils;//账号工具
    private GoodsListAdapter goodsListAdapter;//适配器

    private ImageView comeback;//返回键
    private TextView page_name;//页面标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_list);
        init();

        initEvent();
    }
    private void init(){
        goods_list=(ListView)findViewById(R.id.goods_list);
        //页面标题
        page_name=(TextView)findViewById(R.id.page_name);
        //返回键
        comeback=(ImageView)findViewById(R.id.comeback);


        intent=getIntent();
        user=(User)intent.getSerializableExtra("user");
        accountUtils=new AccountUtils(GoodsListActivity.this);
        String status=intent.getStringExtra("type");//判断类型
        //初始化商品列表（从数据库中读取）
        if(status.equals("record")){
            goodsBeanArrayList=accountUtils.getRecord(user.getAccount());//足迹
            page_name.setText("我的足迹");
            if(goodsBeanArrayList.size()==0||goodsBeanArrayList==null){
                Toast.makeText(GoodsListActivity.this,"足迹空空如也呢" , Toast.LENGTH_SHORT).show();
            }else {
                //创建适配器的时候顺带传入数据表名字，这样就可以只写一个删除数据表数据的函数了
                //传入用户是为了需要用户名和商品名来删除信息
                goodsListAdapter=new GoodsListAdapter(this,user,"record",goodsBeanArrayList,false);
                //Log.d("GoodsListActivity", goodsBeanArrayList.get(0).getG_name());
            }

        }
        else if(status.equals("collection")){
            goodsBeanArrayList=accountUtils.getCollections(user.getAccount());//收藏
            page_name.setText("我的收藏");
            if(goodsBeanArrayList.size()==0||goodsBeanArrayList==null){
                Toast.makeText(GoodsListActivity.this,"收藏空空如也呢" , Toast.LENGTH_SHORT).show();
            }else {
                goodsListAdapter=new GoodsListAdapter(this,user,"collection",goodsBeanArrayList,false);
            }

        }
        else if(status.equals("sort")){
            //其他，如通过分类过来
            goodsBeanArrayList=(ArrayList<GoodsBean>) intent.getSerializableExtra("goodsList");
            if (goodsBeanArrayList==null){
                Log.d("GoodsListActivity","空空如也");
            }else {
                page_name.setText(intent.getStringExtra("label"));
                //Log.d("GoodsListActivity","src"+);
                goodsListAdapter=new GoodsListAdapter(this,user,null,goodsBeanArrayList,true);
            }

        }

        goods_list.setAdapter(goodsListAdapter);
    }
    private void initEvent(){
        comeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goods_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取当前点击的商品
                GoodsBean goods=(GoodsBean) parent.getAdapter().getItem(position);
                //设置需要跳转的activity：GoodsActivity
                intent.setClass(GoodsListActivity.this, GoodsActivity.class);
                //把商品实例传递过去
                intent.putExtra("goods",goods);
                //跳转
                startActivity(intent);
            }
        });
    }
}
