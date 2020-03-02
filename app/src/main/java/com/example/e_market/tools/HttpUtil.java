package com.example.e_market.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.e_market.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class HttpUtil {
    //请求返回值
    String res;
    //建立静态的AsyncHttpClient
    private static AsyncHttpClient client = new AsyncHttpClient();
    //基础url
    private static final String BASE_URL = "http://134.175.242.22/zg/android/";
    //get请求
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    //post请求
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }
    //根据相对路径拼接url
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    //获取热门商品(已废弃)
    public String getHotGoods(final Context context){
        Log.d("HomeFragment","开始访问hot");
        client.get(BASE_URL + "hot/getHot.php", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("HomeFragment","访问网络失败");
                Toast.makeText(context, "gg", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                res=responseString;

            }
        });
        Log.d("HomeFragment","返回数据："+res);
        return res;
    }

}
