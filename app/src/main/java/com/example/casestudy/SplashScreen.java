package com.example.casestudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Animation bottomAnim;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        tv=findViewById(R.id.textView);
        tv.setAnimation(bottomAnim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,Sign_up_with_phone_number.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        },4000);

//        Thread thread=new Thread()
//        {
//            public void run()
//            {
//                try {
//                        sleep(4000);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                finally {
//                    Intent intent=new Intent(SplashScreen.this,Sign_up_with_phone_number.class);
//                    startActivity(intent);
//                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    finish();
//                }
//            }
//        };thread.start();
    }
}