package com.example.thomas.menu;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by thomas on 09/05/17.
 */

public class UDPClient {
    private AsyncTask<Void, Void, Void> async_cient;
    public String message= "1";

    //public byte[] Message= new byte[]{(byte)1,(byte)2,(byte)10,(byte)15};

    @SuppressLint("NewApi")
    public void send() {
        async_cient = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatagramSocket ds = null;

                try {
                    ds = new DatagramSocket(call.PortA);
                    DatagramPacket dp;
                    InetAddress local = InetAddress.getByName(call.Adr);

                    dp = new DatagramPacket(message.getBytes(),message.length(),local,call.PortA );
                    ds.setBroadcast(true);
                    ds.send(dp);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }
                return null;
            }

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }
        };

        if (Build.VERSION.SDK_INT >= 11)
            async_cient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else async_cient.execute();
    }

}
