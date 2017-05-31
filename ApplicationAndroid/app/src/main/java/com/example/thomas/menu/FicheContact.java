package com.example.thomas.menu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//Affiche les données d'un Contact et permet de choisir l'interraction avec celui-ci
public class FicheContact extends AppCompatActivity {
protected String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialise l'affichage avec celui de activity_fiche_contact.xml
        setContentView(R.layout.activity_fiche_contact);

        //Fait correspondre des éléments du layout avec des variables de la classe
        TextView pseudo =(TextView) findViewById(R.id.pseudo) ;
        TextView num =(TextView) findViewById(R.id.num) ;

        //Utilise des variables de MainActivity pour mettre à jour
        //l'affichage des TextView, en affichant le pseudo et le numéro de téléphone
        pseudo.setText(MainActivity.key.getPseudo());
        num.setText(MainActivity.key.getNum());

        //Lors de l'appui sur mess le programme lance mess();
        FloatingActionButton mess = (FloatingActionButton) findViewById(R.id.bt1);
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mess();

            }
        });
        //Lors de l'appui sur suppr le programme lance suppr();
        FloatingActionButton suppr = (FloatingActionButton) findViewById(R.id.bt2);
        suppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suppr();

            }
        });
        //Lors de l'appui sur call le programme lance call();
        FloatingActionButton call = (FloatingActionButton) findViewById(R.id.bt3);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();

            }
        });
    }

    //Lance l'activity call et termine FicheContact
    private void call(){
        startActivity(new Intent(FicheContact.this,call.class));
        finish();
    }
    //Met à jour la base de données en supprimant le contact
    // Lance l'activity Contact pour actualiser l'affichage des contacts et termine FicheContact
    private void suppr(){
        MainActivity.cont.supprimer(MainActivity.key.getPseudo());
        startActivity(new Intent(FicheContact.this,ContactActivity.class));
        finish();
    }

    //Lance l'activity mess et termine FicheContact
    private void mess(){
        Intent inte=new Intent(FicheContact.this,mess.class);
        startActivity(inte);
        finish();
    }

}
