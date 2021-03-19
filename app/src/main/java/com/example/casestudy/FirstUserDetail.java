package com.example.casestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class FirstUserDetail extends AppCompatActivity {

    EditText etNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_user_detail);
        getSupportActionBar().hide();

//      the Existing user value Focus false

        etNumber = findViewById(R.id.existingNumber);
//        e1.setText("9726036668");

        String mobile = getIntent().getStringExtra("mobile");
        etNumber.setText(mobile);


    }
}