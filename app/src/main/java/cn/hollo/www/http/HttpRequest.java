package cn.hollo.www.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by orson on 16/1/14.
 */
public class HttpRequest {
    private static HttpRequest instance;
    private RequestQueue mQueue;

    /**
     * 单例模式
     * @param context
     */
    private HttpRequest(Context context){
        mQueue = Volley.newRequestQueue(context);
        mQueue.start();
    }

    /**
     * 初始化该类:
     * 这个方法只在app启动的时候，初始化一次即可
     * @param context
     */
    public static void init(Context context){
        if (instance == null)
            instance = new HttpRequest(context);
    }

    /**
     * 返回给类的实例对象
     * @return
     */
    static HttpRequest getInstance(){
        return instance;
    }

    /**
     * 添加并且执行请求任务
     * @param request
     */
    void executeRequest(Request request){
        mQueue.add(request);
    }

}
