package com.example.thomas.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Time;

//Activity permettant d'entrer des paramètres pour ensuit lancer le protocole UDP
public class call extends AppCompatActivity {
    protected   EditText txtPortA;
    protected  EditText txtAdr;
    protected EditText txtPortB;
    protected EditText txtHour;
    protected  static int PortA;
    protected static  String Adr;
    protected static int PortB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialise l'affichage avec celui de call_activity.xml
        setContentView(R.layout.call_activity);

        //Fait correspondre des éléments du layout avec des variables de la classe
        Button sendbtn = (Button) findViewById(R.id.button);
        txtAdr = (EditText) findViewById(R.id.tx0);
        txtPortA= (EditText) findViewById(R.id.tx1);
        txtPortB = (EditText) findViewById(R.id.tx2);
        txtHour= (EditText) findViewById(R.id.tx3);

        //Lors de l'appui sur sendbtn le programme lance sendCall();
        sendbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendCall();
            }
        });
    }

    private void sendCall(){
        //Met dans des variables le contenue des EditText
        PortA= Integer.parseInt(txtPortA.getText().toString());
        PortB= Integer.parseInt(txtPortB.getText().toString());
        Adr= txtAdr.getText().toString();

        //Lancement de l'activité DeviceList
        Intent inten=new Intent(call.this,DeviceList.class);
        startActivity(inten);

    }
}