package cn.hollo.www.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.hollo.www.controllers.OnNoticeListener;
import cn.hollo.www.controllers.UserLoginController;
import cn.hollo.www.entities.UserLoginEntity;
import cn.hollo.www.views.UserLoginView;

/**
 * Created by orson on 16/1/15.
 * 用户登陆
 */
public class UserLoginActivity extends AppCompatActivity implements UserLoginView.OnActionSubmitListener, OnNoticeListener {
    private UserLoginView userLoginView;
    private UserLoginController controller;         //

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLoginView = new UserLoginView(this);
        userLoginView.setOnActionSubmitListener(this);
        controller = new UserLoginController(this);
        controller.setOnNoticeListener(this);
        this.setContentView(userLoginView.getView());
    }

    @Override
    public void onActionSubmit(UserLoginEntity entity) {
        controller.submit(entity);
    }

    @Override
    public void onNotice(NoticeType type) {
        if (type == NoticeType.CLOSE)
            finish();
    }
}
