package com.example.villip.app1.dataprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "linkManager";
    private static final int DATABASE_VERSION = 1;
    private static final String LINKS_TABLE_NAME = "linksTable";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase) {
        sqliteDatabase.execSQL("CREATE TABLE " + LINKS_TABLE_NAME + " (" + LinksContract.Links._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LinksContract.Links.LINK + " TEXT, " +
                LinksContract.Links.STATUS + " INTEGER, " + LinksContract.Links.TIME + " TEXT )");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  LINKS_TABLE_NAME);
        onCreate(db);
    }
}
