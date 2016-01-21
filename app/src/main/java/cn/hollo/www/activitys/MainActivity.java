package cn.hollo.www.activitys;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import cn.hollo.www.views.WebViewComponent;

public class MainActivity extends AppCompatActivity {
    private WebViewComponent mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = new WebViewComponent(this);
        setContentView(mainView.getView());

        String url = "http://opdev.hollo.cn";

        mainView.loadWebPage(url);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mainView.onKeyDown(keyCode, event);
    }

}
