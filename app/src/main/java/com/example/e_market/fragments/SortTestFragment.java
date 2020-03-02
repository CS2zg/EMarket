package com.example.e_market.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.R;
import com.example.e_market.activities.GoodsActivity;
import com.example.e_market.activities.GoodsListActivity;
import com.example.e_market.adapters.HotGoodsAdapter;
import com.example.e_market.beans.GoodsBean;
import com.example.e_market.beans.SortBean;
import com.example.e_market.tools.GetJsonDataUtil;
import com.example.e_market.tools.HttpUtil;
import com.example.e_market.tools.ParseJsonUtil;
import com.loopj.android.http.TextHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class SortTestFragment extends Fragment {
    String sort;
    private View view;
    private Intent intent;
    private ListView sort_classifies;
    private GridView sort_items;
    private SortItemsAdapter gridAdapter;
    private ParseJsonUtil parseJsonUtil;
    //数据
    ArrayList<String> sort_classifies_list;
    ArrayList<SortBean> sort_item_list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将布局文件解析出来
        view =inflater.inflate(R.layout.fragment_sort_test,container,false);
        //初始化控件
        init();
        initEvent();
        return view;
    }
    private void init(){
        intent=new Intent();
        sort_classifies=(ListView)view.findViewById(R.id.sort_classifies);
        sort_items=(GridView)view.findViewById(R.id.sort_items);
        parseJsonUtil=new ParseJsonUtil();
        sort=GetJsonDataUtil.getJson(getActivity(),"sort.json");//把json文件解析成Json对象
        sort_classifies_list=parseJsonUtil.getSortClassifies(sort);//获取keys，即为分类的数据
        gridAdapter=new SortItemsAdapter();
        try{
            sort_item_list=parseJsonUtil.parseJsonToSort("手机数码",sort);//默认是”手机数码“

        }catch (Exception e) {
            e.printStackTrace();
        }
        sort_classifies.setAdapter(new SortClassifiesAdapter());
        sort_items.setAdapter(gridAdapter);
    }

    private void initEvent(){
        //分类item点击事件
        sort_classifies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classify=(String)parent.getAdapter().getItem(position);
                try{
                    sort_item_list=parseJsonUtil.parseJsonToSort(classify,sort);
                    gridAdapter.notifyDataSetChanged();
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        //各标签下的item点击事件
        sort_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SortBean s_item=(SortBean) parent.getAdapter().getItem(position);
                String table=s_item.getS_label();
                String title=s_item.getS_title();
                Log.d("SortFragment","我按了");
                //get请求热销商品
                HttpUtil.get("getData.php?table="+table+"&goods="+title, null, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(getActivity(),"访问失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        //Toast.makeText(getActivity(),responseString,Toast.LENGTH_SHORT).show();
                        ArrayList<GoodsBean> arrayList=new ParseJsonUtil().parseJsonToGoods(responseString,"sort");
                        //设置需要跳转的activity：GoodsActivity
                        intent.setClass(getActivity(), GoodsListActivity.class);
                        //把商品实例传递过去
                        intent.putExtra("type","sort");
                        intent.putExtra("label",s_item.getS_title());
                        intent.putExtra("goodsList",arrayList);
                        //跳转
                        startActivity(intent);
                        //Log.d("SortFragment","返回访问数据："+responseString);
                    }
                });
            }
        });

    }

    class SortClassifiesAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return sort_classifies_list.size();
        }

        @Override
        public Object getItem(int position) {
            return sort_classifies_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=View.inflate(getActivity(),R.layout.sort_cla,null);
            TextView classify=(TextView)convertView.findViewById(R.id.classify_text);
            if(sort_classifies_list!=null){
                classify.setText(sort_classifies_list.get(position));
            }else {
                classify.setText("null");
                Log.d("SortFragment", "null");
            }

            return convertView;
        }
    }
    class SortItemsAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return sort_item_list.size();
        }

        @Override
        public Object getItem(int position) {
            return sort_item_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=View.inflate(getActivity(),R.layout.sort_item,null);

            SmartImageView img=(SmartImageView)convertView.findViewById(R.id.sort_item_img);
            TextView title=(TextView)convertView.findViewById(R.id.sort_item_title);
            if(sort_item_list!=null){
                SortBean sortBean=sort_item_list.get(position);
                img.setImageUrl(sortBean.getS_src(),R.drawable.error_img);//含有中文、空格等一些特殊字符的url需要编码
                title.setText(sortBean.getS_title());
            }else {
                title.setText("null");
                Log.d("SortFragment", "null");
            }

            return convertView;
        }
    }
}
