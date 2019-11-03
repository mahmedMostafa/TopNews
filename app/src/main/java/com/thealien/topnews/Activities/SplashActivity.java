package com.thealien.topnews.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.thealien.topnews.Activities.MainActivity.MainActivity;
import com.thealien.topnews.Preferences.Preferences;
import com.thealien.topnews.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;
    Preferences preferences;
    Boolean firstTime = true;
    Intent intent;
    private TextView newsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        newsTextView = findViewById(R.id.topNews_text_view);
        preferences = Preferences.getInstance(this);


        firstTime = preferences.getKey(Preferences.KEY_FIRST_TIME_RUN).equals("null");

        preferences.saveKey(Preferences.KEY_FIRST_TIME_RUN,"no");
        //Log.d("The first time",preferences.getKey(Preferences.KEY_FIRST_TIME_RUN));
        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newsTextView.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.topnews_text_view);
                newsTextView.setAnimation(animation);
            }
        },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //this method will be executed once the timer is over
                //start the main activity or the start activity
                if(firstTime){
                    intent = new Intent(SplashActivity.this, StartActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
