package cn.hollo.www.application;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by orson on 16/1/18.
 * 账户信息管理器
 */
public class AccountMananger {
    private static final String SP_NAME = "user_account";
    private static AccountMananger instance;
    private SharedPreferences sp;

    private AccountMananger(Context context){
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 初始化方法
     * @param context
     */
    static void init(Context context){
        if (instance == null)
            instance = new AccountMananger(context);
    }

    /**
     *
     * @return
     */
    public static AccountMananger getInstance(){
        return instance;
    }

    /**
     * 设置字符数值
     * @param name
     * @param value
     */
    public void putString(String name, String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    /**
     * 返回保存的数值
     * @param name
     * @return
     */
    public String getString(String name){
        return sp.getString(name, null);
    }
}
