package com.example.e_market.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_market.R;
import com.example.e_market.beans.User;
import com.example.e_market.tools.AccountUtils;

import org.w3c.dom.Text;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    Intent intent;
    //页面组件
    private EditText username;
    private EditText password;
    private Button but_login;
    private TextView go_signup;
    private TextView go_findpwd;

    private ImageView comeback;
    private TextView page_name;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent();
        //初始化组件
        username =(EditText)findViewById(R.id.input_username);
        password =(EditText)findViewById((R.id.input_password));
        but_login=(Button)findViewById(R.id.button_login);
        go_signup=(TextView)findViewById((R.id.go_signup));
        go_findpwd=(TextView)findViewById((R.id.go_fingpwd));
        //页面标题
        page_name=(TextView)findViewById(R.id.page_name);
        page_name.setText("登录");
        //返回键
        comeback=(ImageView)findViewById(R.id.comeback);
        comeback.setOnClickListener(this);

        but_login.setOnClickListener(this);
        go_signup.setOnClickListener(this);
        go_findpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                //登录
                if(logIn(LoginActivity.this)){
                    Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                    //intent.setClass(LoginActivity.this, MainActivity.class);
                    //获取用户账号
                    String account=username.getText().toString().trim();
                    //更新用户登录信息，写入数据库
                    new AccountUtils(LoginActivity.this).updateLoginInfo(account);
                    //成功结束activity
                    finish();

                }
                else{
                    Toast.makeText(LoginActivity.this,"账号或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_signup:
                //跳转注册界面
                intent.setClass(LoginActivity.this, SingupActivity.class);
                startActivity(intent);
                break;
            case R.id.go_fingpwd:
                //跳转找回密码界面
                Toast.makeText(LoginActivity.this,"宝贝，密码不能找回哦(○｀ 3′○)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.comeback:
                finish();
                break;
            default:
                    break;
        }

    }

    protected boolean logIn(Context context){
        boolean isSuccess=false;//登录状态
        //获取账号密码
        String account=username.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        //账号和密码都是空的
        if(account.isEmpty() || pwd.isEmpty()){
            Toast.makeText(LoginActivity.this,"账号或者密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            AccountUtils au=new AccountUtils(context);
            User user=au.getAccountInfo(account);
            Log.d("LoginActivity","数据库得到账号"+user.getAccount());
            //用户账号存在且密码正确
            if(user!=null && pwd.equals(user.getPassword())){
                intent.putExtra("user",user);//把
                isSuccess= true;
            }
        }
        return isSuccess;
    }

}

