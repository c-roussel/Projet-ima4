package com.example.thomas.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


//Activité d'ajout de contact dans la base de donnée
public class AjoutContact extends AppCompatActivity {
    TextView tx1;
    TextView tx2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Initialise l'affichage avec celui de ajout_contact.xml
        setContentView(R.layout.ajout_contact);

        //Fait correspondre des éléments du layout avec des variables de la classe
        Button bt1 = (Button) findViewById(R.id.bt1);
        tx1 = (TextView) findViewById(R.id.txt1);
        tx2 = (TextView) findViewById(R.id.txt2);

        //Lors de l'appui sur bt1 le programme lance bt1();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1();

            }
        });
    }
         public void bt1(){
             //utilise une variable globale dans MainActivity, pour ajouter un contact dans la base de donnée
             MainActivity.cont.ajouter(new BDDContact(tx1.getText().toString(),tx2.getText().toString()));

             //Lance ContactActivity et termine AjoutContact, pour pouvoir la liste de Contact actualisée.
             startActivity(new Intent(AjoutContact.this,ContactActivity.class));
             finish();
    }
}
