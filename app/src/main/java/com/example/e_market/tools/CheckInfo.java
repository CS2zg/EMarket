package com.example.e_market.tools;

public class CheckInfo {
    //检查账号密码
    //账号为10~20位，密码为6~15位
    //暂只验证长度，其他的我太懒，还不想写
    public static boolean checkAccount(String account){

        if(account.length()<9 || account.length()>20) return false;
        else return true;
    }
    public static boolean checkPassword(String password){
        if(password.length()<6 || password.length()>15) return false;
        else return true;
    }
}
