package com.heaven.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    Animation bottomAnim;

    TextView tv;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        tv=findViewById(R.id.textView);
        tv.setAnimation(bottomAnim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent=new Intent(SplashScreen.this,Sign_up_with_phone_number.class);
//                startActivity(intent);

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                firebaseAuth.addAuthStateListener(authStateListener);
                finish();

            }
        }, 5000);

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

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SplashScreen.this, Sign_up_with_phone_number.class);
                startActivity(intent);
            }

        }
    };


//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }


}