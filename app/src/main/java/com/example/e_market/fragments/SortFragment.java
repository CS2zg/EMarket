package com.example.e_market.fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.example.e_market.R;
import com.example.e_market.adapters.HomeAdapter;
import com.example.e_market.adapters.MenuAdapter;
import com.example.e_market.beans.CategoryBean;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SortFragment extends Fragment {
    private View view;
    //数据存储
    private List<String> menuList = new ArrayList<>();
    private List<CategoryBean.DataBean> homeList = new ArrayList<>();
    private List<Integer> showTitle;
    //主界面
    private ListView lv_menu;
    private ListView lv_home;
    //适配器
    private MenuAdapter menuAdapter;
    private HomeAdapter homeAdapter;
    private int currentItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将布局文件解析出来
        view =inflater.inflate(R.layout.fragment_sort,container,false);
        Fresco.initialize(getActivity());
        init();//初始化组件和相关数据
        initEvent();//初始化监听事件
        loadData();
        return view;
    }

    private void initEvent() {
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                //    tv_title.setText(menuList.get(position));
                lv_home.setSelection(showTitle.get(position));
            }
        });


        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
//				lv_home.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    //       tv_title.setText(menuList.get(currentItem));
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });
    }

    private void init() {
        lv_menu = (ListView) view.findViewById(R.id.lv_menu);
        // tv_title = (TextView) findViewById(R.id.tv_titile);
        lv_home = (ListView) view.findViewById(R.id.lv_home);
        menuAdapter = new MenuAdapter(getActivity(), menuList);
        lv_menu.setAdapter(menuAdapter);

        homeAdapter = new HomeAdapter(getActivity(), homeList);
        lv_home.setAdapter(homeAdapter);


    }

    private void loadData() {

        String json = getJson(getActivity(), "category.json");
        CategoryBean categoryBean = JSONObject.parseObject(json, CategoryBean.class);
        showTitle = new ArrayList<>();

        for (int i = 0; i < categoryBean.getData().size(); i++) {
            CategoryBean.DataBean dataBean = categoryBean.getData().get(i);
            menuList.add(dataBean.getModuleTitle());
            showTitle.add(i);
            homeList.add(dataBean);
        }
        // tv_title.setText(categoryBean.getData().get(0).getModuleTitle());
        Log.d("MainActivity", menuList.get(0));
        menuAdapter.notifyDataSetChanged();
        homeAdapter.notifyDataSetChanged();
    }
    /**
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
