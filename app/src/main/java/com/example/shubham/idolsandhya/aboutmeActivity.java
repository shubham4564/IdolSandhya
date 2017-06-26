package com.example.shubham.idolsandhya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by shubham on 6/26/17.
 */

public class aboutmeActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_vote:
                    Toast.makeText(getApplicationContext(),
                            "Vote",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(aboutmeActivity.this , MainActivity.class);
                    startActivity(intent);
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
                    Intent intent1 = new Intent(aboutmeActivity.this , socialActivity.class);
                    startActivity(intent1);
                    //    mTextMessage.setText(R.string.title_social);
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {*/
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
        //}
    }
}
