package com.example.casestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class FirstUserDetail extends AppCompatActivity {

    EditText e1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_user_detail);
        getSupportActionBar().hide();

//      the Existing user value Focus false

        e1=findViewById(R.id.exisinumber);
        e1.setText("9726036668");


    }
}