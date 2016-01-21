package cn.hollo.www.http;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by orson on 16/1/14.
 * 网络请求代理类
 */
public class HttpRequestProxy {
    private static final String URL_USER_LOGIN = "http://opdev.hollo.cn/index/check-password";     //用户登陆地址

    private static HttpRequestProxy instance;
    private HttpRequest mHttpRequest;

    private HttpRequestProxy() {
        mHttpRequest = HttpRequest.getInstance();
    }

    /**
     * 返回代理对象
     * @return
     */
    public static HttpRequestProxy getInstance(){
        if (instance == null)
            instance = new HttpRequestProxy();

        return instance;
    }

    /**
     * 用户登陆
     * @param account   ：账号
     * @param password  ：密码
     * @param listener  ：响应事件监听器对象
     */
    public void userLogin(String account, String password, OnResponseListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", account);
            jsonObject.put("password", password);
            HttpJsonRequest request = new HttpJsonRequest(Request.Method.POST, URL_USER_LOGIN, jsonObject, listener);

            mHttpRequest.executeRequest(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**#################################################
     * json数据请求类
     */
    private class HttpJsonRequest extends JsonObjectRequest{
        private String requestUrl;

        public HttpJsonRequest(int method, String url,  JSONObject jsonRequest, final OnResponseListener listener) {
            super(method, url, jsonRequest, new Response.Listener<JSONObject>(){
                public void onResponse(JSONObject jsonObject) {
                    HttpResponseEntity entity = new HttpResponseEntity();
                    entity.setCode(200);
                    entity.setData(jsonObject);

                        //取出响应头部信息
                        try {
                            if (jsonObject.has("cookie"))
                                entity.setCookie(jsonObject.getString("cookie"));

                            if (jsonObject.has("url"))
                                entity.setUrl(jsonObject.getString("url"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    if (listener != null)
                        listener.onResponse(entity);

                    Log.i("=====", "response success=== " + jsonObject.toString());
                }
            }, new Response.ErrorListener(){
                public void onErrorResponse(VolleyError volleyError) {
                    HttpResponseEntity entity = new HttpResponseEntity();

                    if (volleyError == null || volleyError.networkResponse == null)
                        entity.setCode(-1);
                    else{
                        entity.setCode(volleyError.networkResponse.statusCode);
                        entity.setErrMessage(volleyError.getMessage());
                    }

                    if (listener != null)
                        listener.onResponse(entity);

                    Log.i("=====", "response error=== " + volleyError.networkResponse.statusCode);
                }
            });

            requestUrl = url;
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                String cookieStr = response.headers.get("Set-Cookie");

               /* if (cookieStr != null && cookieStr.startsWith("PHPSESSID"))
                    cookieStr = cookieStr.substring(0, cookieStr.indexOf(';'));*/

                //将cookie字符串添加到jsonObject中，该jsonObject会被deliverResponse递交，调用请求时则能在onResponse中得到
                JSONObject jsonObject = new JSONObject(jsonString);
                jsonObject.put("cookie",cookieStr);
                jsonObject.put("url", requestUrl);

                return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }
    }
}
