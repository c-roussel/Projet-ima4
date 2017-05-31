package com.example.thomas.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by thomas on 03/05/17.
 */
//Activity permettant d'envoyer un SMS
public class mess extends AppCompatActivity {

    protected EditText txtMes;
    protected EditText txtAdr;
    protected EditText txtPort;
    protected String num;
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //Initialise l'affichage avec celui de ajout_contact.xml
        setContentView(R.layout.mess_activity);

        num=MainActivity.num;

        //Fait correspondre des éléments du layout avec des variables de la classe
        Button sendbtn = (Button) findViewById(R.id.button);
         txtMes = (EditText) findViewById(R.id.tx1);
         txtAdr = (EditText) findViewById(R.id.tx2);
         txtPort = (EditText) findViewById(R.id.tx3);

        //Lors de l'appui sur sendbtn le programme lance sendSMSMessage();
        sendbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });
    }
    //Envoie un SMS en prenant en compte le contenu des EditText,
    // et affiche le numéro du destinataire
    public void sendSMSMessage(){
        SmsManager smsManager = SmsManager.getDefault();
        Toast.makeText(mess.this,
                num, Toast.LENGTH_SHORT).show();
       smsManager.sendTextMessage(num,null,txtMes.getText().toString()+"\n Adresse:"+txtAdr.getText().toString()+"\n Port:"+txtPort.getText().toString(),null,null);
    }

}
