package com.yangb66.birthday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 彪 on 2017/12/7.
 */

public class mySQLOH extends SQLiteOpenHelper{
    public static final String DB_NAME = "Contacts.db";
    public static final String TABLE_NAME = "Contacts";
    public static final int DB_VERSION = 1;
    public static final String KEY_NAME = "name";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_GIFT = "gift";

    public mySQLOH(Context context) {
        super(context, DB_NAME, null, DB_VERSION);      // 创建访问对象，但没有创建数据库
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "create table " + TABLE_NAME +
                        " (_id integer primary key autoincrement," +
                        " name text not null , birthday text , gift text);";
        db.execSQL(CREATE_TABLE);
    }

    public void insert(String name, String birthday, String gift){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("birthday", birthday);
        values.put("gift", gift);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void insert(Member member){
        insert(member.name, member.birthday, member.gift);
    }

    public void update(String name, String birthday, String gift){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name=?"; //主键列名 = ?
        String[] whereArgs = {name};  //主键的值
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("birthday", birthday);
        values.put("gift", gift);
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    public void update(Member member){
        update(member.name, member.birthday, member.gift);
    }

    public void delete(String name){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name=?"; // 主键列名 = ?
        String[] whereArgs = {name};  // 主键的值
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public boolean query(String name){
        SQLiteDatabase db = getReadableDatabase();
        String selection = "name = ?";
        String[] selectionArgs = new String[]{name};
        Cursor cursor = db.query(TABLE_NAME, new String[]{"name"}, selection, selectionArgs, null, null, null);
        boolean ret = cursor.moveToFirst();
        cursor.close();
        db.close();
        return (ret);
    }

    public Member getById(Integer id){
        Member ret = null;
        SQLiteDatabase db = getReadableDatabase();
        String selection = "_id = ?";
        String[] selectionArgs = { id.toString() };
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if(cursor.moveToNext()){
            ret = new Member(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        cursor.close();
        db.close();
        return ret;
    }

    public Cursor getFirstCursor(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    // DB version 变化时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        // onCreate(db);
    }
}
