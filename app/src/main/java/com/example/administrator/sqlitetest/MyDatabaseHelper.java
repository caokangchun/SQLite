package com.example.administrator.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/4/24.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    //create table Book(id integer primary key autoincrement, author text, name text, price real, page integer);
    static final String CREATE_BOOK = "create table Book (id integer primary key autoincrement, author text, name text, price real, page integer)";
    static final String CREATE_CATEGORY = "create table Category(id integer primary key autoincrement, category_name text, category_code integer)";
    static final String CREATE_FAMILY = "create table Family(id integer primary key autoincrement, name text, relation text,age integer, height real, weight real)";
    private static final String ADD_COLUMN = "alter table Book add column category_id integer";
    private static final String ADD_COLUMN2 = "alter table Book add column category_id2 integer";
    private static final String ADD_COLUMN3 = "alter table Book add column category_id3 integer";
    private final Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name,factory,version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        db.execSQL(CREATE_FAMILY);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("drop table if exists Book");
//        db.execSQL("drop table if exists Category");
//        onCreate(db);

        switch (oldVersion)
        {
            case 5:
                db.execSQL(CREATE_FAMILY);

            case 6:
                db.execSQL(ADD_COLUMN);

            case 7:
                db.execSQL(ADD_COLUMN2);

            case 8:
                db.execSQL(ADD_COLUMN3);

            default:
                break;
        }
        Toast.makeText(mContext,"Upgrade Succeed "+ oldVersion +"->"+ newVersion,Toast.LENGTH_SHORT).show();
    }
}
