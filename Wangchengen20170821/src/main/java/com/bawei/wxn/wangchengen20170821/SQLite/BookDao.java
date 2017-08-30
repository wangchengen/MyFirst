package com.bawei.wxn.wangchengen20170821.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bawei.wxn.wangchengen20170821.bean.BooksInfo;
import com.bawei.wxn.wangchengen20170821.bean.DBbookBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2017/8/21.
 */

public class BookDao {

    private SQLiteDatabase db;

    public BookDao(Context context) {

        MySQLite mySQLite = new MySQLite(context);
        db = mySQLite.getWritableDatabase();
    }

    public void saveData(List<BooksInfo.ResultBean.BookListBean> list){

        for (BooksInfo.ResultBean.BookListBean dBbookBean : list) {
            ContentValues values = new ContentValues();
            values.put("name",dBbookBean.getName());
            values.put("imgUrl",dBbookBean.getCoverImg());

            db.insert("books",null,values);
        }



    }

    public List<DBbookBean> findData(){

        List<DBbookBean> list = new ArrayList<>();

        Cursor cursor = db.query("books",null,null,null,null,null,null);

        while (cursor.moveToNext()){
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));

            list.add(new DBbookBean(_id,name,imgUrl));
        }

        return list;

    }


    public void deleteData(int id){

        db.delete("books","_id = ?",new String[]{String.valueOf(id)});

    }


}
