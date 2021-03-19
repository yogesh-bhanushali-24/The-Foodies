package com.example.casestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;

public class OtpVerificationActivity extends AppCompatActivity {

    MaterialTextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        getSupportActionBar().hide();

        String mobile = getIntent().getStringExtra("mobile");

        tv = findViewById(R.id.txtVerify);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpVerificationActivity.this, FirstUserDetail.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
                finish();
            }
        });
    }
}