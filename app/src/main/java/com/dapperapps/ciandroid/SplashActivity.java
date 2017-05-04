package com.dapperapps.ciandroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends Activity {

    Button mBtnToday;
    Button mBtnWeekly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mBtnToday=(Button) findViewById(R.id.btn_today);
        mBtnWeekly=(Button) findViewById(R.id.btn_weekly);



        mBtnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
        mBtnWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this, ScreenSlidePagerActivity.class);
                startActivity(i);

            }
        });

    }



}
