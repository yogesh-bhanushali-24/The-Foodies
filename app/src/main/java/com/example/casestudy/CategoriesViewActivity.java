package com.example.casestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesViewActivity extends AppCompatActivity {

    private ImageView BackImage;
    private TextView foodCategoryTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_view);

        getSupportActionBar().hide();

        BackImage = findViewById(R.id.backBtn);
        foodCategoryTv = findViewById(R.id.foodCategoryNameDisplay);

        String CategoryName = getIntent().getStringExtra("Category");
        foodCategoryTv.setText(CategoryName.toUpperCase() + " MENU");

        BackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesViewActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}