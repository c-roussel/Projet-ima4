package com.example.thomas.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by Thomas S on 11/05/2017.
 */


public class UDP2 extends AppCompatActivity {
    private ToggleButton startbtn;
    private FloatingActionButton cancelbtn;
    private Button lvl1;
    private Button lvl2;
    private Button lvl3;
    private Button lvl4;
    private TextView tv1;
    private int cmp=0;
    protected static String buff;
    final UDPClient client= new UDPClient();
    CountDownTimer cpt= new CountDownTimer(30000, 1000) {

        public void onTick(long millisUntilFinished) {
            client.send();
            msg(buff);
        }

        public void onFinish() {
            if (cmp==0){
            cpt.start();
        }

        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udp2);
        final UDPReceive2 receive = new UDPReceive2();



        //contex = UDP.this;
        cancelbtn = (FloatingActionButton) findViewById(R.id.button);
        startbtn = (ToggleButton) findViewById(R.id.toggle);
        lvl1 =(Button) findViewById(R.id.lvl1);
        lvl2 =(Button) findViewById(R.id.lvl2);
        lvl3 =(Button) findViewById(R.id.lvl3);
        lvl4 =(Button) findViewById(R.id.lvl4);
        tv1=(TextView)findViewById(R.id.textView2) ;
        TextView tv1 = (TextView) findViewById(R.id.textView);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                receive.cont=0;
                cmp=1;
                cpt.cancel();
                client.message="1";
                client.send();
                finish();
            }
        });

        lvl1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                client.message="a";
            }
        });
        lvl2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                client.message="b";
            }
        });
        lvl3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                client.message="c";
            }
        });
        lvl4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                client.message="d";
            }
        });
        startbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                { receive.cont=1;
                    receive.receive(client);
                    cmp=0;
                cpt.start();}
                else{
                    cmp=1;
                    cpt.cancel();
                    client.message="1";
                    client.send();

                    receive.cont=0;
                }
            }
        });
    }

    private void Timer(){
        cpt.start();
    };

    // mise à jours d'un EditText
    private  void msg(String s)
    {
        tv1.setText(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

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
}
