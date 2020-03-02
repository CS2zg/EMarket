package com.example.e_market.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.R;
import com.example.e_market.beans.User;
import com.example.e_market.tools.AccountUtils;
import com.example.e_market.tools.CheckInfo;

public class SingupActivity extends AppCompatActivity {
    //注册界面的wu个组件
    private EditText su_account;
    private EditText su_username;
    private EditText su_password;
    private EditText su_repwd;
    private Button signup;

    private ImageView comeback;
    private TextView page_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        init();
    }
    protected void init(){
        //初始化组件
        su_account=(EditText)findViewById(R.id.signup_account);
        su_username=(EditText)findViewById(R.id.signup_username);
        su_password=(EditText)findViewById(R.id.signup_password);
        su_repwd=(EditText)findViewById(R.id.signup_repwd);
        signup=(Button)findViewById(R.id.button_signup);
        //页面标题
        page_name=(TextView)findViewById(R.id.page_name);
        page_name.setText("注册");
        //返回键
        comeback=(ImageView)findViewById(R.id.comeback);
        comeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//关闭activity
            }
        });

        //注册按钮点击事件
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp()==1) {
                    Toast.makeText(SingupActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    //new AccountUtils(SingupActivity.this).updateLoginInfo(su_account.getText().toString().trim());
                    finish();//关闭注册activity，回到登录界面
                }
            }
        });
    }
    //注册方法
    //注册成功返回1，失败返回0
    protected int signUp(){
        int status=0;
        //获取注册信息
        String account=su_account.getText().toString().trim();
        String password=su_password.getText().toString().trim();
        String repwd=su_repwd.getText().toString().trim();
        String username=su_username.getText().toString().trim();
        //检查注册信息
        if(!CheckInfo.checkAccount(account)){
            Toast.makeText(SingupActivity.this,"账号错误", Toast.LENGTH_SHORT).show();
        }
        else if(!CheckInfo.checkPassword(password)){
            Toast.makeText(SingupActivity.this,"密码错误", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(repwd)){
            Toast.makeText(SingupActivity.this,"密码前后不一致", Toast.LENGTH_SHORT).show();
        }
        else {
            //判断账号
            if(isExistAccount(SingupActivity.this,account)==1){
                User new_user=new User(username,account,password);
                //保存数据到数据库
                new AccountUtils(SingupActivity.this).saveAccountInfo(new_user);
                status=1;
            }
        }
        return status;
    }

    //判断账号是否存在
    //存在返回0，不存在返回1
    public int isExistAccount(Context context, String account){
        User user= new AccountUtils(context).getAccountInfo(account);
        if(user==null) return 1;
        else  return 0;
    }
}
