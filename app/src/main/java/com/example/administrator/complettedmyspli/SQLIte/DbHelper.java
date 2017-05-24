package com.example.administrator.complettedmyspli.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 11/05/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=1;
    private static final String CREAT_TABLE=

       "  CREATE TABLE "+DbContact.TABLE_NAME+" ("+DbContact.ID+" integer PRIMARY KEY AUTOINCREMENT,"+DbContact.NAME
               +" text NOT NULL,"+DbContact.EMAIL+" text NOT NULL,"+DbContact.USER_NAME+" text NOT NULL UNIQUE,"
               +DbContact.PASSWORD+" text NOT NULL,"+DbContact.SYNC_STATUS+"INTEGER);      ";
    private static final String DROP_TABLE=" DROP TABLE [IF EXISTS] "+DbContact.TABLE_NAME+"; ";

    public DbHelper (Context context){

        super(context,DbContact.DATABASE_NAME,null,DATABASE_VERSION);
}
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
        onCreate(db);

    }
    public void saveToLocalDataBase( String name,String email,String user_name,String password,int synce_status,SQLiteDatabase database){

        ContentValues contentValues=new ContentValues();
       // contentValues.put(DbContact.ID,id);
        contentValues.put(DbContact.NAME,name);
        contentValues.put(DbContact.EMAIL,email);
        contentValues.put(DbContact.USER_NAME,user_name);
        contentValues.put(DbContact.PASSWORD,password);
        contentValues.put(DbContact.SYNC_STATUS,synce_status);
       // database.insert(DbContact.TABLE_NAME,null,contentValues);


    }
    public Cursor readFromLocaleDatase(SQLiteDatabase database){

        String[] projection={DbContact.NAME,DbContact.EMAIL,DbContact.USER_NAME,DbContact.PASSWORD,DbContact.SYNC_STATUS};
        return (database.query(DbContact.TABLE_NAME,projection,null,null,null,null,null));



    }
    public void ubdateLocaleDatabase( String name,String email,String user_name,String password,int synce_status,SQLiteDatabase database){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DbContact.NAME,name);
       // contentValues.put(DbContact.ID,id);
        contentValues.put(DbContact.EMAIL,email);
        contentValues.put(DbContact.USER_NAME,user_name);
        contentValues.put(DbContact.PASSWORD,password);
        contentValues.put(DbContact.SYNC_STATUS,synce_status);

        String slection=DbContact.USER_NAME+"LIKE ?";


        String [] selection_args={user_name};

        database.update(DbContact.TABLE_NAME,contentValues,slection,selection_args);





    }
}
