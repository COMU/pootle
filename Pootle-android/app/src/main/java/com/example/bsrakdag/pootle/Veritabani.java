package com.example.bsrakdag.pootle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Veritabani extends SQLiteOpenHelper {
    private static final String Veritabani_Adi = "Veritabanim";
    private static final int Veritabani_Version = 2;
    public Veritabani(Context context) {
        super(context, Veritabani_Adi, null, Veritabani_Version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ServerTablosu(_servername STRING PRIMARY KEY, username STRING , password STRING, nickname STRING);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ServerTablosu");
        onCreate(db);
    }
}