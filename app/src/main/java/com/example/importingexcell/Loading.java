package com.example.importingexcell;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Log.d("logwtf","ajde pisi mi molim te");

        getSupportActionBar().hide(); // hide the title bar

        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                Log.d("logwtf","izasao je ");
                Intent intentPretraga = new Intent(getApplicationContext(), Pretraga_Activity_V2.class);
                startActivity(intentPretraga);
                finish();
            }
        }, 4000);*/
        /*Log.d("logwtf","izasao iz while-a");
        Intent intentPretraga = new Intent(getApplicationContext(), Pretraga_Activity_V2.class);
        startActivity(intentPretraga);
        finish();
*/
    }
}
