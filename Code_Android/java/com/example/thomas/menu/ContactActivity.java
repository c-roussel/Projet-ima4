package com.example.thomas.menu;

import android.app.ListActivity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by thomas on 30/04/17.
 */
//Affiche la liste de contact et permet de lancer un ajout de contact
// Ou de choisir un contact
public class ContactActivity extends AppCompatActivity {

    public static List<Contact> contacts;
    public ListView mListView;
    public static SQLiteDatabase db;
    private static String[] allColumns = { DatabaseHandler.CONTACT_PSEUDO,
      DatabaseHandler.CONTACT_NUM };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Initialise l'affichage avec celui de contact.xml
        setContentView(R.layout.contact);

        //Fait correspondre des éléments du layout avec des variables de la classe
        mListView = (ListView) findViewById(R.id.simpleListView);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.floatingActionButton3);

        //Lors de l'appui sur add le programme lance add();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();

            }
        });

        //génère une liste de contact
        contacts = genererContacts1();

        if (contacts == null)
        {add();}
        else {
            //Si la liste n'est pas nulle, on génère une ListView
            ContactAdapter adapter = new ContactAdapter(ContactActivity.this, contacts);
            mListView.setAdapter(adapter);
            mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            //Si on clique sur un élément de ListView:
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Met à jour des variables dans MainActivty
                    MainActivity.key = contacts.get(i);
                    MainActivity.num=MainActivity.key.getNum();
                    //Lance FicheContact
                   Intent inte =new Intent(ContactActivity.this, FicheContact.class);
                    startActivity(inte);
                }

            });
        }
    }

    //génère une liste de Contact
    private List<Contact> genererContacts1() {

        List<Contact> contac = new ArrayList<Contact>();
        MainActivity.cont=new ContactDAO(this);
        db=MainActivity.cont.open();
        Cursor cursor= db.query(DatabaseHandler.CONTACT_TABLE_NAME,allColumns,null,null,null,null,null);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
        contac.add(new Contact(cursor.getString(0),cursor.getString(1)));
        }
        cursor.close();
        return contac;
    }

    //Lance AjoutContact et termine ContactActivity
    private void add(){
        startActivity(new Intent(ContactActivity.this,AjoutContact.class));
        finish();

    }
}


