package com.example.mycode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  static int SPLASH_SCREEN = 5000;


    // variable
    Animation topAnim,botomAnim;
    ImageView image;
    TextView logo,slogon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Animatiion
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        // Hooks
        image = findViewById(R.id.mylogo);
        logo = findViewById(R.id.mycode_text);
        slogon = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        logo.setAnimation(botomAnim);
        slogon.setAnimation(topAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logo,"logo_text");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options  = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent,options.toBundle());
                    finish();

                    }
            }
        },SPLASH_SCREEN);
    }
}