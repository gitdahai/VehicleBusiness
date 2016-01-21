package cn.hollo.www.http;

/**
 * Created by orson on 16/1/15.
 */
public class HttpHeader {
    private String url;
    private String cookie;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setHttpHeader(HttpHeader header){
        if (header != null){
            this.cookie = header.getCookie();
            this.url = header.getUrl();
        }
    }
}
