package cn.hollo.www.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import cn.hollo.www.application.AccountMananger;
import cn.hollo.www.application.ClientApplication;
import cn.hollo.www.entities.UserLoginEntity;
import cn.hollo.www.http.HttpRequestProxy;
import cn.hollo.www.http.HttpResponseEntity;
import cn.hollo.www.http.OnResponseListener;
import cn.hollo.www.parser.JsonParser;
import cn.hollo.www.parser.UserVerify;
import cn.hollo.www.routeview.RouteView;

/**
 * Created by orson on 16/1/15.
 * 用户登陆控制器
 */
public class UserLoginController {
    private Context mContext;
    private ProgressDialog progressDialog;
    private OnNoticeListener noticeListener;
    private UserLoginEntity mEntity;

    public UserLoginController(Context context){
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    /**
     * 进行用户登录
     */
    public void submit(UserLoginEntity entity){
        mEntity = entity;
        HttpRequestProxy proxy = HttpRequestProxy.getInstance();
        proxy.userLogin(mEntity.getUsername(), mEntity.getPassword(), responseListener);
        progressDialog.show();
    }

    /**
     * 保存用户的信息
     * @param entity
     */
    private void saveAccount(UserLoginEntity entity){
        AccountMananger mananger = AccountMananger.getInstance();
        mananger.putString("username", entity.getUsername());

        //是否记住密码
        if (entity.isRecorderPasswd())
            mananger.putString("password", entity.getPassword());
        else
            mananger.putString("password", null);
    }

    /**
     * 设置通知监听器
     * @param l
     */
    public void setOnNoticeListener(OnNoticeListener l){
        noticeListener = l;
    }

    /**
     * 请求响应事件监听器
     */
    private OnResponseListener responseListener = new OnResponseListener(){
        public void onResponse(HttpResponseEntity entity) {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            String toastMessage = entity.getErrMessage();
            UserVerify userVerify = null;

            //请求成功，解析数据
            if (entity.getCode() == 200)
                userVerify = JsonParser.getInstance().parser(entity.getData(), UserVerify.class);

            //是否存在验证信息
            if (userVerify != null){
                toastMessage = userVerify.getDesc();

                //保存header对象
                ClientApplication application = (ClientApplication)mContext.getApplicationContext();
                application.setHttpHeaders(entity);

                //验证成功
                if (userVerify.getCode() == 0){
                    RouteView.getInstance().toView(mContext, RouteView.VIEW_NAME_MAIN);
                    mContext = null;
                    progressDialog = null;

                    //保存用户账户信息
                    saveAccount(mEntity);

                    //通知控制台，关闭
                    if (noticeListener != null)
                        noticeListener.onNotice(OnNoticeListener.NoticeType.CLOSE);

                    return;
                }
            }

            //验证失败,弹出提示
            if (toastMessage != null)
                Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG).show();
        }
    };
}
