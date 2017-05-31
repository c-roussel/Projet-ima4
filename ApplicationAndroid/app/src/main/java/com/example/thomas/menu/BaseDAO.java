package com.example.thomas.menu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

//Classe abstraite servant de base pour ContactDAO
public abstract class BaseDAO {// Nous sommes à la première version de la base

  // Si je décide de la mettre à jour, il faudra changer cet attribut

  protected final static int VERSION = 1;

  // Le nom du fichier qui représente ma base

  protected final static String NOM = "database.db";



  protected SQLiteDatabase mDb = null;

  protected DatabaseHandler mHandler = null;


/*
  public BaseDAO(Context pContext) {

    this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);

  }*/

  public BaseDAO(Context context) {
   mHandler = new DatabaseHandler(context);
  }


  public SQLiteDatabase open() {

    // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge

    mDb = mHandler.getWritableDatabase();

    return mDb;

  }



  public void close() {

    mDb.close();

  }



  public SQLiteDatabase getDb() {

    return mDb;

  }

}

