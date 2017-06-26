package com.example.shubham.idolsandhya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shubham on 6/26/17.
 */

public class socialActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{


    private String TAG = socialActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyDl2oUGOweKiHCGQ3jZU7wUBXIGVnRn5Yg&channelId=UCzNfKeIwWsYXlHzK51P4mBg&part=snippet,id&order=date&maxResults=4";
    ArrayList<HashMap<String, String>> videoIdList;




    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;


    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;
    private String YouTubeKey = "AIzaSyCxeQaLuDvg1KxCqViujHJp7ZiFn4O6Vzw";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_vote:
                    Toast.makeText(getApplicationContext(),
                            "Vote",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(socialActivity.this , MainActivity.class);
                    startActivity(intent);
                    //    mTextMessage.setText(R.string.title_vote);
                    return true;
                case R.id.navigation_aboutme:
                    Toast.makeText(getApplicationContext(),
                            "About Me",
                            Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(socialActivity.this , aboutmeActivity.class);
                    startActivity(intent1);
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
        setContentView(R.layout.testsocial);




        videoIdList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);
        new getVideoId().execute();
        new getVideoId().execute();



       // playerFragment =
        //        (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_fragment);

       // playerFragment.initialize(YouTubeKey, this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation1);
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

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        mPlayer = player;

        //Enables automatic control of orientation
        //mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //Show full screen in landscape mode always
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //System controls will appear automatically
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if (!wasRestored) {
            player.cueVideo("LPFi5YEeqao");
            //mPlayer.loadVideo("9rLZYyMbJic");
        }
        else
        {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        mPlayer = null;

    }



    /**
     * Async task class to get json by making HTTP call
     */
    private class getVideoId extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(socialActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray items = jsonObj.getJSONArray("items");

                    // looping through All Contacts
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);

                        // Id node is JSON Object
                        JSONObject id = c.getJSONObject("id");
                        String videoId = id.getString("videoId");

                        // tmp hash map for single videoId
                        HashMap<String, String> videoIds = new HashMap<>();

                        // adding each child node to HashMap key => value
                        videoIds.put("videoId", videoId);


                        // adding contact to contact list
                        videoIdList.add(videoIds);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    socialActivity.this, videoIdList,
                    R.layout.list_item, new String[]{"videoId"}, new int[]{R.id.videoId});

            lv.setAdapter(adapter);
        }

    }

}
