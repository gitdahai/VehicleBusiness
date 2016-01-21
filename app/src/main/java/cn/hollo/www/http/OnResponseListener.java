package cn.hollo.www.http;

/**
 * Created by orson on 16/1/14.
 * http请求响应事件监听器
 */
public interface OnResponseListener {
    public void onResponse(HttpResponseEntity entity);
}
