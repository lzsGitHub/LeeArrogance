package com.sixteen_night.demo;

/**
 * Created by Lee on 2019/9/5 10:54.
 */
public class TextBean {
    private boolean isShowtitle;
    private String date;

    public TextBean(String date) {
        this.date = date;
    }

    public boolean isShowtitle() {
        return isShowtitle;
    }

    public void setShowtitle(boolean showtitle) {
        isShowtitle = showtitle;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
