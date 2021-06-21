package com.heaven.foodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heaven.foodie.model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CategoriesViewActivity extends AppCompatActivity {

    private ImageView BackImage;
    private TextView foodCategoryTv;
    RecyclerView categoriesRecyclerview;
    fooddisplayadapter Categoryadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_view);

        getSupportActionBar().hide();
        BackImage = findViewById(R.id.backBtn);
        foodCategoryTv = findViewById(R.id.foodCategoryNameDisplay);
        String CategoryName = getIntent().getStringExtra("Category");
        foodCategoryTv.setText(CategoryName.toUpperCase() + " MENU");

        //show category wise data

        categoriesRecyclerview = findViewById(R.id.CategoryRecycler);
        categoriesRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FoodModel> options =
                new FirebaseRecyclerOptions.Builder<FoodModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food").orderByChild("categories").equalTo(CategoryName), FoodModel.class)
                        .build();

        Categoryadapter = new fooddisplayadapter(options);
        categoriesRecyclerview.setAdapter(Categoryadapter);


        //end


        BackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesViewActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        Categoryadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        Categoryadapter.stopListening();
    }


}