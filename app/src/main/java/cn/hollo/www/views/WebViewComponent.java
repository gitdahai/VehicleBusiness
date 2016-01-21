package cn.hollo.www.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.hollo.www.R;
import cn.hollo.www.application.ClientApplication;
import cn.hollo.www.dialogs.TipDialog;
import cn.hollo.www.http.HttpHeader;
import cn.hollo.www.widgets.ProgressWheel;

/**
 * Created by orson on 16/1/15.
 */
public class WebViewComponent {
    @Bind(R.id.webview)          WebView mWebView;
    @Bind(R.id.progress_wheel)
    ProgressWheel progressWheel;    //进度条

    private View mView;
    private Map<String, String> headers;
    private Context mContext;
    private Handler mHandler = new Handler();

    public WebViewComponent(Context context){
        this.mContext = context;
        mView = View.inflate(context, R.layout.view_web_component, null);
        ButterKnife.bind(this, mView);

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(chromeClient);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        //设置headers
        headers = new HashMap<String, String>();
        putHeaders(context);
    }



    /**
     * 填充header数据
     */
    private void putHeaders(Context context){
        ClientApplication application = (ClientApplication)context.getApplicationContext();
        HttpHeader header = application.getHttpHeader();

        if (header != null){
            synCookies(header.getUrl(), header.getCookie());
            //headers.put("HTTP_COOKIE", header.getCookie());
        }
    }

    /**
     * 同步cookie
     * @param url
     * @param cookies
     */
    public void synCookies(String url, String cookies) {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        //cookies是在HttpClient中获得的cookie
        cookieManager.setCookie(url, cookies);
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 加载网页页面
     * @param url
     */
    public void loadWebPage(String url){
        //加载页面
        mWebView.loadUrl(url, headers);
        //mWebView.loadUrl(url);
    }

    public View getView(){
        return mView;
    }

    /**
     *
     */
    private WebViewClient webViewClient = new WebViewClient(){
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }
    } ;

    /**
     * 主要显示web页面加载的进度条
     */
    private WebChromeClient chromeClient = new WebChromeClient(){
        public void onProgressChanged(WebView view, int newProgress) {
            int progress = (int)(360 * (newProgress / 100.0));
            progressWheel.setProgress(progress);
            progressWheel.setText(newProgress + "%");

            if (newProgress == 100){
                //延迟500毫秒隐藏
                mHandler.postDelayed(runnable, 500);
            }
            else {
                if (View.INVISIBLE == progressWheel.getVisibility())
                    progressWheel.setVisibility(View.VISIBLE);
            }
        }
    };

    private Runnable runnable = new Runnable() {
        public void run() {
            progressWheel.setVisibility(View.INVISIBLE);
        }
    };

    /**
     * 手机按键事件
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mWebView.canGoBack())
                mWebView.goBack();
            else {

                Resources res = mContext.getResources();
                TipDialog dialog = new TipDialog(mContext);

                DialogInterface.OnClickListener listner = new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE)
                            System.exit(0);//退出程序

                        dialog.dismiss();
                    }
                };

                dialog.setTitle(res.getString(R.string.text_6));
                dialog.setMessage(res.getString(R.string.text_7));
                dialog.setPositiveButton(res.getString(R.string.text_9), listner);
                dialog.setNegativeButton(res.getString(R.string.text_8), listner);
                dialog.show();
            }

            return true;
        }

        return false;
    }
}
