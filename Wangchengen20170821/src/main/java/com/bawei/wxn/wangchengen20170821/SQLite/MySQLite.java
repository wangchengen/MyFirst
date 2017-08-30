package com.bawei.wxn.wangchengen20170821.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wxn on 2017/8/21.
 */

public class MySQLite extends SQLiteOpenHelper {
    public MySQLite(Context context) {
        super(context, "Info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table books(_id integer primary key autoincrement," +
                "name text,imgUrl text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
