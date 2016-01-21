package cn.hollo.www.http;

import org.json.JSONObject;

/**
 * Created by orson on 16/1/14.
 */
public class HttpResponseEntity extends HttpHeader{
    private int code;               //请求结果状态吗
    private String errMessage;      //如果请求失败，这里描述失败的原因
    private JSONObject data;        //请求的响应结果

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
