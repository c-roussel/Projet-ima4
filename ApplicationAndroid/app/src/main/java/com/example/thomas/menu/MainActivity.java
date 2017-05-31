package com.example.thomas.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Variable globale, pour les autres activités, même si l'utilisation d'intent
    //pour transmettre des informations aurait été plus efficace
    public static Contact key;
    public static String num;
    public static ContactDAO cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialise l'affichage avec celui de activity_main.xml
        setContentView(R.layout.activity_main);
        //Fait correspondre un élément du layout avec une variable de la classe
        Button contact = (Button) findViewById(R.id.contact);
        //Lors de l'appui sur contact le programme lance contact();
            contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact();

            }
        });
    }
    //Lance l'activity ContactActivity
    public void contact(){
              startActivity(new Intent(this, ContactActivity.class));
    }

}
