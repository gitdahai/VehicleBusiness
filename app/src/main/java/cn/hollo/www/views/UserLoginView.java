package cn.hollo.www.views;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hollo.www.R;
import cn.hollo.www.application.AccountMananger;
import cn.hollo.www.entities.UserLoginEntity;

/**
 * Created by orson on 16/1/15.
 */
public class UserLoginView implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.username)        TextView username;     //用户名输入
    @Bind(R.id.password)        TextView password;     //密码输入
    @Bind(R.id.submit)          TextView submit;       //提交按钮
    @Bind(R.id.record_passwd)   CheckBox rcorderPasswd;//记住密码

    private View formView;
    private UserLoginEntity entity;
    private OnActionSubmitListener mListener;

    public UserLoginView(Context context){
        formView = View.inflate(context, R.layout.view_user_login, null);
        ButterKnife.bind(this, formView);
        submit.setOnClickListener(this);
        rcorderPasswd.setOnCheckedChangeListener(this);
        entity = new UserLoginEntity();
        //读取账户信息
        readAccount(entity);
        //显示账户信息
        showAccount(entity);
    }

    public View getView(){
        return formView;
    }

    /**
     * 设置事件监听器
     * @param l
     */
    public void setOnActionSubmitListener(OnActionSubmitListener l){
        mListener = l;
    }

    @Override
    public void onClick(View v) {
        entity.setUsername(username.getText().toString());
        entity.setPassword(password.getText().toString());

        if (mListener != null)
            mListener.onActionSubmit(entity);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        entity.setRecorderPasswd(isChecked);
    }

    /**
     * 读取用户信息
     */
    private void readAccount(UserLoginEntity entity){
        AccountMananger mananger = AccountMananger.getInstance();
        entity.setUsername(mananger.getString("username"));
        entity.setPassword(mananger.getString("password"));
    }

    /**
     * 显示账户信息
     * @param entity
     */
    private void showAccount(UserLoginEntity entity){
        if (entity.getUsername() != null)
            username.setText(entity.getUsername());

        if (entity.getPassword() != null){
            password.setText(entity.getPassword());
            rcorderPasswd.setChecked(true);
        }
        else
            rcorderPasswd.setChecked(false);
    }

    /**#####################################################
     * 用户登录事件
     */
    public interface  OnActionSubmitListener{
        public void onActionSubmit(UserLoginEntity entity);
    }

}
