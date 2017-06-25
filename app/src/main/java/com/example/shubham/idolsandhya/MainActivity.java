package com.example.shubham.idolsandhya;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    Button sendBtn;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_vote:
                    Toast.makeText(getApplicationContext(),
                            "Vote",
                            Toast.LENGTH_LONG).show();
                //    mTextMessage.setText(R.string.title_vote);
                    return true;
                case R.id.navigation_aboutme:
                    Toast.makeText(getApplicationContext(),
                            "About Me",
                            Toast.LENGTH_LONG).show();
                //    mTextMessage.setText(R.string.title_aboutme);
                    return true;
                case R.id.navigation_social:
                    Toast.makeText(getApplicationContext(),
                            "Social Links",
                            Toast.LENGTH_LONG).show();
                //    mTextMessage.setText(R.string.title_social);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sendBtn = (Button) findViewById(R.id.btnSendSMS);



        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String phoneNo = "9860561875";
                String sms = "14";
                EditText count1 = (EditText) findViewById(R.id.count);
                //private int iicount;
                int iicount = Integer.parseInt( count1.getText().toString() );
                for(int icount=0; icount < iicount; icount++){
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                        Toast.makeText(getApplicationContext(), "SMS Sent!",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }

            }
        });

       // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }





}
