package cn.hollo.www.parser;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by orson on 16/1/15.
 */
public class JsonParser {
    private static JsonParser instance;
    private Gson mGson;

    private JsonParser(){
        mGson = new Gson();
    }

    public static JsonParser getInstance(){
        if (instance == null)
            instance = new JsonParser();

        return instance;
    }

    /**
     * 解析方法
     * @param json
     * @param classType
     * @param <T>
     * @return
     */
    public <T> T parser(JSONObject json, Class<T> classType){
       return mGson.fromJson(json.toString(), classType);
    }
}
