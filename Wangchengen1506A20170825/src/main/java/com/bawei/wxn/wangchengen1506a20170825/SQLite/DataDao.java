package com.bawei.wxn.wangchengen1506a20170825.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bawei.wxn.wangchengen1506a20170825.bean.HttpData;
import com.bawei.wxn.wangchengen1506a20170825.bean.SQLiteBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2017/8/25.
 */

public class DataDao {

    private SQLiteDatabase db;

    public DataDao(Context context) {

        MySQLite mySQLite = new MySQLite(context);
        db = mySQLite.getWritableDatabase();
    }


    public void saveData(List<HttpData.ResultBean.DataBean> list) {

        for (HttpData.ResultBean.DataBean dataBean : list) {

            ContentValues values = new ContentValues();
            values.put("title", dataBean.getImtro());
            values.put("imgUrl", dataBean.getAlbums().get(0));

            db.insert("data", null, values);
        }

    }


    public List<SQLiteBean> findAllData() {

        List<SQLiteBean> list = new ArrayList<>();

        Cursor cursor = db.query("data", null, null, null, null, null, null);

        while (cursor.moveToNext()) {

            String title = cursor.getString(cursor.getColumnIndex("title"));
            String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));

            list.add(new SQLiteBean(title,imgUrl));
        }

        return list;

    }
}
