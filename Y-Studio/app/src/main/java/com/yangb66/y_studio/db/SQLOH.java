package com.yangb66.y_studio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yangb66.y_studio.model.UrlHint;

/**
 * Created by 彪 on 2017/12/7.
 *
 * 可以管理多个表格
 * 定义table_name1,db_name1,db_version1,table1_key1, ..., insert1, query1, update1, delete1, clear1, getFirstCursor1 作为一个表
 * SQLiteOpenHelper 类可以用作提供数据库服务的订制类，功能正确性和效率都可以内测，然后便于小组分工合作
 */

public class SQLOH extends SQLiteOpenHelper{
    public static final String DB_URLHint = "urlHintList.db";  //urlHint表
    public static final String TABLE_URLHint = "urlHintList";
    public static final int DB_VERSION = 1;
    public static final String KEY_URL = "hintUrl", KEY_TITLE = "hintTitle";

    public SQLOH(Context context) {
        super(context, DB_URLHint, null, DB_VERSION);      // 创建访问对象，但没有创建数据库
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "create table " + TABLE_URLHint + " (" +
                        "_id integer primary key autoincrement," +
                        KEY_URL + " text not null, " +
                        KEY_TITLE + " text" +
                        ");";
        db.execSQL(CREATE_TABLE);
    }

    public void insert(UrlHint urlHint){
        if(!query(urlHint)){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_URL, urlHint.getUrl());
            values.put(KEY_TITLE, urlHint.getTitle());
            db.insert(TABLE_URLHint, null, values);
            db.close();
        } else{
            update(urlHint);
        }
    }


    public void update(UrlHint urlHint){
        if(!query(urlHint)){
            SQLiteDatabase db = getWritableDatabase();
            String whereClause = KEY_URL + "= ?"; //主键列名 = ?
            String[] whereArgs = {urlHint.getUrl()};  //主键的值
            ContentValues values = new ContentValues();
            values.put(KEY_URL, urlHint.getUrl());
            values.put(KEY_TITLE, urlHint.getTitle());
            db.update(TABLE_URLHint, values, whereClause, whereArgs);
            db.close();
        }
    }

    public void delete(UrlHint urlHint){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_URL + "= ?"; //主键列名 = ?
        String[] whereArgs = {urlHint.getUrl()};  //主键的值
        db.delete(TABLE_URLHint, whereClause, whereArgs);
        db.close();
    }

    public boolean query(UrlHint urlHint){
        SQLiteDatabase db = getReadableDatabase();
        String selection = KEY_URL + "= ?";
        String[] selectionArgs = new String[]{urlHint.getUrl()};
        Cursor cursor = db.query(TABLE_URLHint, new String[]{KEY_URL}, selection, selectionArgs, null, null, null);
        boolean ret = cursor.moveToFirst();
        cursor.close();
        db.close();
        return (ret);
    }

    public Cursor getFirstCursor(){
        SQLiteDatabase db = getReadableDatabase();
        // 获得第一个数据，按照_id从大到小的方式排序
        Cursor cursor = db.query(TABLE_URLHint, null, null, null, null, null, "_id DESC");
        return cursor;
    }

    // DB version 变化时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS" + TABLE_login);
        // onCreate(db);
    }

    public boolean isEmpty(){
        SQLiteDatabase db = getReadableDatabase();
        // 获得第一个数据，按照_id从大到小的方式排序
        Cursor cursor = db.query(TABLE_URLHint, null, null, null, null, null, "_id DESC");
        return cursor == null || !cursor.moveToFirst();
    }

    public void clear(){
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_URLHint, null, null);
    }
}
