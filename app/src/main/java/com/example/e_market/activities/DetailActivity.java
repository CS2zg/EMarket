package com.example.e_market.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.MainActivity;
import com.example.e_market.R;
import com.example.e_market.beans.User;
import com.example.e_market.tools.AccountUtils;
import com.example.e_market.tools.AddressSelector;
import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.hb.dialog.myDialog.MyPwdInputDialog;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    User user;
    //布局
    LinearLayout dl_name;
    //LinearLayout dl_account;账号不能更改
    LinearLayout dl_password;
    LinearLayout dl_address;
    //组件
    private TextView dt_name;
    private TextView dt_account;
    private TextView dt_address;
    private Button button_logout;
    private ImageView comeback;
    private TextView page_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        setUserInfo();
    }
    private void init(){
        intent =getIntent(); //new Intent();
        user=(User)intent.getSerializableExtra("user");
        //初始化组件
        dl_name=(LinearLayout)findViewById(R.id.detail_name_l);
        dl_password=(LinearLayout)findViewById(R.id.detail_password_l);
        dl_address=(LinearLayout)findViewById(R.id.detail_address_l);

        dt_name=(TextView)findViewById(R.id.detail_name);
        dt_account=(TextView)findViewById(R.id.detail_account);
        dt_address=(TextView)findViewById(R.id.detail_address);
        button_logout=(Button)findViewById(R.id.button_logout);
       //页面标题
        page_name=(TextView)findViewById(R.id.page_name);
        page_name.setText("我的信息");
        //返回键
        comeback=(ImageView)findViewById(R.id.comeback);
        comeback.setOnClickListener(this);
        //注册点击监听事件
        dl_name.setOnClickListener(this);
        dl_password.setOnClickListener(this);
        dl_address.setOnClickListener(this);
        //dt_account.setOnClickListener(this);
        button_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_name_l:
                //修改用户名
                modifyUsername(user.getUsername());
                Log.d("DetailActivity","name after modify"+user.getUsername());
                break;
            case R.id.detail_password_l:
                //修改密码
                modifyPassword();
                Log.d("DetailActivity","密码 after modify"+user.getPassword());
                break;
            case R.id.detail_address_l:
                AddressSelector addressSelector=new AddressSelector();
                addressSelector.parseData(DetailActivity.this);
                //addressSelector.showPickerView(DetailActivity.this);
                Log.d("DetailActivity","dizhi按下了");
                break;
            case R.id.button_logout:
                Log.d("DetailActivity","注销按下了");
                //跳转到登录界面
                intent.setClass(DetailActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.comeback:
                finish();
                break;
            default:
                break;
        }
    }
    //设置用户信息在UI
    private void setUserInfo(){
        dt_name.setText(user.getUsername());
        dt_account.setText(user.getAccount());
        if(user.getAddress()==""){
            dt_address.setText("未设置");
        }else {
            dt_address.setText("已设置");
        }
    }
    //修改用户名
    private void modifyUsername(String username){
        //创建输入弹窗
        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(DetailActivity.this).builder();
        myAlertInputDialog.setTitle("请输入新用户名").setEditText(username);
        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击做一些事情
                user.setUsername(myAlertInputDialog.getResult());//修改user的username属性
                setUserInfo();//更新UI的用户信息
                new AccountUtils(DetailActivity.this).updateUsername(user.getAccount(),user.getUsername());//更新数据库信息
                Log.d("DetailActivity","新用户名："+user.getUsername());
                myAlertInputDialog.dismiss();
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏
                myAlertInputDialog.dismiss();
            }
        });
        myAlertInputDialog.show();
    }
    //修改密码
    private void modifyPassword(){
        //创建密码输入框
        final MyPwdInputDialog old_pwdDialog = new MyPwdInputDialog(this).builder().setTitle("请输入旧密码");
        final MyPwdInputDialog new_pwdDialog = new MyPwdInputDialog(this).builder().setTitle("请输入新密码");
        //旧密码输入框监听器
        old_pwdDialog.setPasswordListener(new MyPwdInputDialog.OnPasswordResultListener() {
            @Override
            public void onPasswordResult(String old_password) {
                //判断输入密码是否与原密码一致
                if(old_password.equals(user.getPassword())){
                    //隐藏旧密码输入框，显示新密码输入框
                    old_pwdDialog.dismiss();
                    new_pwdDialog.show();
                }else {
                    Toast.makeText(DetailActivity.this,"密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //新密码输入框监听器
        new_pwdDialog.setPasswordListener(new MyPwdInputDialog.OnPasswordResultListener() {
            @Override
            public void onPasswordResult(String new_password) {
                user.setPassword(new_password);
                setUserInfo();
                new AccountUtils(DetailActivity.this).updatePassword(user.getAccount(),user.getPassword());
                Log.d("DetailActivity","新密码："+user.getPassword());
                new_pwdDialog.dismiss();
            }
        });
        old_pwdDialog.show();
    }
}
