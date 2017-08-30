package com.bawei.wxn.wangchengen1506a20170825.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wxn on 2017/8/25.
 */

public class MySQLite extends SQLiteOpenHelper {
//    3)	数据存储到本地数据库，数据库命名为yuekao.db
    public MySQLite(Context context) {
        super(context, "yuekao.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table data(_id integer primary key autoincrement," +
                "title text," +
                "imgUrl text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
