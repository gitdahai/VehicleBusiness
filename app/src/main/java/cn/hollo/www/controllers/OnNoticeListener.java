package cn.hollo.www.controllers;

/**
 * Created by orson on 16/1/15.
 * 通知试图，状态发生改变，需要试图从新刷新数据
 */
public interface OnNoticeListener {
    /**
     * 通知的类型
     */
    public enum NoticeType{
        CLOSE
    }

    /**
     * 发送通知
     * @param type
     */
    public void onNotice(NoticeType type);
}
