package cn.hollo.www.routeview;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import cn.hollo.www.activitys.MainActivity;

/**
 * Created by orson on 16/1/15.
 * 试图路由工具类
 */
public class RouteView {
    public static final String VIEW_NAME_MAIN = "main.view";

    private static RouteView instance;
    private Map<String, Integer> routeMap;

    private RouteView(){
        routeMap = new HashMap<String, Integer>();
        routeMap.put(VIEW_NAME_MAIN, 1);
    }

    public static RouteView getInstance(){
        if (instance == null)
            instance = new RouteView();

        return instance;
    }

    /**
     *
     * @param viewName
     */
    public void toView(Context context, String viewName){
        if (!routeMap.containsKey(viewName))
            return;

        Intent intent = null;

        //进行试图路由
        switch (routeMap.get(viewName)){
            case 1: intent = new Intent(context, MainActivity.class);   break;
        }

        //进行试图跳转
        if (intent != null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
