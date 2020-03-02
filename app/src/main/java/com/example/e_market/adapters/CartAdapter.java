package com.example.e_market.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.R;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.StatusGoodsBean;
import com.example.e_market.beans.User;
import com.example.e_market.tools.AccountUtils;
import com.loopj.android.image.SmartImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter{

    public static CartAdapter c ;

    public ArrayList<StatusGoodsBean> goodsBeanArrayList;
    private User user;//用户
    private LayoutInflater layoutInflater;
    private Context context;
    private AccountUtils au;//数据库操作工具

    public CartAdapter(Context context, User user,ArrayList<StatusGoodsBean> goodsBeanArrayList){
        this.layoutInflater=LayoutInflater.from(context);
        this.au=new AccountUtils(context);
        this.user=user;
        this.goodsBeanArrayList=goodsBeanArrayList;
        this.context=context;

        c = this;
    }
    @Override
    public int getCount() {
        //原来还是购物车时，由于获取了购物车商品数，导致数组下标越界，很烦
        //因为一种商品可以有几个，造成ListView的长度与实际的商品种类不一致
        return goodsBeanArrayList.size();//返回item数目
    }
    @Override
    public  Object getItem(int position){
        return goodsBeanArrayList.get(position);//返回item对象
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.cart_item, null);
        final StatusGoodsBean statusGoodsBean= goodsBeanArrayList.get(position);//先是获取带状态商品
        final GoodsBean goodsBean=statusGoodsBean.getGoodsBean();//然后是无状态商品（原商品）
        //实例化ListView的控件
        //商品信息组件
        SmartImageView pic=(SmartImageView)convertView.findViewById(R.id.item_img);
        TextView name=(TextView)convertView.findViewById(R.id.item_name);
        TextView price=(TextView)convertView.findViewById(R.id.item_price);

        //其他组件
        CheckBox is_checked=(CheckBox)convertView.findViewById(R.id.isChecked);
        TextView item_sub=(TextView)convertView.findViewById(R.id.item_sub);
        TextView item_add=(TextView)convertView.findViewById(R.id.item_add);
        TextView item_qual=(TextView)convertView.findViewById(R.id.item_qual);
        RoundedImageView item_delete=(RoundedImageView)convertView.findViewById(R.id.item_delete);
        //设置商品信息
        name.setText(goodsBean.getG_name());
        price.setText(String.valueOf(goodsBean.getG_price()));
        pic.setImageUrl(goodsBean.getG_src());
        item_qual.setText(String.valueOf(goodsBean.getG_num()));//商品数量
        is_checked.setChecked(statusGoodsBean.isChecked());//根据含状态商品的当前状态设置checkBox是否选中
        //监听事件
        is_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //内部类怎样才能修改外部类的成员变量？
                //直接修改不行?
                //Log.d("CartFragment", "before status： " + goodsBeanArrayList.get(position).isChecked());
                statusGoodsBean.setChecked(isChecked);// 改变自己的状态
                goodsBeanArrayList.set(position,statusGoodsBean);//根据是否选中设置状态
                //Log.d("CartFragment", "after status： " + goodsBeanArrayList.get(position).isChecked());
            }
        });

        item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                au.updateCart(user.getAccount(),goodsBean,"add");//在数据库中修改
                goodsBean.setG_num(goodsBean.getG_num()+1);//修改商品数量
                notifyDataSetChanged();//刷新
                Log.d("CartFragment", "one of cart add is updated ");
            }
        });
        item_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodsBean.getG_num()<2){
                    //只有一个时不能再减了
                    Toast.makeText(context,"不能再少了" , Toast.LENGTH_SHORT).show();
                }else {
                    au.updateCart(user.getAccount(),goodsBean,"sub");
                    goodsBean.setG_num(goodsBean.getG_num()-1);//商品数量
                    notifyDataSetChanged();
                    Log.d("CartFragment", "one of cart sub is updated ");
                }

            }
        });
        item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsBeanArrayList.remove(statusGoodsBean);
                au.deleteData("cart",user.getAccount(),goodsBean.getG_name());
                notifyDataSetChanged();
                Log.d("CartFragment", "one of cart is deleted "+goodsBean.getG_name());

            }
        });
        return convertView;
    }

    public ArrayList<StatusGoodsBean> getGoodsBeanArrayList() {
        for (StatusGoodsBean g:goodsBeanArrayList
        ) {
            Log.d("CartFragment", "adapter: "+g.isChecked());
        }

        return goodsBeanArrayList;
    }

    public void setGoodsBeanArrayList(ArrayList<StatusGoodsBean> goodsBeanArrayList) {
        this.goodsBeanArrayList = goodsBeanArrayList;
    }
}
