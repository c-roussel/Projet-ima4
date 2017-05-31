package com.example.thomas.menu;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by thomas on 09/05/17.
 */


public class UDPReceive {

    DatagramSocket dsocket;
    DatagramPacket packet;
    byte[] buff;
    int taille =0;

    String buf;


    private AsyncTask<Void, Void, Void> async_cient;
    public String Message = "m";
    public int cont =1;

    @SuppressLint("NewApi")
    final public byte[] receive(final BluetoothSocket btSocket, final UDPClient client) {
        async_cient = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                while (cont==1) {
                    try {
                        try {
                            dsocket = new DatagramSocket(call.PortB);
                            // dsocket.setReuseAddress(true);
                            //dsocket.setSoTimeout(1000);
                            byte[] buffer = new byte[1];
                            packet = new DatagramPacket(buffer, buffer.length);
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }
                        int continu = 1;


                        while (continu == 1) {  // && counter < 100
                            // send to server omitted
                            try {
                                dsocket.receive(packet);
                                taille = packet.getLength();

                                if (taille == 0) {
                                    buff = new byte[]{-1};
                                } else {
                                    buff = packet.getData();

                                    dsocket.close();
                                    continu = 0;
                                }

                                // If you're not using an infinite loop:
                                //mDataGramSocket.close();

                            } catch (SocketTimeoutException | NullPointerException e) {
                                // no response received after 1 second. continue sending

                                e.printStackTrace();

                            }
                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                        // return "error:" + e.getMessage();
                    }
                    if (cont==1) {
                        UDP.message = buff;
                        UDP.send(btSocket, client);
                    }



                }
                return null;
            }



/*
                try {int continu=1;
                    while (continu==1) {

                        dsocket.receive(packet);
                        taille = packet.getLength();

                        if (taille == 0) {
                            buff = new byte[]{-1};
                        } else {
                            buff = packet.getData();
                            dsocket.close();
                            continu = 0;
                        }
                        dsocket.close();
                    }
                }
                catch (SocketTimeoutException | NullPointerException e) {
                // no response received after 1 second. continue sending

                e.printStackTrace();
            }

                }
                return null;
            }*/

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }
        };

        if (Build.VERSION.SDK_INT >= 11)
            async_cient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else async_cient.execute();

        return buff;
    }

}
