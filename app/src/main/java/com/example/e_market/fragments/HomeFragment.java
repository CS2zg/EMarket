package com.example.e_market.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.R;
import com.example.e_market.activities.GoodsActivity;
import com.example.e_market.activities.SearchActivity;
import com.example.e_market.adapters.HotGoodsAdapter;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.tools.GlideImageLoader;
import com.example.e_market.tools.HttpUtil;
import com.example.e_market.tools.ParseJsonUtil;
import com.loopj.android.http.TextHttpResponseHandler;

import com.youth.banner.Banner;
import java.util.ArrayList;

import java.util.List;


import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {
    private View view;
    private Intent intent;

    private GridView gridView;
    private FrameLayout search_img;
//    private Banner banner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将布局文件解析出来
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        initEvent();
        Log.d("HomeFragment", "operating procedure");

        return view;
    }
    private void init(){
        intent=new Intent();
        gridView=(GridView)view.findViewById(R.id.hot_goods_lists);
        search_img=(FrameLayout)view.findViewById(R.id.search_img);
//        banner = (Banner) view.findViewById(R.id.banner);
//        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        List<String> images=new ArrayList<String>();
//        images.add("http://134.175.242.22/zg/android/banners/banner1.JPG");
//        images.add("http://134.175.242.22/zg/android/banners/banner2.JPG");
//        images.add("http://134.175.242.22/zg/android/banners/banner3.JPG");
//        banner.setImages(images);
//        //banner设置方法全部调用完毕时最后调用
//        banner.start();
        //get请求热销商品
        HttpUtil.get("hot/getHot.php", null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(),"访问失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //Toast.makeText(getActivity(),responseString,Toast.LENGTH_SHORT).show();
                ArrayList<GoodsBean> arrayList=new ParseJsonUtil().parseJsonToGoods(responseString,"hot");
                HotGoodsAdapter hotGoodsAdapter = new HotGoodsAdapter(getActivity(),arrayList);
                gridView.setAdapter(hotGoodsAdapter);
                //Log.d("HomeFragment","返回访问数据："+responseString);
            }
        });
    }
    private void initEvent(){
        //搜索框点击事件
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        //热门商品的点击监听
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取当前点击的商品
                GoodsBean hot_goods=(GoodsBean) parent.getAdapter().getItem(position);
                //设置需要跳转的activity：GoodsActivity
                intent.setClass(getActivity(), GoodsActivity.class);
                //把商品实例传递过去
                intent.putExtra("goods",hot_goods);


                //跳转
                startActivity(intent);
            }
        });
    }

}
