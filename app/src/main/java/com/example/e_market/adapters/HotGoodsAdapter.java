package com.example.e_market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.e_market.R;
import com.example.e_market.beans.GoodsBean;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class HotGoodsAdapter extends BaseAdapter {
    //需要适配的商品清单
    private ArrayList<GoodsBean> goodsBeanArrayList;
    private LayoutInflater layoutInflater;
    //构造方法
    public HotGoodsAdapter(Context context,ArrayList<GoodsBean> goodsBeanArrayList) {
        this.goodsBeanArrayList=goodsBeanArrayList;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return goodsBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsBeanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view;
        //if(convertView == null) {}
        convertView = layoutInflater.inflate(R.layout.hot_goods_item, null);
        //view = LayoutInflater.from(context).inflate
        SmartImageView hot_goods_img = (SmartImageView) convertView.findViewById(R.id.hot_goods_img);
        TextView hot_goods_name = (TextView) convertView.findViewById(R.id.hot_goods_name);
        TextView hot_goods_price = (TextView) convertView.findViewById(R.id.hot_goods_price);
        GoodsBean goodsBean = goodsBeanArrayList.get(position);
        hot_goods_img.setImageUrl(goodsBean.getG_src(), R.drawable.g1);
        hot_goods_name.setText(goodsBean.getG_name());
        hot_goods_price.setText(String.valueOf(goodsBean.getG_price()));

        return convertView;
    }
    class ViewHolder{
        SmartImageView hot_goods_img;
        TextView hot_goods_name;
        TextView hot_goods_price;

    }
}
