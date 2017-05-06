package com.example.administrator.sqlitetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CAO";
    MyDatabaseHelper dbHelper;
    Button create;
    Button add;
    Button update;
    Button delete;
    Button replace;
    Button query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this,"Book.db",null,5);
        create = (Button) findViewById(R.id.create);
        add = (Button) findViewById(R.id.add);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        replace = (Button) findViewById(R.id.replace);
        query = (Button) findViewById(R.id.query);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

//                ContentValues contentValues = new ContentValues();
//                contentValues.put("name","xiyouji");
//                contentValues.put("author","wuchengen");
//                contentValues.put("price",55);
//                contentValues.put("page",1255);
//
//                db.insert("Book",null,contentValues);
                db.execSQL("INSERT INTO book(author, name, price, page) VALUES('吴承恩','西游记',81,3000)");
                Toast.makeText(MainActivity.this,"Add succeeded",Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("price",198);
//                values.put("page",1980);
//                db.update("Book",values,"id = ?",new String[]{"2"});
                db.execSQL("UPDATE book SET price=93 WHERE id=2 OR id=1");//还是用sql语句比较方便，上面注释的方法不好理解，麻烦
                Toast.makeText(MainActivity.this,"Update succeeded",Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("DELETE FROM book");
                Toast.makeText(MainActivity.this,"Delete succeeded",Toast.LENGTH_SHORT).show();
            }
        });

        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                try{
                    db.beginTransaction();
                    db.execSQL("DELETE FROM book");
                    db.execSQL("INSERT INTO book(author, name, price, page) VALUES('wuchengen','xiyouji',81,3000)");

                    if(false)
                    {
                        throw new NullPointerException();
                    }
                    db.setTransactionSuccessful();
                    Toast.makeText(MainActivity.this,"Replace succeeded",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    db.endTransaction();
                }
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query("book",null,null,null,null,null,null);
                if(cursor.moveToFirst())
                {
                    do {
                        Log.d(TAG, "id= "+cursor.getString(cursor.getColumnIndex("id")));
                        Log.d(TAG, "author= "+cursor.getString(cursor.getColumnIndex("author")));
                        Log.d(TAG, "name= "+cursor.getString(cursor.getColumnIndex("name")));
                        Log.d(TAG, "price= "+cursor.getString(cursor.getColumnIndex("price")));
                        Log.d(TAG, "page= "+cursor.getString(cursor.getColumnIndex("page")));
                    }
                    while(cursor.moveToNext());
                    cursor.close();
                }
            }
        });
    }
}
