package com.example.casestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.SignInButtonImpl;

public class Sign_up_with_phone_number extends AppCompatActivity {

    SignInButtonImpl btnContinue;
    EditText etUserNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_phone_number);
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        etUserNumber = findViewById(R.id.UserNumber);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = etUserNumber.getText().toString();
                Intent intentOtpVerificationActivity = new Intent(Sign_up_with_phone_number.this, OtpVerificationActivity.class);
                intentOtpVerificationActivity.putExtra("mobile", n);
                startActivity(intentOtpVerificationActivity);
                Toast.makeText(Sign_up_with_phone_number.this, "button click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}