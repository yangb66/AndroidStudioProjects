package com.yangb66.lab_web.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 彪 on 2017/12/7.
 */

public class SQLOH extends SQLiteOpenHelper{
    public static final String DB_login = "WebLab.db";
    public static final String TABLE_login = "WebLab";
    public static final int DB_VERSION = 1;
    public static final String KEY_LOGIN = "login";
    public static final String KEY_ID = "id";
    public static final String KEY_BLOG = "blog";

    public SQLOH(Context context) {
        super(context, DB_login, null, DB_VERSION);      // 创建访问对象，但没有创建数据库
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "create table " + TABLE_login +
                        " (_id integer primary key autoincrement," +
                        KEY_LOGIN + " text not null," + 
                        KEY_ID + " text ," + 
                        KEY_BLOG + " text);";
        db.execSQL(CREATE_TABLE);
    }

    public void insert(String login, int id, String blog){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login", login);
        values.put("id", id);
        values.put("blog", blog);
        db.insert(TABLE_login, null, values);
        db.close();
    }


    public void update(String login, int id, String blog){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "login=?"; //主键列名 = ?
        String[] whereArgs = {login};  //主键的值
        ContentValues values = new ContentValues();
        values.put("login", login);
        values.put("id", id);
        values.put("blog", blog);
        db.update(TABLE_login, values, whereClause, whereArgs);
        db.close();
    }

    public void delete(String login){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "login=?"; // 主键列名 = ?
        String[] whereArgs = {login};  // 主键的值
        db.delete(TABLE_login, whereClause, whereArgs);
        db.close();
    }

    public boolean query(String login){
        SQLiteDatabase db = getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[]{login};
        Cursor cursor = db.query(TABLE_login, new String[]{"login"}, selection, selectionArgs, null, null, null);
        boolean ret = cursor.moveToFirst();
        cursor.close();
        db.close();
        return (ret);
    }

    public Cursor getFirstCursor(){
        SQLiteDatabase db = getReadableDatabase();
        // 获得第一个数据，按照_id从大到小的方式排序
        Cursor cursor = db.query(TABLE_login, null, null, null, null, null, "_id DESC");
        return cursor;
    }

    // DB version 变化时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS" + TABLE_login);
        // onCreate(db);
    }
}
