package cn.hollo.www.entities;

/**
 * Created by orson on 16/1/15.
 * 用户登录实体数据类
 */
public class UserLoginEntity {
    private String username;
    private String password;
    private boolean isRecorderPasswd;   //是否记住密码

    public boolean isRecorderPasswd() {
        return isRecorderPasswd;
    }

    public void setRecorderPasswd(boolean recorderPasswd) {
        isRecorderPasswd = recorderPasswd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
