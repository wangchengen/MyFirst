package com.bawei.wxn.wangchengen1506a20170825.bean;

/**
 * Created by wxn on 2017/8/25.
 */

public class SQLiteBean {


    private String title;
    private String imgUrl;

    public SQLiteBean(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
