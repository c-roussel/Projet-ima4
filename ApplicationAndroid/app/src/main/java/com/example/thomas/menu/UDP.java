package com.example.thomas.menu;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;

/**
 * Created by thomas on 09/05/17.
 */

public class UDP extends AppCompatActivity  {

    protected static byte [] message= new byte[]{56};
    private ToggleButton startbtn;
    private FloatingActionButton cancelbtn;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private int cmp=0;

    //Timer permettant d'envoyer des paquets UDP, à intervalle régulier
    //Ici toutes les secondes, le timer s'arrète a 30 secondes
    //C'est pourquoi onFinish() appel Timer() qui va relancer le compteur
    protected final UDPClient client= new UDPClient();
    CountDownTimer cpt= new CountDownTimer(30000, 1000) {

        public void onTick(long millisUntilFinished) {
            client.send();

        }

        public void onFinish() {
            Timer();
        }
    };

    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Initialise l'affichage avec celui de activity_udp.xml
        setContentView(R.layout.activity_udp);

        //Initialise un objet UDPReceive pour la réception UDP
        final UDPReceive receive = new UDPReceive();

        //Récupère l'addresse Bluetooth grâce à un intent
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);

        //Fait correspondre des éléments du layout avec des variables de la classe
        cancelbtn = (FloatingActionButton) findViewById(R.id.button);
        startbtn = (ToggleButton) findViewById(R.id.toggle);
        Button sendbtn = (Button) findViewById(R.id.button2);
        TextView tv1 = (TextView) findViewById(R.id.textView);

        //Appel la fonction execute() pour se connecter en bluetooth avec l'appareil
        new ConnectBT().execute();

        //Lors de l'appui sur cancelbtn le programme se charge d'arrêter la réception et l'envoi
        //en bluetooth comme en UDP après avoir envoyer des messages de fin à l'autre utilisateur et
        // a son appareil bluetooth
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Annule la boucle infinie de receive
                receive.cont=0;
                //Met cmp=1 pour empêcher que Timer() relance le compteur
                cmp=1;
                //Annule le compteur, se qui va lancer onFinish() et donc Timer()
                cpt.cancel();
                //Envoie d'un message UDP de fin
                client.message=new String (new byte[] {59});
                client.send();
                //envoie d'un message Bluetooth de fin
                message=new byte[]{59};
                send(btSocket,client);
                //se déconnecte du périphérique bluetooth
                Disconnect();
                //réinitialise les variables message
                client.message="1";
                message= new byte[]{55};
                //et termine le programme
                cancel();
            }
        });

        //Lors de l'appui sur sendbtn le programme se charge d'envoyer un message a son appareil bluetooth
        // et d'envoyer un message en UDP à l'autre utilisateur.
        //Cette partie est seulement utile pour faire des tests
        sendbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                send(btSocket,client);
               client.send();
                Toast.makeText(UDP.this,call.Adr,Toast.LENGTH_SHORT).show();
            }
        });

        //Lors de l'appui sur startbtn le programme se charge de verifer l'etat du ToggleButton
        //Si il est sur "on" on reprend la réception et l'envoi UDP comme bluetooth
        startbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                { receive.cont=1;
                    receive.receive(btSocket,client);
                    cmp=0;
                cpt.start();}
                else{
                    //Sinon on arrête la transmission UDP et bluetooth

                    receive.cont=0;
                    cmp=1;
                    cpt.cancel();
                    client.message="1";
                    client.send();


                    message="1".getBytes();
                    send(btSocket,client);
                }
            }
        });


        }

    //Est appélé à chaque onFinish() du timer et permet de le relancer si cmp==0
    private void Timer(){
        if (cmp==0){
        cpt.start();
        }
    };

    //Se deconnecte du périphérique bluetooth
    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    //Envoie et réception sur le périphérique bluetooth et mise à jours des valeurs envoyer en UDP
    protected static void send(BluetoothSocket btSocket,UDPClient client)
    { byte[] mmBuffer = new byte[1];
            if (btSocket!=null)
        {
            try
            {   btSocket.getOutputStream().write(message);
                int nums=btSocket.getInputStream().read(mmBuffer);
                client.message=new String(mmBuffer);

            }
            catch (IOException e)
            {

            }
        }
    }


    // fast way to call Toast
    private  void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
    }
    //Ouverture du menu option, mais pas utiliser
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }
    //Gestion du menu option, mais pas utiliser
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Classe pour se connecter en bluetooth
private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
{
    private boolean ConnectSuccess = true; //if it's here, it's almost connected

    @Override
    protected void onPreExecute()
    {
        progress = ProgressDialog.show(UDP.this, "Connecting...", "Please wait!!!");  //show a progress dialog
    }

    @Override
    protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
    {
        try
        {
            if (btSocket == null || !isBtConnected)
            {
                myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                btSocket.connect();//start connection
                //thread= new ConnectedThread();
            }
        }
        catch (IOException e)
        {
            ConnectSuccess = false;//if the try failed, you can check the exception here
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
    {
        super.onPostExecute(result);

        if (!ConnectSuccess)
        {
            msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
            finish();
        }
        else
        {
            msg("Connected.");
            isBtConnected = true;
        }
        progress.dismiss();
    }
}


    public void cancel()
    {finish();}

}
