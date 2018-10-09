package com.example.one.calorierecorderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DBname = "Calorie.db";
    private static final String DAILY = "Daily";

    private static final String CREATE_DAILY_TABLE
            = "create table " + DAILY + "(id integer primary key autoincrement, date DATE, calorie integer)";

    public DBHelper(Context context, int version) {
        super(context,DBname,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDb) {
        sqlDb.execSQL(CREATE_DAILY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion){
            case 1:
                db.execSQL(CREATE_DAILY_TABLE);
                break;
            default:
                throw new IllegalStateException("Unkonw version:"+oldVersion);
        }
    }
}
