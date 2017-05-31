package com.example.thomas.menu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

//méthodes pour modifier la base de données
public class ContactDAO extends BaseDAO {

    public static final String TABLE_NAME = "Contact";

  public static final String PSEUDO = "pseudo";

  public static final String NUM = "num";


  public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + PSEUDO + " TEXT PRIMARY KEY AUTOINCREMENT, " +NUM + " TEXT NOT NULL "+ ");";


  public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

public ContactDAO(Context context){
  super(context);
}
  public void ajouter(BDDContact m) {


      ContentValues value = new ContentValues();

      value.put(ContactDAO.NUM, m.getNum());

        value.put(ContactDAO.PSEUDO, m.getPseudo());

    mDb.insert(ContactDAO.TABLE_NAME, null, value);

  }


  public void supprimer(String pseudo) {


      mDb.delete(TABLE_NAME, PSEUDO + " = ?", new String[] {String.valueOf(pseudo)});

  }


  public void modifierNum(BDDContact m) {

   ContentValues value = new ContentValues();

    value.put(NUM, m.getNum());

    mDb.update(TABLE_NAME, value, PSEUDO  + " = ?", new String[] {String.valueOf(m.getPseudo())});

  }

}
