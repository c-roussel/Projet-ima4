package com.example.thomas.menu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



//Contient les chaines de caractères nécessaires pour la
//manipulation de base de données
public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String CONTACT_PSEUDO = "pseudo";
    public static final String CONTACT_NUM = "num";
    private static final int DATABASE_VERSION = 1;

    public static final String CONTACT_TABLE_NAME = "Contact";
    public static final String CONTACT_TABLE_CREATE =
            "CREATE TABLE " + CONTACT_TABLE_NAME + " (" +
                    CONTACT_PSEUDO + " TEXT PRIMARY KEY , " +
                    CONTACT_NUM +
                    " TEXT NOT NULL);";

    public DatabaseHandler(Context context) {
        super(context, CONTACT_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONTACT_TABLE_CREATE);
    }
    public static final String CONTACT_TABLE_DROP = "DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME + ";";


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(CONTACT_TABLE_DROP);

        onCreate(db);

    }
}

