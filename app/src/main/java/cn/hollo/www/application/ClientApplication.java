package cn.hollo.www.application;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import cn.hollo.www.http.HttpHeader;
import cn.hollo.www.http.HttpRequest;

/**
 * Created by orson on 16/1/14.
 */
public class ClientApplication extends Application{
    private Map<String, Object> chache = new HashMap<String, Object>();

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化网络通信功能
        HttpRequest.init(this);
        //初始化账户信息管理对象
        AccountMananger.init(this);
    }

    /**
     * 设置httpHeader 对象
     * @param header
     */
    public void setHttpHeaders(HttpHeader header){
        if (header == null)
            return;

        HttpHeader httpHeader = null;

        if (!chache.containsKey("HttpHeader")){
            httpHeader = new HttpHeader();
            httpHeader.setHttpHeader(header);
            chache.put("HttpHeader", httpHeader);
        }
        else{
            httpHeader = (HttpHeader)chache.get("HttpHeader");
            httpHeader.setHttpHeader(header);
        }
    }

    /**
     * 获取header对象
     * @return
     */
    public HttpHeader getHttpHeader(){
        return (HttpHeader)chache.get("HttpHeader");
    }

}
