package com.example.e_market.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.R;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.User;
import com.example.e_market.tools.AccountUtils;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class GoodsListAdapter extends BaseAdapter {
    //需要适配的商品清单
    private ArrayList<GoodsBean> goodsBeanArrayList;
    //private GoodsBean goodsBean;//具体商品
    private User user;//用户
    private String table_type;//数据表
    private LayoutInflater layoutInflater;
    private AccountUtils au;//数据库操作工具
    private boolean isSort;
    //构造方法
    public GoodsListAdapter(Context context, User user,String table_type,ArrayList<GoodsBean> goodsBeanArrayList,boolean isSort) {
        this.goodsBeanArrayList=goodsBeanArrayList;
        this.user=user;
        this.table_type=table_type;
        this.layoutInflater=LayoutInflater.from(context);
        this.au=new AccountUtils(context);
        this.isSort=isSort;
    }
//没什么用
    public void setSort(boolean sort) {
        isSort = sort;
    }

    public boolean isSort() {
        return isSort;
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
        convertView = layoutInflater.inflate(R.layout.goods_item, null);
        SmartImageView goods_item_img = (SmartImageView) convertView.findViewById(R.id.goods_item_img);
        TextView goods_item_name = (TextView) convertView.findViewById(R.id.goods_item_name);
        TextView goods_item_price = (TextView) convertView.findViewById(R.id.goods_item_price);
        LinearLayout other_label=(LinearLayout)convertView.findViewById(R.id.other_label);//删除
        if(isSort){other_label.setVisibility(View.GONE);}

        //获取商品
        int index=getCount()-(position+1);//逆序渲染
        //这小东西不能作为适配器的属性定义，否则点击事件将会获取当前的item失败，总是会获取最后一个，只能用final修饰了
        final GoodsBean goodsBean = goodsBeanArrayList.get(index);
        //设置信息
        goods_item_img.setImageUrl(goodsBean.getG_src().replace(" ","%20"), R.drawable.error_img);
        goods_item_name.setText(goodsBean.getG_name());
        goods_item_price.setText(String.valueOf(goodsBean.getG_price()));

        other_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("GoodsListActivity", goodsBean.getG_src());
//                Log.d("GoodsListActivity", goodsBean.getG_src().replace(" ","%20"));
                //http://134.175.242.22/zg/android/imgs/手机数码/大牌手机/荣耀/华为（HUAWEI）%20荣耀9i手机%20幻夜黑%20全网通4G%20(4G+64G)%20标配版.jpg
                goodsBeanArrayList.remove(goodsBean);
                au.deleteData(table_type,user.getAccount(),goodsBean.getG_name());//从数据库删除
                notifyDataSetChanged();
            }
        });
        return convertView;

    }
}
