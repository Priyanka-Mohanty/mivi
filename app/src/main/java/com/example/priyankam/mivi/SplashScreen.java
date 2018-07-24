package com.example.priyankam.mivi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2 * 1000;// 1 seconds
    Animation animFadein;
    LinearLayout linearLayout;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = SplashScreen.this;


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.

        }


        // load the animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        // set animation listener
        animFadein.setAnimationListener(this);

        // animation for image
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        // start the animation

        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.startAnimation(animFadein);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    public void onAnimationEnd(Animation animation) {
        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}
