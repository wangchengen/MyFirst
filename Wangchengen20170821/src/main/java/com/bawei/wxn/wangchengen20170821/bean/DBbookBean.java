package com.bawei.wxn.wangchengen20170821.bean;

/**
 * Created by wxn on 2017/8/21.
 */

public class DBbookBean {

    private int _id;

    private String name;
    private String imgUrl;

    public DBbookBean(int _id, String name, String imgUrl) {
        this._id = _id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public DBbookBean(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
